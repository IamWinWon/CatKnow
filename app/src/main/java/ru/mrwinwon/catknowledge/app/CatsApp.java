package ru.mrwinwon.catknowledge.app;

import android.app.Application;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import ru.mrwinwon.catknowledge.di.component.DaggerAppComponent;


public class CatsApp extends Application implements HasAndroidInjector {

    private static CatsApp sInstance;

    public static CatsApp getAppContext() {
        return sInstance;
    }

    private static synchronized void setInstance(CatsApp app) {
        sInstance = app;
    }


    @Inject
    DispatchingAndroidInjector<Object> fragmentDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeComponent();
        setInstance(this);
    }

    private void initializeComponent() {
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);
    }


    @Override
    public AndroidInjector<Object> androidInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
