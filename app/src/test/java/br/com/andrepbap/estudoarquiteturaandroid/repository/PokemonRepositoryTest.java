package br.com.andrepbap.estudoarquiteturaandroid.repository;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonListModel;
import br.com.andrepbap.estudoarquiteturaandroid.preferences.PokemonListPreferences;
import br.com.andrepbap.estudoarquiteturaandroid.webclient.Client;
import br.com.andrepbap.estudoarquiteturaandroid.webclient.PokemonClient;
import br.com.andrepbap.estudoarquiteturaandroid.webclient.PokemonService;
import br.com.andrepbap.estudoarquiteturaandroid.webclient.RetrofitServiceInstanceProvider;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PokemonRepositoryTest {

    private PokemonRepository repository;

    @Mock
    private Context context;

    private PokemonClient<PokemonListModel> client;

    @Mock
    PokemonService pokemonService;

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

//        fakeWebClient = new FakeWebClient(baseCallback);
//        fakeWebClient.setSuccess(true);


        Mockito.when(pokemonService.getAll(anyInt(), anyInt())).thenReturn(new MockClient().create());
        client = new PokemonClient<>(pokemonService);

        repository = new PokemonRepository(context, client, pokemonListPreferences);
    }

    @Test
    public void shouldDelegateToPokemonListPreferences() {
        repository.updateListPositionStateWith(666);
        verify(pokemonListPreferences).setLastSeenPosition(666);
    }

    @Test
    public void shouldDelegateToWebClient() {
        repository.getFromWebClient();
    }
}

class MockClient {
    Call<ResponseBody> create() {
        NetworkBehavior networkBehavior = NetworkBehavior.create();
        networkBehavior.setFailurePercent(0);

        MockRetrofit mockRetrofit = new MockRetrofit.Builder(RetrofitServiceInstanceProvider.getInstance().getRetrofit())
                .networkBehavior(networkBehavior)
                .build();

        BehaviorDelegate<PokemonService> pokemonServiceBehaviorDelegate = mockRetrofit.create(PokemonService.class);

        return pokemonServiceBehaviorDelegate.returningResponse(ResponseBody.create(MediaType.parse("application/json"), "{}")).getAll(20, 20);
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
    public void getPokemonList(int offset, Class<PokemonListModel> clazz, BaseCallback<PokemonListModel> callback) {
        if (success) {
            baseCallback.success(null);
        } else {
            baseCallback.error("mock de error");
        }
    }
}