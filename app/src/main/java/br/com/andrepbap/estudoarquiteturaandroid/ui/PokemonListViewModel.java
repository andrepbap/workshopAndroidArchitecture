package br.com.andrepbap.estudoarquiteturaandroid.ui;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonListModel;
import br.com.andrepbap.estudoarquiteturaandroid.repository.BaseCallback;
import br.com.andrepbap.estudoarquiteturaandroid.repository.PokemonRepository;
import br.com.andrepbap.estudoarquiteturaandroid.repository.Resource;

public class PokemonListViewModel extends ViewModel {
    private PokemonRepository pokemonRepository;

    public PokemonListViewModel(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    public LiveData<Resource<PokemonListModel>> getPokemonList(){
        return pokemonRepository.getPokemonList();
    }
}
