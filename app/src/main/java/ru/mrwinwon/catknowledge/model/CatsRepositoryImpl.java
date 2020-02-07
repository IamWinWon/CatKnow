package ru.mrwinwon.catknowledge.model;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ru.mrwinwon.catknowledge.model.local.CatAvatar;
import ru.mrwinwon.catknowledge.model.local.CatDao;
import ru.mrwinwon.catknowledge.model.local.CatEntity;
import ru.mrwinwon.catknowledge.model.remote.ApiService;
import ru.mrwinwon.catknowledge.model.remote.CatsResponse;
import ru.mrwinwon.catknowledge.model.remote.NetworkBounder;
import ru.mrwinwon.catknowledge.model.remote.Resource;

import static ru.mrwinwon.catknowledge.model.remote.ApiConsts.RANDOM_URL;

public class CatsRepositoryImpl implements CatsRepository {

    private final CatDao catDao;
    private final ApiService apiService;

    @Inject
    CatsRepositoryImpl(CatDao catDao, ApiService apiService) {
        this.apiService = apiService;
        this.catDao = catDao;
    }


    @Override
    public LiveData<Resource<List<CatEntity>>> loadCats() {
        return new NetworkBounder<List<CatEntity>, CatsResponse>() {
            @Override
            protected void saveCallResult(CatEntity item) {
                if (item != null)
                    Completable.fromAction(() -> catDao.saveCat(item))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableCompletableObserver() {
                                @Override
                                public void onComplete() {
                                    Log.d("CatEntry: ", item.toString());
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.d("CatEntry: ", e.getMessage());
                                }
                            });

            }

            @Override
            protected void saveCallAvatarResult(CatAvatar item) {
                if (item != null)
                    Completable.fromAction(() -> catDao.saveAvatar(item))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableCompletableObserver() {
                                @SuppressLint("CheckResult")
                                @Override
                                public void onComplete() {
                                    Log.d("CatAvatar: ", item.toString());
                                    catDao.loadEmptyCat("empty")
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribeWith(new DisposableSingleObserver<CatEntity>() {
                                                @Override
                                                public void onSuccess(CatEntity catEntity) {
                                                    catEntity.setAvatar(item.getAvatar());
                                                    saveCallResult(catEntity);
                                                }

                                                @Override
                                                public void onError(Throwable e) {
                                                    e.printStackTrace();
                                                    Log.d("CatEntry: ", e.getMessage());
                                                }
                                            });
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.d("CatAvatar: ", e.getMessage());
                                }
                            });
            }

            @NonNull
            @Override
            protected LiveData<List<CatEntity>> loadFromDb() {
                return catDao.loadCats();
            }

            @NonNull
            @Override
            protected Single<CatEntity> createCallRequests() {
                return apiService.loadCatInfo();
            }

            @NonNull
            @Override
            protected Single<CatAvatar> createCallAvatar() {
                return apiService.loadCatAvatar(RANDOM_URL);
            }
        }.getAsLiveData();
    }

    @SuppressLint("CheckResult")
    @Override
    public void deleteCatById(String catId) {
        Completable.fromAction(() -> catDao.deleteByCatId(catId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.d("CatEntry", ":" + catId + " deleting done");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("CatEntry", ":" + catId + " deleting NE done");

                    }
                });
    }

}
