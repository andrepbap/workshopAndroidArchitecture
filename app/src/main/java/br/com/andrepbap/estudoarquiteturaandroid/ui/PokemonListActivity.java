package br.com.andrepbap.estudoarquiteturaandroid.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.andrepbap.estudoarquiteturaandroid.R;
import br.com.andrepbap.estudoarquiteturaandroid.repository.PokemonRepository;
import br.com.andrepbap.estudoarquiteturaandroid.ui.viewmodel.PokemonListViewModel;
import br.com.andrepbap.estudoarquiteturaandroid.ui.viewmodel.PokemonListViewModelFactory;

public class PokemonListActivity extends AppCompatActivity {

    private PokemonListViewModel pokemonListViewModel;
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PokemonListViewModelFactory pokemonListViewModelFactory = new PokemonListViewModelFactory(new PokemonRepository(this));
        ViewModelProvider viewModelProvider = new ViewModelProvider(this, pokemonListViewModelFactory);
        pokemonListViewModel = viewModelProvider.get(PokemonListViewModel.class);

        setupRecyclerView();
        getModel();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.main_recycler_view);
        adapter = new RecyclerAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(!recyclerView.canScrollVertically(RecyclerView.VERTICAL) && newState == RecyclerView.SCROLL_STATE_IDLE){
                    pokemonListViewModel.paginate();
                }
            }
        });
    }

    private void getModel() {
        pokemonListViewModel.getPokemonList().observe(this, resource -> {
            if (resource.data != null) {
                adapter.update(resource.data.getResults());
            }

            if (resource.error != null) {
                Toast.makeText(PokemonListActivity.this, resource.error, Toast.LENGTH_LONG).show();
            }
        });
    }
}