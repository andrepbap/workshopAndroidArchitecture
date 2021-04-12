package br.com.andrepbap.estudoarquiteturaandroid.ui.viewmodel;

import androidx.lifecycle.LiveData;

import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonListState;
import br.com.andrepbap.estudoarquiteturaandroid.repository.PokemonRepository;
import br.com.andrepbap.estudoarquiteturaandroid.repository.Resource;

public class PokemonListViewModel extends androidx.lifecycle.ViewModel {

    private final PokemonRepository repository;

    PokemonListViewModel(PokemonRepository repository) {
        this.repository = repository;
    }

    public LiveData<Resource<PokemonListState>> getPokemonList() {
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
