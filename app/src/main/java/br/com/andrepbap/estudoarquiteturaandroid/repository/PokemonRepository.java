package br.com.andrepbap.estudoarquiteturaandroid.repository;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.com.andrepbap.estudoarquiteturaandroid.database.AppDatabase;
import br.com.andrepbap.estudoarquiteturaandroid.database.BaseAsyncTask;
import br.com.andrepbap.estudoarquiteturaandroid.database.PokemonDAO;
import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonListModel;
import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonListState;
import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonModel;
import br.com.andrepbap.estudoarquiteturaandroid.preferences.PokemonListPreferences;
import br.com.andrepbap.estudoarquiteturaandroid.webclient.Client;
import br.com.andrepbap.estudoarquiteturaandroid.webclient.WebClient;

public class PokemonRepository {
    public static final String GET_ALL_URL = "https://pokeapi.co/api/v2/pokemon";

    private final PokemonDAO pokemonDAO;
    private final Client<PokemonListModel> webClient;

    private MediatorLiveData<Resource<PokemonListState>> mediator;
    private final MutableLiveData<Resource<PokemonListModel>> webClientLiveData = new MutableLiveData<>();
    private final PokemonListPreferences pokemonListPreferences;

    public PokemonRepository(Context context, Client<PokemonListModel> client, PokemonListPreferences pokemonListPreferences) {
        this.webClient = client;
        pokemonDAO = AppDatabase.getInstance(context)
                .pokemonDAO();
        this.pokemonListPreferences = pokemonListPreferences;

        setupPokemonListMediator();
    }

    public LiveData<Resource<PokemonListState>> getPokemonList() {
        return mediator;
    }

    public void paginate() {
        getFromWebClient(pokemonListPreferences.getNextPageLink());
    }

    public void updateListPositionStateWith(int position) {
        pokemonListPreferences.setLastSeenPosition(position);
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
            PokemonListState pokemonListState = new PokemonListState(pokemonListModel, pokemonListPreferences.getLastSeemPosition());
            mediator.postValue(new Resource<>(pokemonListState));
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

    void getFromWebClient(String path) {
        webClient.get(path, PokemonListModel.class, new BaseCallback<PokemonListModel>() {
            @Override
            public void success(PokemonListModel result) {
                updateLocalDatabase(result);
                updateNextPageLink(result);
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

    private void updateNextPageLink(PokemonListModel result) {
        String savedNextPageLinkOffset = Uri.parse(pokemonListPreferences.getNextPageLink()).getQueryParameter("offset");
        String nextPageLinkOffset = Uri.parse(result.getNextPage()).getQueryParameter("offset");

//        if (savedNextPageLinkOffset == null) {
            pokemonListPreferences.setNextPageLink(result.getNextPage());
//        } else if (Integer.parseInt(nextPageLinkOffset) > Integer.parseInt(savedNextPageLinkOffset)) {
//            pokemonListPreferences.setNextPageLink(result.getNextPage());
//        }
    }
}
