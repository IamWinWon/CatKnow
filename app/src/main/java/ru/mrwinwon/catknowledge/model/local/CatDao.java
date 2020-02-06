package ru.mrwinwon.catknowledge.model.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CatDao {

    @Query("SELECT * FROM cats")
    LiveData<List<CatEntity>> loadCats();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveCats(List<CatEntity> catEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveCat(CatEntity catEntity);

    @Delete
    void deleteCat(CatEntity catEntity);

    @Query("DELETE FROM cats WHERE id = :catId")
    void deleteByCatId(String catId);
}
