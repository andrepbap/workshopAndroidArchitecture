package br.com.andrepbap.estudoarquiteturaandroid.model;

import java.io.Serializable;
import java.util.List;

public class PokemonListModel implements Serializable {

    private List<PokemonModel> results;

    public PokemonListModel(List<PokemonModel> results) {
        this.results = results;
    }

    public List<PokemonModel> getResults() {
        return results;
    }

    public PokemonModel[] getResultsAsArray() {
        return results.toArray(new PokemonModel[0]);
    }
}
