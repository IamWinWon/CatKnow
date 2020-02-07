package ru.mrwinwon.catknowledge.ui.main.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import dagger.android.HasAndroidInjector;
import dagger.android.support.AndroidSupportInjection;
import ru.mrwinwon.catknowledge.viewModel.CatsViewModel;

public abstract class BaseFragment<V extends ViewModel, D extends ViewDataBinding> extends Fragment implements HasAndroidInjector {

    protected V viewModel;
    protected D dataBinding;

    protected abstract Class<V> getViewModel();

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @LayoutRes
    protected abstract int getLayoutRes();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
        viewModel = (V) new ViewModelProvider(this, viewModelFactory).get(CatsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
        return dataBinding.getRoot();
    }
}
