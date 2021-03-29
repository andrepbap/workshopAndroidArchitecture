package br.com.andrepbap.estudoarquiteturaandroid.ui;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonListModel;
import br.com.andrepbap.estudoarquiteturaandroid.repository.BaseCallback;
import br.com.andrepbap.estudoarquiteturaandroid.repository.PokemonRepository;

public class PokemonListViewModel extends ViewModel {
    private PokemonRepository pokemonRepository;

    public PokemonListViewModel(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    public LiveData<PokemonListModel> getPokemonList(){
        MutableLiveData<PokemonListModel> liveData = new MutableLiveData<>();
        pokemonRepository.getPokemonList(new BaseCallback<PokemonListModel>() {
            @Override
            public void success(PokemonListModel pokemonListModel) {
                liveData.postValue(pokemonListModel);
            }

            @Override
            public void error(String error) {
//                Toast.makeText(PokemonListActivity.this, error, Toast.LENGTH_LONG).show();
            }
        });
        return liveData;
    }
}
