package ru.mrwinwon.catknowledge.di.builders;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ru.mrwinwon.catknowledge.ui.main.CatsFragment;

@Module
public abstract class FragmentBuilderModule {

    @SuppressWarnings("unused")
    @ContributesAndroidInjector
    abstract CatsFragment contributeCatsFragment();

}
