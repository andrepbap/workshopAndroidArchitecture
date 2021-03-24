package br.com.andrepbap.estudoarquiteturaandroid.ui.viewmodel;

import androidx.lifecycle.LiveData;

import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonListActivityWithPokemons;
import br.com.andrepbap.estudoarquiteturaandroid.repository.PokemonRepository;
import br.com.andrepbap.estudoarquiteturaandroid.repository.Resource;

public class PokemonListViewModel extends androidx.lifecycle.ViewModel {

    private final PokemonRepository repository;

    public PokemonListViewModel(PokemonRepository repository) {
        this.repository = repository;
    }

    public LiveData<Resource<PokemonListActivityWithPokemons>> getPokemonList() {
        return repository.getPokemonList();
    }

    public void resetList() {
        repository.resetList();
    }

    public void paginate() {
        repository.paginate();
    }

    public void updateListPositionStateWith(int position) {
        repository.updateListPositionStateWith(position);
    }
}
