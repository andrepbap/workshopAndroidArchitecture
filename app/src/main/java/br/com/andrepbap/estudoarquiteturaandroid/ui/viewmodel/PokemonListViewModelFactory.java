package br.com.andrepbap.estudoarquiteturaandroid.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonListModel;
import br.com.andrepbap.estudoarquiteturaandroid.repository.Repository;

public class PokemonListViewModelFactory implements ViewModelProvider.Factory {

    private Repository<PokemonListModel> repository;

    public PokemonListViewModelFactory(Repository<PokemonListModel> repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PokemonListViewModel(repository);
    }
}
