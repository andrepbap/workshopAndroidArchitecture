package br.com.andrepbap.estudoarquiteturaandroid.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonDetailModel;
import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonListState;
import br.com.andrepbap.estudoarquiteturaandroid.repository.PokemonRepository;
import br.com.andrepbap.estudoarquiteturaandroid.repository.Resource;

public class PokemonViewModel extends AndroidViewModel {

    private final PokemonRepository repository;

    public PokemonViewModel(@NonNull Application application) {
        super(application);
        repository = new PokemonRepository(getApplication());
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

    public LiveData<Resource<PokemonDetailModel>> getPokemonDetails(String name) {
        return repository.getPokemonDetailsFromWebClient(name);
    }
}
