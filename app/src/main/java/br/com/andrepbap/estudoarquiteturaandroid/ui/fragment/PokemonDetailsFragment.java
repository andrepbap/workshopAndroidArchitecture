package br.com.andrepbap.estudoarquiteturaandroid.ui.fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.andrepbap.estudoarquiteturaandroid.R;

public class PokemonDetailsFragment extends Fragment {

    private PokemonDetailsViewModel mViewModel;

    public static PokemonDetailsFragment newInstance() {
        return new PokemonDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pokemon_details_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PokemonDetailsViewModel.class);
        // TODO: Use the ViewModel
    }

}