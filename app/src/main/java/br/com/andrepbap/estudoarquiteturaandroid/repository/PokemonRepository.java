package br.com.andrepbap.estudoarquiteturaandroid.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.com.andrepbap.estudoarquiteturaandroid.database.AppDatabase;
import br.com.andrepbap.estudoarquiteturaandroid.database.PokemonDAO;
import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonListModel;
import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonModel;
import br.com.andrepbap.estudoarquiteturaandroid.webclient.WebClient;

public class PokemonRepository {
    public static final String GET_ALL_PATH = "pokemon";

    private final PokemonDAO pokemonDAO;
    private final WebClient<PokemonListModel> webClient;

    private final MediatorLiveData<Resource<PokemonListModel>> mediator = new MediatorLiveData<>();

    public PokemonRepository(Context context) {
        webClient = new WebClient<>();
        pokemonDAO = AppDatabase.getInstance(context)
                .pokemonDAO();
    }

    public LiveData<Resource<PokemonListModel>> getPokemonList() {

        mediator.addSource(getFromLocalDatabase(), pokemonModelArray -> {
            PokemonListModel pokemonListModel = new PokemonListModel(pokemonModelArray);
            mediator.postValue(new Resource<>(pokemonListModel));
        });

        mediator.addSource(getFromWebClient(), resource -> {
            if (resource.error != null && mediator.getValue() != null) {
                mediator.postValue(new Resource<>(mediator.getValue().data, resource.error));
            }
        });

        return mediator;
    }

    private LiveData<List<PokemonModel>> getFromLocalDatabase() {
          return pokemonDAO.getAll();
    }

    private LiveData<Resource<PokemonListModel>> getFromWebClient() {
        MutableLiveData<Resource<PokemonListModel>> liveData = new MutableLiveData<>();

        webClient.get(GET_ALL_PATH, PokemonListModel.class, new BaseCallback<PokemonListModel>() {
            @Override
            public void success(PokemonListModel result) {
                updateLocalDatabase(result);
            }

            @Override
            public void error(String error) {
                liveData.postValue(new Resource<>(error));
            }
        });

        return liveData;
    }

    private void updateLocalDatabase(PokemonListModel result) {
        pokemonDAO.insertAll(result.getResultsAsArray());
    }
}
