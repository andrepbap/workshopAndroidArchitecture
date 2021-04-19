package br.com.andrepbap.estudoarquiteturaandroid.repository;

import android.content.Context;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class PokemonRepositoryTest {

    private PokemonRepository repository;
    @Mock
    private Context context;

    @Before
    public void setUp() {
        repository = new PokemonRepository(context);
    }
}