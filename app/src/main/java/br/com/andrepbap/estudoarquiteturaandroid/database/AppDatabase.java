package br.com.andrepbap.estudoarquiteturaandroid.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonModel;

@Database(entities = {PokemonModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PokemonDAO pokemonDAO();
}
