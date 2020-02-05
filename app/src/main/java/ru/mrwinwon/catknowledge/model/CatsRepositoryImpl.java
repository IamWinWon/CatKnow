package ru.mrwinwon.catknowledge.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import ru.mrwinwon.catknowledge.model.local.CatDao;
import ru.mrwinwon.catknowledge.model.local.CatEntity;
import ru.mrwinwon.catknowledge.model.remote.ApiService;
import ru.mrwinwon.catknowledge.model.remote.CatsResponse;
import ru.mrwinwon.catknowledge.model.remote.NetworkBounder;
import ru.mrwinwon.catknowledge.model.remote.Resource;

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
            protected void saveCallResult(CatsResponse item) {
                if (item != null)
                    catDao.saveCats(item.getCatEntities());
            }

            @NonNull
            @Override
            protected LiveData<List<CatEntity>> loadFromDb() {
                return catDao.loadCats();
            }

            @NonNull
            @Override
            protected Call<CatsResponse> createCall() {
                return apiService.loadRandomCatInfo();
            }
        }.getAsLiveData();
    }

    @Override
    public void deleteCat(CatEntity catEntity) {
        catDao.deleteCat(catEntity);
    }

}
