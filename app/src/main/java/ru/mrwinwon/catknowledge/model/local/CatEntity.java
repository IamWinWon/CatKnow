package ru.mrwinwon.catknowledge.model.local;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "cats")
public class CatEntity {

    @NonNull
    @PrimaryKey
    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("text")
    @Expose
    private String text;

    @SerializedName("createdAt")
    @Expose
    private String createdAt;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
