package br.com.andrepbap.estudoarquiteturaandroid;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.util.List;

import br.com.andrepbap.estudoarquiteturaandroid.database.AppDatabase;
import br.com.andrepbap.estudoarquiteturaandroid.database.PokemonDAO;
import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonModel;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class PokemonDaoTest {

    private AppDatabase db;
    private PokemonDAO dao;

    @Rule
    public TestRule testRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        db = Room.inMemoryDatabaseBuilder(appContext, AppDatabase.class).build();
        dao = db.pokemonDAO();
    }

    @After
    public void destroy() {
        db.close();
    }

    @Test
    public void shouldRemoveZeroRowsIfTableIsEmpty() {
        int removedRows = dao.nukeTable();
        Assert.assertEquals(0, removedRows);
    }

    @Test
    public void shouldInsertAsExpected() {
        PokemonModel model = new PokemonModel();
        model.setName("Jihazad");
        dao.insertAll(model);

        dao.getAll().observeForever(pokemonModels -> {
            assertEquals(1, pokemonModels.size());
            assertEquals("Jihazad", pokemonModels.get(0).getName());
        });
    }

    @Test
    public void shouldNotInsertANullName() {
        PokemonModel model = new PokemonModel();
        model.setName(null);
        dao.insertAll(model);

        dao.getAll().observeForever(pokemonModels -> {
            assertEquals(0, pokemonModels.size());
        });
    }

    @Test
    public void shouldRemoveOneRowOfTable() {
        PokemonModel model = new PokemonModel();
        dao.insertAll(model);

        int removedRows = dao.nukeTable();
        Assert.assertEquals(1, removedRows);
    }


}