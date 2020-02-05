package ru.mrwinwon.catknowledge;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import ru.mrwinwon.catknowledge.databinding.MainActivityBinding;
import ru.mrwinwon.catknowledge.ui.main.CatsFragment;
import ru.mrwinwon.catknowledge.ui.main.base.BaseActivity;

public class CatsActivity extends BaseActivity<MainActivityBinding> {

    @Override
    protected int getLayoutRes() {
        return R.layout.main_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, CatsFragment.newInstance())
                    .commitNow();
        }
    }

}
