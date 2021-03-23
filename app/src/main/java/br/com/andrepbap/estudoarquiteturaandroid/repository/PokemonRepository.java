package br.com.andrepbap.estudoarquiteturaandroid.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.com.andrepbap.estudoarquiteturaandroid.database.AppDatabase;
import br.com.andrepbap.estudoarquiteturaandroid.database.BaseAsyncTask;
import br.com.andrepbap.estudoarquiteturaandroid.database.PokemonDAO;
import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonListModel;
import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonModel;
import br.com.andrepbap.estudoarquiteturaandroid.webclient.WebClient;

public class PokemonRepository {
    public static final String GET_ALL_URL = "https://pokeapi.co/api/v2/pokemon";
    public String nextPage;

    private final PokemonDAO pokemonDAO;
    private final WebClient<PokemonListModel> webClient;

    private MediatorLiveData<Resource<PokemonListModel>> mediator;
    private final MutableLiveData<Resource<PokemonListModel>> webClientLiveData = new MutableLiveData<>();

    public PokemonRepository(Context context) {
        webClient = new WebClient<>();
        pokemonDAO = AppDatabase.getInstance(context)
                .pokemonDAO();

        setupPokemonListMediator();
    }

    public LiveData<Resource<PokemonListModel>> getPokemonList() {
        return mediator;
    }

    public void paginate() {
        getFromWebClient(nextPage);
    }

    public void resetList() {
        new BaseAsyncTask<>(new BaseAsyncTask.BaseAsyncTaskDelegate<Integer>() {
            @Override
            public Integer onAsyncTaskInBackground() {
                return pokemonDAO.nukeTable();
            }

            @Override
            public void onAsyncTaskFinish(Integer result) {
                getFromWebClient(GET_ALL_URL);
            }
        }).execute();
    }

    private void setupPokemonListMediator() {
        mediator = new MediatorLiveData<>();

        mediator.addSource(getFromLocalDatabase(), pokemonModelArray -> {
            PokemonListModel pokemonListModel = new PokemonListModel(pokemonModelArray);
            mediator.postValue(new Resource<>(pokemonListModel));
        });

        mediator.addSource(webClientLiveData, resource -> {
            if (resource.error != null && mediator.getValue() != null) {
                mediator.postValue(new Resource<>(mediator.getValue().data, resource.error));
            }
        });

        getFromWebClient(GET_ALL_URL);
    }

    private LiveData<List<PokemonModel>> getFromLocalDatabase() {
          return pokemonDAO.getAll();
    }

    private void getFromWebClient(String path) {

        webClient.get(path, PokemonListModel.class, new BaseCallback<PokemonListModel>() {
            @Override
            public void success(PokemonListModel result) {
                updateLocalDatabase(result);
                nextPage = result.getNextPage();
            }

            @Override
            public void error(String error) {
                webClientLiveData.postValue(new Resource<>(error));
            }
        });
    }

    private void updateLocalDatabase(PokemonListModel result) {
        pokemonDAO.insertAll(result.getResultsAsArray());
    }
}
