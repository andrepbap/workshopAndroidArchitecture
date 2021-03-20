package br.com.andrepbap.estudoarquiteturaandroid.ui.viewmodel;

import androidx.lifecycle.LiveData;

import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonListModel;
import br.com.andrepbap.estudoarquiteturaandroid.repository.PokemonRepository;
import br.com.andrepbap.estudoarquiteturaandroid.repository.Resource;

public class PokemonListViewModel extends androidx.lifecycle.ViewModel {

    private final PokemonRepository repository;

    public PokemonListViewModel(PokemonRepository repository) {
        this.repository = repository;
    }

    public LiveData<Resource<PokemonListModel>> getPokemonList() {
        return repository.getPokemonList();
    }

    public void paginate() {
        repository.paginate();
    }
}
