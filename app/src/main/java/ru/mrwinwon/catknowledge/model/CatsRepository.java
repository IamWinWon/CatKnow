package ru.mrwinwon.catknowledge.model;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.mrwinwon.catknowledge.model.local.CatEntity;
import ru.mrwinwon.catknowledge.model.remote.Resource;

public interface CatsRepository {

    LiveData<Resource<List<CatEntity>>> loadCats();

    void deleteCatById(String catId);
}
