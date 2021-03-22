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

    public LiveData<Boolean> scrollHandler(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollingDownLiveData.postValue(dy >= 0);
            }
        });

        return scrollingDownLiveData;
    }

    public void paginate() {
        repository.paginate();
    }
}
