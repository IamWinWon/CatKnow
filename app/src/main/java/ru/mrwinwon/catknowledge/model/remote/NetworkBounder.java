package ru.mrwinwon.catknowledge.model.remote;

import android.annotation.SuppressLint;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.google.gson.stream.MalformedJsonException;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import ru.mrwinwon.catknowledge.R;
import ru.mrwinwon.catknowledge.app.CatsApp;
import ru.mrwinwon.catknowledge.model.local.CatAvatar;
import ru.mrwinwon.catknowledge.model.local.CatEntity;

public abstract class NetworkBounder<T, V> {
    private final MediatorLiveData<Resource<T>> resourceMediatorLiveData = new MediatorLiveData<>();


    @MainThread
    protected NetworkBounder() {
        resourceMediatorLiveData.setValue(Resource.loading(null));


        LiveData<T> dbSource = loadFromDb();
        fetchFromNetwork(dbSource);

    }

    @SuppressLint("CheckResult")
    private void fetchFromNetwork(final LiveData<T> dbSource) {
        resourceMediatorLiveData.addSource(dbSource, newData -> resourceMediatorLiveData.setValue(Resource.loading(newData)));

        createCallRequests()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CatEntity>() {
                    @Override
                    public void onSuccess(CatEntity catEntity) {
                        if (catEntity != null) {
                            saveCallResult(catEntity);
                            resourceMediatorLiveData.removeSource(dbSource);
                            resourceMediatorLiveData.addSource(loadFromDb(), newData -> {
                                if (newData != null)
                                    resourceMediatorLiveData.setValue(Resource.success(newData));
                            });
                        }

                    }

                    public void onError(Throwable e) {
                        e.printStackTrace();
                        resourceMediatorLiveData.removeSource(dbSource);
                        resourceMediatorLiveData.addSource(dbSource, newData -> resourceMediatorLiveData.setValue(Resource.error(getCustomErrorMessage(e), newData)));
                    }
                });


        createCallAvatar()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CatAvatar>() {
                    @Override
                    public void onSuccess(CatAvatar catAvatar) {
                        saveCallAvatarResult(catAvatar);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });

    }

    private String getCustomErrorMessage(Throwable error) {

        if (error instanceof SocketTimeoutException) {
            return CatsApp.getAppContext().getString(R.string.requestTimeOutError);
        } else if (error instanceof MalformedJsonException) {
            return CatsApp.getAppContext().getString(R.string.responseMalformedJson);
        } else if (error instanceof IOException) {
            return CatsApp.getAppContext().getString(R.string.networkError);
        } else if (error instanceof HttpException) {
            return (((HttpException) error).response().message());
        } else {
            return CatsApp.getAppContext().getString(R.string.unknownError);
        }

    }


    @WorkerThread
    protected abstract void saveCallResult(CatEntity item);

    @WorkerThread
    protected abstract void saveCallAvatarResult(CatAvatar item);

    @NonNull
    @MainThread
    protected abstract LiveData<T> loadFromDb();

    @NonNull
    @MainThread
    protected abstract Single<CatEntity> createCallRequests();

    @NonNull
    @MainThread
    protected abstract Single<CatAvatar> createCallAvatar();

    public final LiveData<Resource<T>> getAsLiveData() {
        return resourceMediatorLiveData;
    }
}
