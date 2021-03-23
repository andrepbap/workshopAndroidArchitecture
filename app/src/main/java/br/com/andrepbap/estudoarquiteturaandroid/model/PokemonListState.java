package br.com.andrepbap.estudoarquiteturaandroid.model;

public class PokemonListState {
    private PokemonListModel pokemonListModel;
    private int lastSeemPosition;

    public PokemonListState(PokemonListModel pokemonListModel, int lastSeemPosition) {
        this.pokemonListModel = pokemonListModel;
        this.lastSeemPosition = lastSeemPosition;
    }

    public PokemonListModel getPokemonListModel() {
        return pokemonListModel;
    }

    public int getLastSeemPosition() {
        return lastSeemPosition;
    }
}
