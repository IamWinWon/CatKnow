package ru.mrwinwon.catknowledge.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import ru.mrwinwon.catknowledge.viewModel.CatsViewModel;
import ru.mrwinwon.catknowledge.viewModel.CatsViewModelFactory;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CatsViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsArticleListViewModel(CatsViewModel articleListViewModel);


    @Binds
    @SuppressWarnings("unused")
    abstract ViewModelProvider.Factory bindsViewModelFactory(CatsViewModelFactory viewModelFactory);
}
