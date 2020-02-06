package ru.mrwinwon.catknowledge.model.remote;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.google.gson.stream.MalformedJsonException;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import ru.mrwinwon.catknowledge.R;
import ru.mrwinwon.catknowledge.app.CatsApp;
import ru.mrwinwon.catknowledge.model.local.CatEntity;

public abstract class NetworkBounder<T, V> {
    private final MediatorLiveData<Resource<T>> resourceMediatorLiveData = new MediatorLiveData<>();

    @MainThread
    protected NetworkBounder() {
        resourceMediatorLiveData.setValue(Resource.loading(null));

        LiveData<T> dbSource = loadFromDb();

        resourceMediatorLiveData.addSource(dbSource, data -> {
            resourceMediatorLiveData.removeSource(dbSource);
            if (shouldFetch()) {
                fetchFromNetwork(dbSource);
            } else {
                resourceMediatorLiveData.addSource(dbSource, newData -> {
                    if (null != newData)
                        resourceMediatorLiveData.setValue(Resource.success(newData));
                });
            }
        });
    }

    /**
     * This method fetches the data from remoted service and save it to local db
     *
     * @param dbSource - Database source
     */
    private void fetchFromNetwork(final LiveData<T> dbSource) {
        resourceMediatorLiveData.addSource(dbSource, newData -> resourceMediatorLiveData.setValue(Resource.loading(newData)));

        createCall().enqueue(new Callback<V>() {
            @Override
            public void onResponse(@NonNull Call<V> call, @NonNull Response<V> response) {
                resourceMediatorLiveData.removeSource(dbSource);
                saveResultAndReInit(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<V> call, @NonNull Throwable t) {
                resourceMediatorLiveData.removeSource(dbSource);
                resourceMediatorLiveData.addSource(dbSource, newData -> resourceMediatorLiveData.setValue(Resource.error(getCustomErrorMessage(t), newData)));
            }
        });

//        Disposable disposable = Single.create()
//                .subscribeWith(new DisposableSingleObserver<CatEntity>(){
//                    @Override
//                    public void onSuccess(CatEntity catEntity) {
//                        resourceMediatorLiveData.removeSource(dbSource);
//                        saveResultAndReInit(response.body());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        resourceMediatorLiveData.removeSource(dbSource);
//                        resourceMediatorLiveData.addSource(dbSource, newData -> resourceMediatorLiveData.setValue(Resource.error(getCustomErrorMessage(e), newData)));
//                    }
//                });
//        addSubscription(disposable);
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

    @SuppressLint("StaticFieldLeak")
    @MainThread
    private void saveResultAndReInit(V response) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                saveCallResult(response);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                resourceMediatorLiveData.addSource(loadFromDb(), newData -> {
                    if (newData != null)
                        resourceMediatorLiveData.setValue(Resource.success(newData));
                });
            }
        }.execute();
    }

    @WorkerThread
    protected abstract void saveCallResult(V item);

    @MainThread
    private boolean shouldFetch() {
        return true;
    }

    @NonNull
    @MainThread
    protected abstract LiveData<T> loadFromDb();

    @NonNull
    @MainThread
    protected abstract Call<V> createCall();

    @NonNull
    @MainThread
    protected abstract Single<V> createSingle();

    public final LiveData<Resource<T>> getAsLiveData() {
        return resourceMediatorLiveData;
    }
}
