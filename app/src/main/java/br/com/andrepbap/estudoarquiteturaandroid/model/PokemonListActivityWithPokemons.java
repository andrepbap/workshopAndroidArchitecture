package br.com.andrepbap.estudoarquiteturaandroid.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.List;

public class PokemonListActivityWithPokemons implements Serializable {

    @Embedded
    private PokemonListActivityModel pokemonListActivityModel;
    @Relation(
            parentColumn = "uid",
            entityColumn = "activityId"
    )
    private List<PokemonModel> results;

    public List<PokemonModel> getResults() {
        return results;
    }

    public void setResults(List<PokemonModel> results) {
        this.results = results;
    }

    public PokemonListActivityModel getPokemonListActivityModel() {
        return pokemonListActivityModel;
    }

    public void setPokemonListActivityModel(PokemonListActivityModel pokemonListActivityModel) {
        this.pokemonListActivityModel = pokemonListActivityModel;
    }
}
