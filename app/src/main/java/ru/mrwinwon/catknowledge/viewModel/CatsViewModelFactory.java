package ru.mrwinwon.catknowledge.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import ru.mrwinwon.catknowledge.model.CatsRepository;

@Singleton
public class CatsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> classProviderMap;

    @Inject
    CatsViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> classProviderMap) {
        this.classProviderMap = classProviderMap;
    }

//    private CatsRepository catsRepository;

//    public CatsViewModelFactory(CatsRepository catsRepository) {
//        super();
//        this.catsRepository = catsRepository;
//    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//        return (T) new CatsViewModel(catsRepository);
        Provider<? extends ViewModel> creator = classProviderMap.get(modelClass);
        if (creator == null) {
            for (Map.Entry<Class<? extends ViewModel>, Provider<ViewModel>> entry : classProviderMap.entrySet()) {
                if (modelClass.isAssignableFrom(entry.getKey())) {
                    creator = entry.getValue();
                    break;
                }
            }
        }
        if (creator == null) {
            throw new IllegalArgumentException("unknown model class " + modelClass);
        }
        try {
            return (T) creator.get();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
