package br.com.andrepbap.estudoarquiteturaandroid.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import br.com.andrepbap.estudoarquiteturaandroid.repository.PokemonRepository;

public class PokemonListViewModelFactory implements ViewModelProvider.Factory {

    private final PokemonRepository repository;

    public PokemonListViewModelFactory(PokemonRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PokemonListViewModel(repository);
    }
}
