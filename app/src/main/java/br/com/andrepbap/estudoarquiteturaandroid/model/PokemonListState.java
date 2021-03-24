package br.com.andrepbap.estudoarquiteturaandroid.model;

public class PokemonListState {
    private PokemonListActivityWithPokemons pokemonListActivityWithPokemons;
    private int lastSeemPosition;

    public PokemonListState(PokemonListActivityWithPokemons pokemonListActivityWithPokemons, int lastSeemPosition) {
        this.pokemonListActivityWithPokemons = pokemonListActivityWithPokemons;
        this.lastSeemPosition = lastSeemPosition;
    }

    public PokemonListActivityWithPokemons getPokemonListActivityWithPokemons() {
        return pokemonListActivityWithPokemons;
    }

    public int getLastSeemPosition() {
        return lastSeemPosition;
    }
}
