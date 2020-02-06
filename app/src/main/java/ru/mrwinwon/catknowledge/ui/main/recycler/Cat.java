package ru.mrwinwon.catknowledge.ui.main.recycler;

import com.google.gson.annotations.SerializedName;

public class Cat {

    @SerializedName("_id")
    private String id;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("text")
    private String description;

    @SerializedName("createdAt")
    private String createdAt;

    public Cat(String id, String avatar, String description, String createdAt) {
        this.id = id;
        this.avatar = avatar;
        this.description = description;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
