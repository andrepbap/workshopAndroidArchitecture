package br.com.andrepbap.estudoarquiteturaandroid.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.andrepbap.estudoarquiteturaandroid.R;
import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonListModel;
import br.com.andrepbap.estudoarquiteturaandroid.repository.Repository;

public class PokemonListActivity extends AppCompatActivity {

    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        new Repository<PokemonListModel>().get("pokemon", PokemonListModel.class, new Repository.Callback<PokemonListModel>() {
            @Override
            public void success(PokemonListModel pokemonListModel) {
                adapter.update(pokemonListModel.getResults());
            }

            @Override
            public void error(String error) {
                Toast.makeText(PokemonListActivity.this, error, Toast.LENGTH_LONG).show();
            }
        });
    }
}