package ru.mrwinwon.catknowledge.model.remote;

import ru.mrwinwon.catknowledge.model.local.CatEntity;

public class CatsResponse {

    private CatEntity catEntities;

    public void setCatEntities(CatEntity catEntities) {
        this.catEntities = catEntities;
    }

    public CatEntity getCatEntities() {
        return catEntities;
    }

}
