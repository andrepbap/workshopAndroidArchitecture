package br.com.andrepbap.estudoarquiteturaandroid.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import br.com.andrepbap.estudoarquiteturaandroid.R;
import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonListState;
import br.com.andrepbap.estudoarquiteturaandroid.repository.PokemonRepository;
import br.com.andrepbap.estudoarquiteturaandroid.ui.RecyclerAdapter;
import br.com.andrepbap.estudoarquiteturaandroid.ui.viewmodel.PokemonListViewModel;
import br.com.andrepbap.estudoarquiteturaandroid.ui.viewmodel.PokemonListViewModelFactory;

public class PokemonListFragment extends Fragment {

    private PokemonListViewModel pokemonListViewModel;

    private RecyclerAdapter adapter;
    private RecyclerView recyclerView;

    private FloatingActionButton goToTopActionButton;

    public static PokemonListFragment newInstance() {
        return new PokemonListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pokemon_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PokemonListViewModelFactory pokemonListViewModelFactory = new PokemonListViewModelFactory(requireActivity().getApplication());
        ViewModelProvider viewModelProvider = new ViewModelProvider(requireActivity(), pokemonListViewModelFactory);
        pokemonListViewModel = viewModelProvider.get(PokemonListViewModel.class);

        setupRecyclerView(view);
        setupGoToTopButton(view);
        observePokemonList();
    }

    private void setupRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.main_recycler_view);

        adapter = new RecyclerAdapter(new ArrayList<>());

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        addSaveListPositionHandler();
        addScrollToTopButtonVisibilityHandler();
        addPaginationHandler();
    }

    private void addPaginationHandler() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (!recyclerView.canScrollVertically(RecyclerView.VERTICAL) && newState == RecyclerView.SCROLL_STATE_IDLE){
                pokemonListViewModel.paginate();
            }
            }
        });
    }

    private void addScrollToTopButtonVisibilityHandler() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (dy > 0) {
                goToTopActionButton.setVisibility(View.VISIBLE);
            } else {
                goToTopActionButton.setVisibility(View.GONE);
            }
            }
        });
    }

    private void addSaveListPositionHandler() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                if(layoutManager instanceof GridLayoutManager){
                    int firstVisibleItemPosition = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
                    pokemonListViewModel.updateListPositionStateWith(firstVisibleItemPosition);
                }
            }
            }
        });
    }

    private void setupGoToTopButton(View view) {
        goToTopActionButton = view.findViewById(R.id.go_to_top_floating_action_button);
        goToTopActionButton.setVisibility(View.GONE);
        goToTopActionButton.setOnClickListener(v -> pokemonListViewModel.resetList());
    }

    private void observePokemonList() {
        pokemonListViewModel.getPokemonList().observe(this, resource -> {
            PokemonListState data = resource.data;
            if (data != null) {
                adapter.update(data.getPokemonListModel().getResults());
                recoverListPositionStateWith(data.getLastSeemPosition());
            }

            if (resource.error != null) {
                Toast.makeText(getActivity(), resource.error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void recoverListPositionStateWith(int position) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if (layoutManager != null) {
            int count = layoutManager.getItemCount();
            if(position != RecyclerView.NO_POSITION && position < count){
                layoutManager.scrollToPosition(position);
            }
        }
    }
}