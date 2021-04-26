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

import br.com.andrepbap.estudoarquiteturaandroid.R;
import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonListState;
import br.com.andrepbap.estudoarquiteturaandroid.preferences.PokemonListPreferences;
import br.com.andrepbap.estudoarquiteturaandroid.repository.PokemonRepository;
import br.com.andrepbap.estudoarquiteturaandroid.ui.viewmodel.PokemonListViewModel;
import br.com.andrepbap.estudoarquiteturaandroid.ui.viewmodel.PokemonListViewModelFactory;
import br.com.andrepbap.estudoarquiteturaandroid.webclient.WebClient;

public class PokemonListActivity extends AppCompatActivity {

    private PokemonListViewModel pokemonListViewModel;

    private RecyclerAdapter adapter;
    private RecyclerView recyclerView;

    private FloatingActionButton goToTopActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PokemonListViewModelFactory pokemonListViewModelFactory = new PokemonListViewModelFactory(new PokemonRepository(this, new WebClient<>(), new PokemonListPreferences(this)));
        ViewModelProvider viewModelProvider = new ViewModelProvider(this, pokemonListViewModelFactory);
        pokemonListViewModel = viewModelProvider.get(PokemonListViewModel.class);

        setupRecyclerView();
        setupGoToTopButton();
        observePokemonList();
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.main_recycler_view);

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

    private void setupGoToTopButton() {
        goToTopActionButton = findViewById(R.id.go_to_top_floating_action_button);
        goToTopActionButton.setVisibility(View.GONE);
        goToTopActionButton.setOnClickListener(view -> pokemonListViewModel.resetList());
    }

    private void observePokemonList() {
        pokemonListViewModel.getPokemonList().observe(this, resource -> {
            PokemonListState data = resource.data;
            if (data != null) {
                adapter.update(data.getPokemonListModel().getResults());
                recoverListPositionStateWith(data.getLastSeemPosition());
            }

            if (resource.error != null) {
                Toast.makeText(PokemonListActivity.this, resource.error, Toast.LENGTH_LONG).show();
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