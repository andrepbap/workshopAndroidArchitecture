package br.com.andrepbap.estudoarquiteturaandroid.ui.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonListModel;
import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonListState;
import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonModel;
import br.com.andrepbap.estudoarquiteturaandroid.repository.PokemonRepository;
import br.com.andrepbap.estudoarquiteturaandroid.repository.Resource;

import static org.junit.Assert.*;

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

    @Test
    public void shouldReturnRepositoryData(){
        MutableLiveData<Resource<PokemonListState>> liveData = getData(Collections.emptyList());

        Mockito.when(pokemonRepository.getPokemonList()).thenReturn(liveData);

        assertEquals(0, getRepositoryResults().size());
    }

    @Test
    public void shouldReturnRepositoryDataNotEmpty(){
        PokemonModel pokemonModel = new PokemonModel();
        pokemonModel.setName("Jihazad");

        List<PokemonModel> models = Arrays.asList(pokemonModel);
        MutableLiveData<Resource<PokemonListState>> liveData = getData(models);

        Mockito.when(pokemonRepository.getPokemonList()).thenReturn(liveData);

        assertEquals(1, getRepositoryResults().size());
        assertEquals("Jihazad", getRepositoryResults().get(0).getName());
    }

    private List<PokemonModel> getRepositoryResults() {
        return getRepositoryData().getPokemonListModel().getResults();
    }

    private PokemonListState getRepositoryData() {
        return pokemonListViewModel.getPokemonList().getValue().data;
    }

    @NotNull
    private MutableLiveData<Resource<PokemonListState>> getData(List<PokemonModel> results) {
        MutableLiveData<Resource<PokemonListState>> liveData = new MutableLiveData<>();
        PokemonListModel pokemonListModel = new PokemonListModel(results);
        PokemonListState pokemonListState = new PokemonListState(pokemonListModel, 0);
        liveData.setValue(new Resource<>(pokemonListState));
        return liveData;
    }

}