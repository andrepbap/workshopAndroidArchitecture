package br.com.andrepbap.estudoarquiteturaandroid.webclient;

import com.google.gson.Gson;

import java.io.IOException;

import br.com.andrepbap.estudoarquiteturaandroid.repository.BaseCallback;
import okhttp3.ResponseBody;
import retrofit2.Callback;

public class PokemonClient<T> implements Client<T> {
    public static final int LIMIT = 20;
    private final PokemonService pokemonService;

    public PokemonClient(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @Override
    public void getPokemonList(int offset, Class<T> clazz, BaseCallback<T> callback) {

        pokemonService
                .getAll(LIMIT, offset)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        if (!response.isSuccessful()) {
                            callback.error(response.message());
                            return;
                        }

                        try {
                            callback.success(new Gson().fromJson(response.body().string(), clazz));
                        } catch (IOException e) {
                            callback.error(e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                        callback.error(t.getMessage());
                    }
                });
    }
}
