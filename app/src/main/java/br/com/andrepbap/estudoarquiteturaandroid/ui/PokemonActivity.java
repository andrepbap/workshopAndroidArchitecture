package br.com.andrepbap.estudoarquiteturaandroid.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import br.com.andrepbap.estudoarquiteturaandroid.R;
import br.com.andrepbap.estudoarquiteturaandroid.ui.fragment.PokemonListFragment;

public class PokemonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.pokemon_activity_container, PokemonListFragment.newInstance())
                .commit();
    }
}