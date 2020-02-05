package ru.mrwinwon.catknowledge.model.remote;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("/facts/random?animal_type=cat&amount=2")
    Call<CatsResponse> loadRandomCatInfo();

//    @GET("/facts/random?animal_type=cat&amount=1")
//    Single<CatsResponse> loadCatInfo();

    // TODO: 03.02.2020 picture
}
