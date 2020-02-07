package ru.mrwinwon.catknowledge.model.remote;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Url;
import ru.mrwinwon.catknowledge.model.local.CatAvatar;
import ru.mrwinwon.catknowledge.model.local.CatEntity;

public interface ApiService {

    @GET("/facts/random?animal_type=cat&amount=1")
    Single<CatEntity> loadCatInfo();

    @GET
    Single<CatAvatar> loadCatAvatar(@Url String url);
}
