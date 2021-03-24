package br.com.andrepbap.estudoarquiteturaandroid.model;

import java.io.Serializable;
import java.util.List;

public class PokemonListAPIModel implements Serializable {
    private List<PokemonModel> results;

    private String next;

    public List<PokemonModel> getResults() {
        return results;
    }

    public PokemonModel[] getResultsAsArray() {
        return results.toArray(new PokemonModel[0]);
    }

    public String getNextPage() {
        return next;
    }
}
