package ru.mrwinwon.catknowledge.di.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import ru.mrwinwon.catknowledge.app.CatsApp;
import ru.mrwinwon.catknowledge.di.builders.ActivityBuilderModule;
import ru.mrwinwon.catknowledge.di.builders.FragmentBuilderModule;
import ru.mrwinwon.catknowledge.di.module.AppModule;

@Singleton
@Component(modules = {
        AppModule.class,
        AndroidInjectionModule.class,
        ActivityBuilderModule.class,
        FragmentBuilderModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(CatsApp catsApp);
}
