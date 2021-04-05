package br.com.andrepbap.estudoarquiteturaandroid.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonModel;

@Database(entities = {PokemonModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private final static String DATABASE_NAME = "pokemon.db";
    private static AppDatabase instance;

    public abstract PokemonDAO pokemonDAO();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }
}
