package ru.mrwinwon.catknowledge.model.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CatEntity.class}, version = 1)
public abstract class CatsDatabase extends RoomDatabase {
    public abstract CatDao catDao();
}
