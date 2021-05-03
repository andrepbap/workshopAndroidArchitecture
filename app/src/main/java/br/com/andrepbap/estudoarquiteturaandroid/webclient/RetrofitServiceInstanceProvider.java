package br.com.andrepbap.estudoarquiteturaandroid.webclient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceInstanceProvider {

    private static RetrofitServiceInstanceProvider instance;

    private final Retrofit retrofit;
    private final PokemonService pokemonService;

    private RetrofitServiceInstanceProvider() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        pokemonService = retrofit.create(PokemonService.class);
    }

    public static RetrofitServiceInstanceProvider getInstance() {
        if (instance == null) {
            instance = new RetrofitServiceInstanceProvider();
        }
        return instance;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public PokemonService getPokemonService() {
        return pokemonService;
    }
}
