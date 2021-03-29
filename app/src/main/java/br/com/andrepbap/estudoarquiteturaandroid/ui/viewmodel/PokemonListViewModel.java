package br.com.andrepbap.estudoarquiteturaandroid.ui.viewmodel;

import androidx.lifecycle.LiveData;

import java.util.List;

import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonListActivityModel;
import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonModel;
import br.com.andrepbap.estudoarquiteturaandroid.repository.PokemonRepository;
import br.com.andrepbap.estudoarquiteturaandroid.repository.Resource;

public class PokemonListViewModel extends androidx.lifecycle.ViewModel {

    private final PokemonRepository repository;
    private int currentListPosition;

    public PokemonListViewModel(PokemonRepository repository) {
        this.repository = repository;
    }

    public LiveData<Resource<List<PokemonModel>>> getPokemonList() {
        return repository.getPokemonList();
    }

    public void resetList() {
        repository.resetList();
    }

    public void paginate() {
        repository.paginate();
    }

    public LiveData<PokemonListActivityModel> getPokemonListActivityState() {
        return repository.getPokemonListActivityState();
    }

    public void setCurrentListPosition(int currentListPosition) {
        this.currentListPosition = currentListPosition;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        updateListPosition(currentListPosition);
    }

    public void updateListPosition(int currentListPosition) {
        repository.updateListPositionStateWith(currentListPosition);
    }
}
