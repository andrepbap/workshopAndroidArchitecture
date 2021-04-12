package br.com.andrepbap.estudoarquiteturaandroid.ui.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonListState;
import br.com.andrepbap.estudoarquiteturaandroid.repository.PokemonRepository;
import br.com.andrepbap.estudoarquiteturaandroid.repository.Resource;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class PokemonListViewModelTest {
    @Mock
    private PokemonRepository pokemonRepository;

    private PokemonListViewModel pokemonListViewModel;

    @Rule
    public TestRule testRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        pokemonListViewModel = new PokemonListViewModel(pokemonRepository);
    }

    @Test
    public void shouldReturnRepositoryContent(){
        LiveData<Resource<PokemonListState>> emptyLiveData = new MutableLiveData<>();

        Mockito.when(pokemonRepository.getPokemonList()).thenReturn(emptyLiveData);

        assertNull(pokemonListViewModel.getPokemonList().getValue());
    }
}