package br.com.andrepbap.estudoarquiteturaandroid.model;

import java.io.Serializable;
import java.util.List;

public class PokemonListModel implements Serializable {

    private List<PokemonModel> results;

    public List<PokemonModel> getResults() {
        return results;
    }
}
