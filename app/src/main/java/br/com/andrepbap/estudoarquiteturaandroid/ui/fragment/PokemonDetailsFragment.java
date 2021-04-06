package br.com.andrepbap.estudoarquiteturaandroid.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.andrepbap.estudoarquiteturaandroid.R;
import br.com.andrepbap.estudoarquiteturaandroid.ui.viewmodel.PokemonListViewModelFactory;
import br.com.andrepbap.estudoarquiteturaandroid.ui.viewmodel.PokemonViewModel;

public class PokemonDetailsFragment extends Fragment {

    private ImageView image;
    private TextView name;

    private PokemonViewModel pokemonViewModel;

    public static PokemonDetailsFragment newInstance() {
        return new PokemonDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pokemon_details_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PokemonListViewModelFactory pokemonListViewModelFactory = new PokemonListViewModelFactory(requireActivity().getApplication());
        ViewModelProvider viewModelProvider = new ViewModelProvider(requireActivity(), pokemonListViewModelFactory);
        pokemonViewModel = viewModelProvider.get(PokemonViewModel.class);

        setupLayout(view);
        getPokemonDetails();
    }

    private void setupLayout(View view) {
        image = view.findViewById(R.id.pokemon_detail_image);
        name = view.findViewById(R.id.pokemon_detail_name);
    }

    private void getPokemonDetails() {
        pokemonViewModel.getPokemonDetails("").observe(this, pokemonDetailModelResource -> {
            name.setText(pokemonDetailModelResource.data.getSpecies().getName());

            Picasso.get()
                    .load(pokemonDetailModelResource.data.getSprites().getOther().getOfficialArtwork().getFrontDefault())
                    .fit()
                    .centerCrop()
                    .into(image);
        });
    }
}