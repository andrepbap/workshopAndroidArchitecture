package br.com.andrepbap.estudoarquiteturaandroid.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PokemonListViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;

    public PokemonListViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PokemonViewModel(application);
    }
}
