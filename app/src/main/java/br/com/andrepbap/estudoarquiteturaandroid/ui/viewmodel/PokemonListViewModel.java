package br.com.andrepbap.estudoarquiteturaandroid.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonListModel;
import br.com.andrepbap.estudoarquiteturaandroid.repository.PokemonRepository;
import br.com.andrepbap.estudoarquiteturaandroid.repository.Resource;

public class PokemonListViewModel extends androidx.lifecycle.ViewModel {

    private final PokemonRepository repository;
    private final MutableLiveData<Boolean> scrollingDownLiveData = new MutableLiveData<>();

    public PokemonListViewModel(PokemonRepository repository) {
        this.repository = repository;
    }

    public LiveData<Resource<PokemonListModel>> getPokemonList() {
        return repository.getPokemonList();
    }

    public void createPaginationHandler(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(RecyclerView.VERTICAL) && newState == RecyclerView.SCROLL_STATE_IDLE){
                    repository.paginate();
                }
            }
        });
    }

    public LiveData<Boolean> scrollHandler(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy != 0) {
                    scrollingDownLiveData.postValue(dy > 0);
                }
            }
        });

        return scrollingDownLiveData;
    }

    public void resetList() {
        scrollingDownLiveData.postValue(false);
        repository.resetList();
    }
}
