package ru.mrwinwon.catknowledge.di.module;

import android.app.Application;

import androidx.room.Room;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mrwinwon.catknowledge.model.CatsRepository;
import ru.mrwinwon.catknowledge.model.CatsRepositoryImpl;
import ru.mrwinwon.catknowledge.model.local.CatDao;
import ru.mrwinwon.catknowledge.model.local.CatsDatabase;
import ru.mrwinwon.catknowledge.model.remote.ApiConsts;
import ru.mrwinwon.catknowledge.model.remote.ApiService;

@Module(includes = ViewModelModule.class)
public class AppModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(ApiConsts.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.readTimeout(ApiConsts.READ_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.writeTimeout(ApiConsts.WRITE_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        return okHttpClient.build();
    }

    @Provides
    @Singleton
    ApiService provideRetrofit(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConsts.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(ApiService.class);
    }

    @Provides
    @Singleton
    CatsDatabase provideArticleDatabase(Application application) {
        return Room.databaseBuilder(application, CatsDatabase.class, "database.db").build();
    }

    @Provides
    @Singleton
    CatDao provideCatDao(CatsDatabase catsDatabase) {
        return catsDatabase.catDao();
    }
}
