package br.com.andrepbap.estudoarquiteturaandroid.ui.viewmodel;

import androidx.lifecycle.LiveData;

import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonListModel;
import br.com.andrepbap.estudoarquiteturaandroid.repository.Repository;
import br.com.andrepbap.estudoarquiteturaandroid.repository.Resource;

public class PokemonListViewModel extends androidx.lifecycle.ViewModel {

    private final Repository<PokemonListModel> repository;

    public PokemonListViewModel(Repository<PokemonListModel> repository) {
        this.repository = repository;
    }

    public LiveData<Resource<PokemonListModel>> getPokemonList() {
        return repository.get("pokemon", PokemonListModel.class);
    }
}
