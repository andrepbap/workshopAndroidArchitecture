package br.com.andrepbap.estudoarquiteturaandroid.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import br.com.andrepbap.estudoarquiteturaandroid.database.AppDatabase;
import br.com.andrepbap.estudoarquiteturaandroid.database.PokemonDAO;
import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonListModel;
import br.com.andrepbap.estudoarquiteturaandroid.webclient.WebClient;

public class PokemonRepository {
    public static final String GET_ALL_PATH = "pokemon";

    private final PokemonDAO pokemonDAO;
    private final WebClient<PokemonListModel> webClient;
    private MediatorLiveData<Resource<PokemonListModel>> mediator;

    public PokemonRepository(Context context) {
        webClient = new WebClient<>();
        pokemonDAO = AppDatabase.getInstance(context)
                .pokemonDAO();
    }

    public LiveData<Resource<PokemonListModel>> getPokemonList() {
        mediator = new MediatorLiveData<>();
        setupMediator();
        return mediator;
    }

    private void setupMediator() {
        mediator.addSource(pokemonDAO.getAll(), pokemonModels -> {
            PokemonListModel pokemonListModel = new PokemonListModel(pokemonModels);

            Resource<PokemonListModel> pokemonListModelResource = new Resource<>();
            pokemonListModelResource.setValues(pokemonListModel);

            mediator.postValue(pokemonListModelResource);
        });

        mediator.addSource(getFromWebClient(), resource -> {
            if(resource.getError() != null && mediator.getValue() != null) {
                Resource<PokemonListModel> pokemonListModelResource = new Resource<>();
                pokemonListModelResource.setError(resource.getError());
                pokemonListModelResource.setValues(mediator.getValue().getValues());
                mediator.postValue(pokemonListModelResource);
            }
        });
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
                Resource<PokemonListModel> pokemonListModelResource = new Resource<>();
                pokemonListModelResource.setError(error);
                liveData.postValue(pokemonListModelResource);
            }
        });
        return liveData;
    }

    private void updateLocalDatabase(PokemonListModel result) {
        pokemonDAO.insertAll(result.getResultsAsArray());
    }
}
