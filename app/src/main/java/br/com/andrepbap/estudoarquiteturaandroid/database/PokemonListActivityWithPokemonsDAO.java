package br.com.andrepbap.estudoarquiteturaandroid.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonListActivityModel;
import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonListActivityWithPokemons;
import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonModel;

import static androidx.room.OnConflictStrategy.IGNORE;

@Dao
public abstract class PokemonListActivityWithPokemonsDAO {

    @Transaction
    @Query("SELECT * FROM PokemonListActivityModel")
    public abstract List<PokemonListActivityWithPokemons> getPokemonListActivityModelList();

    @Transaction
    @Query("SELECT * FROM PokemonListActivityModel LIMIT 1")
    public abstract LiveData<PokemonListActivityWithPokemons> getPokemonListActivityModel();

    @Transaction
    @Query("SELECT nextPage FROM PokemonListActivityModel WHERE uid = :uid")
    public abstract String getNextPageLink(String uid);

    @Transaction
    @Query("UPDATE PokemonListActivityModel SET lastSeemListPosition = :lastSeemPosition WHERE uid = :uid")
    public abstract int updateLastSeemPosition(int lastSeemPosition, String uid);

    @Transaction
    @Query("UPDATE PokemonListActivityModel SET nextPage = :nextPage WHERE uid = :uid")
    public abstract int updateNextPageLink(String nextPage, String uid);

    @Query("DELETE FROM PokemonListActivityModel")
    abstract int nukePokemonListActivityModel();

    @Query("DELETE FROM PokemonModel")
    abstract int nukePokemonModel();

    @Transaction
    public int nuke() {
        int listResult = nukePokemonListActivityModel();
        int pokemonModelResult = nukePokemonModel();

        return Math.max(listResult, pokemonModelResult);
    }

    @Transaction
    public void insert(PokemonListActivityModel pokemonListActivityModel, List<PokemonModel> pokemonModelList) {

        int update = updateNextPageLink(pokemonListActivityModel.getNextPage(), pokemonListActivityModel.getUid());

        if (update == 0) {
            insert(pokemonListActivityModel);
        }

        for (PokemonModel pokemonModel : pokemonModelList) {
            pokemonModel.setActivityId(pokemonListActivityModel.getUid());
            insert(pokemonModel);
        }
    }

    @Insert(onConflict = IGNORE)
    abstract void insert(PokemonListActivityModel pokemonListActivityModel);

    @Insert(onConflict = IGNORE)
    abstract void insert(PokemonModel pokemonModel);
}
