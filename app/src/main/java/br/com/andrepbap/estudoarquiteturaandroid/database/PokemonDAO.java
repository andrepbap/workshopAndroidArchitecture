package br.com.andrepbap.estudoarquiteturaandroid.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonModel;

@Dao
public interface PokemonDAO {
    @Query("SELECT * FROM PokemonModel WHERE activityId = :activityId")
    LiveData<List<PokemonModel>> getAllByActivity(String activityId);
}
