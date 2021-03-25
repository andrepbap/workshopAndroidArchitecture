package br.com.andrepbap.estudoarquiteturaandroid.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.com.andrepbap.estudoarquiteturaandroid.database.AppDatabase;
import br.com.andrepbap.estudoarquiteturaandroid.database.BaseAsyncTask;
import br.com.andrepbap.estudoarquiteturaandroid.database.PokemonDAO;
import br.com.andrepbap.estudoarquiteturaandroid.database.PokemonListActivityDAO;
import br.com.andrepbap.estudoarquiteturaandroid.database.PokemonListActivityWithPokemonsDAO;
import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonListActivityModel;
import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonListActivityWithPokemons;
import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonListAPIModel;
import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonModel;
import br.com.andrepbap.estudoarquiteturaandroid.webclient.WebClient;

public class PokemonRepository {
    public static final String GET_ALL_URL = "https://pokeapi.co/api/v2/pokemon";

    private final PokemonListActivityWithPokemonsDAO pokemonListActivityWithPokemonsDAO;
    private final PokemonDAO pokemonDAO;
    private final PokemonListActivityDAO pokemonListActivityModelDAO;
    private final WebClient<PokemonListAPIModel> webClient;

    private MediatorLiveData<Resource<List<PokemonModel>>> mediator;
    private final MutableLiveData<Resource<PokemonListActivityWithPokemons>> webClientLiveData = new MutableLiveData<>();

    private final String activityId;

    public PokemonRepository(Context context, String activityId) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        webClient = new WebClient<>();
        pokemonListActivityWithPokemonsDAO = appDatabase.pokemonListActivityWithPokemonsDAO();
        pokemonDAO = appDatabase.pokemonDAO();
        pokemonListActivityModelDAO = appDatabase.pokemonListActivityDAO();
        this.activityId = activityId;

        setupPokemonListMediator();
    }

    public LiveData<Resource<List<PokemonModel>>> getPokemonList() {
        return mediator;
    }

    public LiveData<PokemonListActivityModel> getPokemonListActivityState() {
        return pokemonListActivityModelDAO.getActivity(activityId);
    }

    public void paginate() {
        new BaseAsyncTask<>(new BaseAsyncTask.BaseAsyncTaskDelegate<String>() {
            @Override
            public String onAsyncTaskInBackground() {
                return pokemonListActivityWithPokemonsDAO.getNextPageLink(activityId);
            }

            @Override
            public void onAsyncTaskFinish(String nextPageLink) {
                getFromWebClient(nextPageLink);
            }
        }).execute();
    }

    public void updateListPositionStateWith(int position) {
        new BaseAsyncTask<>(new BaseAsyncTask.BaseAsyncTaskDelegate<Integer>() {
            @Override
            public Integer onAsyncTaskInBackground() {
                return pokemonListActivityWithPokemonsDAO.updateLastSeemPosition(position, activityId);
            }

            @Override
            public void onAsyncTaskFinish(Integer result) {

            }
        }).execute();
    }

    public void resetList() {
        new BaseAsyncTask<>(new BaseAsyncTask.BaseAsyncTaskDelegate<Integer>() {
            @Override
            public Integer onAsyncTaskInBackground() {
                return pokemonListActivityWithPokemonsDAO.nuke();
            }

            @Override
            public void onAsyncTaskFinish(Integer result) {
                getFromWebClient(GET_ALL_URL);
            }
        }).execute();
    }

    private void setupPokemonListMediator() {
        mediator = new MediatorLiveData<>();

        mediator.addSource(getFromLocalDatabase(), pokemonList -> {
            mediator.postValue(new Resource<>(pokemonList));
        });

        mediator.addSource(webClientLiveData, resource -> {
            if (resource.error != null && mediator.getValue() != null) {
                mediator.postValue(new Resource<>(mediator.getValue().data, resource.error));
            }
        });

        getFromWebClient(GET_ALL_URL);
    }

    private LiveData<List<PokemonModel>> getFromLocalDatabase() {
          return pokemonDAO.getAllByActivity(activityId);
    }

    private void getFromWebClient(String path) {

        webClient.get(path, PokemonListAPIModel.class, new BaseCallback<PokemonListAPIModel>() {
            @Override
            public void success(PokemonListAPIModel result) {
                updateLocalDatabase(result);
            }

            @Override
            public void error(String error) {
                webClientLiveData.postValue(new Resource<>(error));
            }
        });
    }

    private void updateLocalDatabase(PokemonListAPIModel result) {
        PokemonListActivityModel pokemonListActivityModel = new PokemonListActivityModel(activityId, result.getNextPage());
        pokemonListActivityWithPokemonsDAO.insert(pokemonListActivityModel, result.getResults());
    }
}
