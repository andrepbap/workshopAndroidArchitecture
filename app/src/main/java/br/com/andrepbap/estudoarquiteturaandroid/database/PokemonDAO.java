package br.com.andrepbap.estudoarquiteturaandroid.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonModel;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface PokemonDAO {
    @Query("SELECT * FROM PokemonModel")
    List<PokemonModel> getAll();

    @Insert(onConflict = REPLACE)
    void insertAll(PokemonModel... pokemonList);
}
