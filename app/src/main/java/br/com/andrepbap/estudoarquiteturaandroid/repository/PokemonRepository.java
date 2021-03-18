package br.com.andrepbap.estudoarquiteturaandroid.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.com.andrepbap.estudoarquiteturaandroid.database.AppDatabase;
import br.com.andrepbap.estudoarquiteturaandroid.database.BaseAsyncTask;
import br.com.andrepbap.estudoarquiteturaandroid.database.PokemonDAO;
import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonListModel;
import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonModel;
import br.com.andrepbap.estudoarquiteturaandroid.webclient.WebClient;

public class PokemonRepository {
    public static final String GET_ALL_PATH = "pokemon";

    private final PokemonDAO pokemonDAO;
    private final WebClient<PokemonListModel> webClient;
    private final MutableLiveData<Resource<PokemonListModel>> liveData = new MutableLiveData<>();
    final Resource<PokemonListModel> resource = new Resource<>();

    public PokemonRepository(Context context) {
        webClient = new WebClient<>();
        pokemonDAO = AppDatabase.getInstance(context)
                .pokemonDAO();
    }

    public LiveData<Resource<PokemonListModel>> getPokemonList() {

        updateResourceWithLastDataValue();

        new BaseAsyncTask<>(new BaseAsyncTask.BaseAsyncTaskDelegate<List<PokemonModel>>() {
            @Override
            public List<PokemonModel> onAsyncTaskInBackground() {
                return pokemonDAO.getAll();
            }

            @Override
            public void onAsyncTaskFinish(List<PokemonModel> result) {
                resource.data = new PokemonListModel(result);
                liveData.postValue(resource);
                getFromWebClient();
            }
        }).execute();

        return liveData;
    }

    private void getFromWebClient() {
        webClient.get(GET_ALL_PATH, PokemonListModel.class, new BaseCallback<PokemonListModel>() {
            @Override
            public void success(PokemonListModel result) {
                resource.data = result;
                liveData.postValue(resource);
                updateLocalDatabase(result);
            }

            @Override
            public void error(String error) {
                resource.error = error;
                liveData.postValue(resource);
            }
        });
    }

    private void updateLocalDatabase(PokemonListModel result) {
        pokemonDAO.insertAll(result.getResultsAsArray());
    }

    private void updateResourceWithLastDataValue() {
        if (resourceHaveData()) {
            resource.data = liveData.getValue().data;
        }

        resource.error = null;
    }

    private boolean resourceHaveData() {
        return liveData.getValue() != null && liveData.getValue().data != null;
    }
}
