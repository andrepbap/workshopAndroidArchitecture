package br.com.andrepbap.estudoarquiteturaandroid.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.andrepbap.estudoarquiteturaandroid.R;
import br.com.andrepbap.estudoarquiteturaandroid.model.PokemonModel;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.PokemonViewHolder> {

    private List<PokemonModel> dataSet;

    public RecyclerAdapter(List<PokemonModel> dataSet) {
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_holder, parent, false);

        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        holder.setupLayout(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void update(List<PokemonModel> dataSet) {
        this.dataSet = dataSet;
        notifyDataSetChanged();
    }

    class PokemonViewHolder extends RecyclerView.ViewHolder {

        private TextView label;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);

            label = itemView.findViewById(R.id.recycler_holder_label);
        }

        public void setupLayout(PokemonModel pokemonModel) {
            label.setText(pokemonModel.getName());
        }
    }
}
