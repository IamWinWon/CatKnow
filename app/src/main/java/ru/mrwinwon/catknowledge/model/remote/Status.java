package ru.mrwinwon.catknowledge.model.remote;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({Status.SUCCESS, Status.ERROR, Status.LOADING})
@Retention(RetentionPolicy.SOURCE)
public @interface Status {
    int SUCCESS = 0;
    int ERROR = 1;
    int LOADING = 2;
}