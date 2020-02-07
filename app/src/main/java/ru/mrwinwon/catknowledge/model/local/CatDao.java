package ru.mrwinwon.catknowledge.model.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface CatDao {

    @Query("SELECT * FROM cats ORDER BY createdAt DESC")
    LiveData<List<CatEntity>> loadCats();

    @Query("SELECT * FROM cats WHERE avatar = :avatar")
    Single<CatEntity> loadEmptyCat(String avatar);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveCat(CatEntity catEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAvatar(CatAvatar catAvatar);

    @Query("DELETE FROM cats WHERE id = :catId")
    void deleteByCatId(String catId);
}
