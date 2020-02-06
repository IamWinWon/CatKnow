package ru.mrwinwon.catknowledge.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import ru.mrwinwon.catknowledge.model.CatsRepository;
import ru.mrwinwon.catknowledge.model.CatsRepositoryImpl;
import ru.mrwinwon.catknowledge.model.local.CatEntity;
import ru.mrwinwon.catknowledge.model.remote.Resource;

public class CatsViewModel extends ViewModel {

    private final CatsRepository catsRepository;
    private final LiveData<Resource<List<CatEntity>>> resourceLiveData;

    @Inject
    public CatsViewModel(CatsRepositoryImpl catsRepository) {
        this.catsRepository = catsRepository;
        resourceLiveData = catsRepository.loadCats();
    }

    public LiveData<Resource<List<CatEntity>>> getCats() {
//        return resourceLiveData;
        return catsRepository.loadCats();
    }

    public void deleteCat(String catId) {
        catsRepository.deleteCatById(catId);
    }
}
