package ru.mrwinwon.catknowledge.model.local;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "avatar")
public class CatAvatar {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("file")
    @ColumnInfo(name = "avatar")
    @Expose
    private String avatar;

    @SerializedName("_id")
    @ColumnInfo(name = "catId")
    @Expose
    private String catId;

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    @Override
    public String toString() {
        return "CatAvatar{" +
                "id=" + id +
                ", avatar='" + avatar + '\'' +
                ", catId='" + catId + '\'' +
                '}';
    }
}
