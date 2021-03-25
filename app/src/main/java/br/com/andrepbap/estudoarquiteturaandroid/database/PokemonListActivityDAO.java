package br.com.andrepbap.estudoarquiteturaandroid.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonListActivityModel;

@Dao
public interface PokemonListActivityDAO {
    @Query("SELECT uid, lastSeemListPosition FROM PokemonListActivityModel WHERE uid = :activityId")
    LiveData<PokemonListActivityModel> getActivity(String activityId);
}
