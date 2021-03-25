package br.com.andrepbap.estudoarquiteturaandroid.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import br.com.andrepbap.estudoarquiteturaandroid.R;
import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonModel;
import br.com.andrepbap.estudoarquiteturaandroid.repository.PokemonRepository;
import br.com.andrepbap.estudoarquiteturaandroid.ui.viewmodel.PokemonListViewModel;
import br.com.andrepbap.estudoarquiteturaandroid.ui.viewmodel.PokemonListViewModelFactory;

public class PokemonListActivity extends AppCompatActivity {

    private PokemonListViewModel pokemonListViewModel;

    private RecyclerAdapter adapter;
    private RecyclerView recyclerView;

    private FloatingActionButton goToTopActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PokemonListViewModelFactory pokemonListViewModelFactory = new PokemonListViewModelFactory(new PokemonRepository(this, PokemonListActivity.class.getSimpleName()));
        ViewModelProvider viewModelProvider = new ViewModelProvider(this, pokemonListViewModelFactory);
        pokemonListViewModel = viewModelProvider.get(PokemonListViewModel.class);

        setupRecyclerView();
        setupGoToTopButton();
        observePokemonList();
        observeListPosition();
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.main_recycler_view);

        adapter = new RecyclerAdapter(new ArrayList<>());

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        addListPositionHandler();
        addScrollToTopButtonVisibilityHandler();
        addPaginationHandler();
    }

    private void addListPositionHandler() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                    if(layoutManager instanceof GridLayoutManager){
                        int firstVisibleItemPosition = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
                        pokemonListViewModel.setCurrentListPosition(firstVisibleItemPosition);
                    }
                }
            }
        });
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

    private void setupGoToTopButton() {
        goToTopActionButton = findViewById(R.id.go_to_top_floating_action_button);
        goToTopActionButton.setVisibility(View.GONE);
        goToTopActionButton.setOnClickListener(view -> pokemonListViewModel.resetList());
    }

    private void observePokemonList() {
        pokemonListViewModel.getPokemonList().observe(this, resource -> {
            List<PokemonModel> pokemonModelList = resource.data;
            if (pokemonModelList != null) {
                adapter.update(pokemonModelList);
            }

            if (resource.error != null) {
                Toast.makeText(PokemonListActivity.this, resource.error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void observeListPosition() {
        pokemonListViewModel.getPokemonListActivityState().observe(this, pokemonListActivityModel -> {

            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

            if (pokemonListActivityModel != null && layoutManager != null) {
                int position = pokemonListActivityModel.getLastSeemListPosition();
                int count = layoutManager.getItemCount();

                if(position != RecyclerView.NO_POSITION && position < count){
                    layoutManager.scrollToPosition(position);
                    pokemonListViewModel.updateListPosition(-1);
                }
            }
        });
    }
}