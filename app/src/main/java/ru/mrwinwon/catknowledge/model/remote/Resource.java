package ru.mrwinwon.catknowledge.model.remote;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static ru.mrwinwon.catknowledge.model.remote.Status.ERROR;
import static ru.mrwinwon.catknowledge.model.remote.Status.LOADING;
import static ru.mrwinwon.catknowledge.model.remote.Status.SUCCESS;


public class Resource<T> {

    @NonNull
    public final int status;

    @Nullable
    public final T data;

    @Nullable
    private final String message;

    private Resource(@NonNull int status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(ERROR, data, msg);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null);
    }

    @Nullable
    public String getMessage() {
        return message;
    }
}
