package br.com.andrepbap.estudoarquiteturaandroid.repository;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.CountDownLatch;

import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonListModel;
import br.com.andrepbap.estudoarquiteturaandroid.preferences.PokemonListPreferences;
import br.com.andrepbap.estudoarquiteturaandroid.webclient.Client;
import br.com.andrepbap.estudoarquiteturaandroid.webclient.WebClient;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PokemonRepositoryTest {

    private PokemonRepository repository;

    @Mock
    private Context context;

    private FakeWebClient fakeWebClient;

    @Mock
    private PokemonListPreferences pokemonListPreferences;

    @Before
    public void setUp() {
        BaseCallback<PokemonListModel> baseCallback = new BaseCallback<PokemonListModel>() {
            @Override
            public void success(PokemonListModel object) {
                Assert.assertTrue(true);
            }

            @Override
            public void error(String error) {
                Assert.fail();
            }
        };

        fakeWebClient = new FakeWebClient(baseCallback);
        fakeWebClient.setSuccess(true);
        repository = new PokemonRepository(context, fakeWebClient, pokemonListPreferences);
    }

    @Test
    public void shouldDelegateToPokemonListPreferences() {
        repository.updateListPositionStateWith(666);
        verify(pokemonListPreferences).setLastSeenPosition(666);
    }

    @Test
    public void shouldDelegateToWebClient() {
        repository.getFromWebClient("/italossauro");
    }
}

class FakeWebClient implements Client<PokemonListModel> {

    private final BaseCallback<PokemonListModel> baseCallback;
    private boolean success;

    public FakeWebClient(BaseCallback<PokemonListModel> baseCallback) {
        this.baseCallback = baseCallback;
    }

    void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public void get(String url, Class<PokemonListModel> clazz, BaseCallback<PokemonListModel> callback) {
        if (success) {
            baseCallback.success(null);
        } else {
            baseCallback.error("mock de error");
        }
    }
}