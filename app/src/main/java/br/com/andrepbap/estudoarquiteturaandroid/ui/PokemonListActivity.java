package br.com.andrepbap.estudoarquiteturaandroid.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import br.com.andrepbap.estudoarquiteturaandroid.R;
import br.com.andrepbap.estudoarquiteturaandroid.repository.PokemonRepository;
import br.com.andrepbap.estudoarquiteturaandroid.ui.viewmodel.PokemonListViewModel;
import br.com.andrepbap.estudoarquiteturaandroid.ui.viewmodel.PokemonListViewModelFactory;

public class PokemonListActivity extends AppCompatActivity {

    private PokemonListViewModel pokemonListViewModel;
    private RecyclerAdapter adapter;
    private FloatingActionButton goToTopActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PokemonListViewModelFactory pokemonListViewModelFactory = new PokemonListViewModelFactory(new PokemonRepository(this));
        ViewModelProvider viewModelProvider = new ViewModelProvider(this, pokemonListViewModelFactory);
        pokemonListViewModel = viewModelProvider.get(PokemonListViewModel.class);

        setupRecyclerView();
        setupGoToTopButton();
        observePokemonList();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.main_recycler_view);

        adapter = new RecyclerAdapter(new ArrayList<>());

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        pokemonListViewModel.scrollHandler(recyclerView).observe(this, isScrollingDown -> {
            if (isScrollingDown) {
                goToTopActionButton.setVisibility(View.VISIBLE);
            } else {
                goToTopActionButton.setVisibility(View.GONE);
            }
        });

        pokemonListViewModel.createPaginationHandler(recyclerView);
    }

    private void setupGoToTopButton() {
        goToTopActionButton = findViewById(R.id.go_to_top_floating_action_button);
        goToTopActionButton.setVisibility(View.GONE);
        goToTopActionButton.setOnClickListener(view -> pokemonListViewModel.resetList());
    }

    private void observePokemonList() {
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