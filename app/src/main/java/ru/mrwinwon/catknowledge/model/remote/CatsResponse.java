package ru.mrwinwon.catknowledge.model.remote;

import java.util.List;

import ru.mrwinwon.catknowledge.model.local.CatEntity;

public class CatsResponse {

//    private CatEntity catEntities;
    private List<CatEntity> catEntities;

//    public void setCatEntities(CatEntity catEntities) {
//        this.catEntities = catEntities;
//    }
//
//    public CatEntity getCatEntities() {
//        return catEntities;
//    }

        public List<CatEntity> getCatEntities() {
        return catEntities;
    }

    public void setCatEntities(List<CatEntity> catEntities) {
        this.catEntities = catEntities;
    }
}
