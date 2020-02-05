package ru.mrwinwon.catknowledge.di.builders;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ru.mrwinwon.catknowledge.CatsActivity;

@Module
public abstract class ActivityBuilderModule {

    @SuppressWarnings("unused")
    @ContributesAndroidInjector(modules = FragmentBuilderModule.class)
    abstract CatsActivity catsActivity();

}
