package br.com.andrepbap.estudoarquiteturaandroid.repository;

import android.content.Context;

import java.util.List;

import br.com.andrepbap.estudoarquiteturaandroid.database.AppDatabaseInstanceProvider;
import br.com.andrepbap.estudoarquiteturaandroid.database.BaseAsyncTask;
import br.com.andrepbap.estudoarquiteturaandroid.database.PokemonDAO;
import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonListModel;
import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonModel;
import br.com.andrepbap.estudoarquiteturaandroid.webclient.WebClient;

public class PokemonRepository {
    public static final String GET_ALL_PATH = "pokemon";

    private final PokemonDAO pokemonDAO;
    private final WebClient<PokemonListModel> webClient;

    public PokemonRepository(Context context) {
        webClient = new WebClient<>();
        pokemonDAO = AppDatabaseInstanceProvider.getInstance(context)
                .getDatabase()
                .pokemonDAO();
    }

    public void getPokemonList(BaseCallback<PokemonListModel> callback) {
        new BaseAsyncTask<>(new BaseAsyncTask.BaseAsyncTaskDelegate<List<PokemonModel>>() {
            @Override
            public List<PokemonModel> onAsyncTaskInBackground() {
                return pokemonDAO.getAll();
            }

            @Override
            public void onAsyncTaskFinish(List<PokemonModel> result) {
                callback.success(new PokemonListModel(result));
                getFromWebClient(callback);
            }
        }).execute();
    }

    private void getFromWebClient(BaseCallback<PokemonListModel> callback) {
        webClient.get(GET_ALL_PATH, PokemonListModel.class, new BaseCallback<PokemonListModel>() {
            @Override
            public void success(PokemonListModel result) {
                callback.success(result);
                updateLocalDatabase(result);
            }

            @Override
            public void error(String error) {

            }
        });
    }

    private void updateLocalDatabase(PokemonListModel result) {
        pokemonDAO.insertAll(result.getResultsAsArray());
    }
}
