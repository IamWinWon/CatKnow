package ru.mrwinwon.catknowledge.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import dagger.android.AndroidInjector;
import ru.mrwinwon.catknowledge.R;
import ru.mrwinwon.catknowledge.databinding.MainFragmentBinding;
import ru.mrwinwon.catknowledge.ui.main.base.BaseFragment;
import ru.mrwinwon.catknowledge.ui.main.recycler.CatsListAdapter;
import ru.mrwinwon.catknowledge.viewModel.CatsViewModel;

import static ru.mrwinwon.catknowledge.model.remote.Status.ERROR;
import static ru.mrwinwon.catknowledge.model.remote.Status.SUCCESS;


public class CatsFragment extends BaseFragment<CatsViewModel, MainFragmentBinding> {

    public static CatsFragment newInstance() {
        return new CatsFragment();
    }


    @Override
    protected Class<CatsViewModel> getViewModel() {
        return CatsViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.main_fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        dataBinding.rvCats.setLayoutManager(new LinearLayoutManager(getActivity()));
        dataBinding.rvCats.setAdapter(new CatsListAdapter(viewModel));
        dataBinding.btnAddCat.setOnClickListener(v -> viewModel.getCats());
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel.getCats()
                .observe(getViewLifecycleOwner(), listResource -> {
                    if (listResource != null && (listResource.status == ERROR || listResource.status == SUCCESS)) {
                        dataBinding.pbFetchingData.setVisibility(View.GONE);
                    }

                    dataBinding.setResource(listResource);

                    if(null != dataBinding.rvCats.getAdapter() && dataBinding.rvCats.getAdapter().getItemCount() > 0){
                        dataBinding.tvError.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return null;
    }
}
