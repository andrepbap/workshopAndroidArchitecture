package br.com.andrepbap.estudoarquiteturaandroid.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonModel;

import static androidx.room.OnConflictStrategy.IGNORE;

@Dao
public interface PokemonDAO {
    @Query("SELECT * FROM PokemonModel")
    LiveData<List<PokemonModel>> getAll();

    @Insert(onConflict = IGNORE)
    void insertAll(PokemonModel... pokemonList);

    @Query("DELETE FROM PokemonModel")
    int nukeTable();
}
