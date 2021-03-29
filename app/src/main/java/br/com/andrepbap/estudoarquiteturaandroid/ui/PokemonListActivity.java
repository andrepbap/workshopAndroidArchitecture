package br.com.andrepbap.estudoarquiteturaandroid.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.andrepbap.estudoarquiteturaandroid.R;
import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonListModel;
import br.com.andrepbap.estudoarquiteturaandroid.repository.BaseCallback;
import br.com.andrepbap.estudoarquiteturaandroid.repository.PokemonRepository;

public class PokemonListActivity extends AppCompatActivity {

    private RecyclerAdapter adapter;
    private final Handler handler = new Handler();
    private PokemonListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PokemonListViewModelFactory factory = new PokemonListViewModelFactory(new PokemonRepository(this));
        viewModel = new ViewModelProvider(this,factory).get(PokemonListViewModel.class);
        setupRecyclerView();
        getModel();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.main_recycler_view);
        adapter = new RecyclerAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    private void getModel() {
        viewModel.getPokemonList().observe(this, pokemonListModel -> adapter.update(pokemonListModel.getResults()));
    }
}