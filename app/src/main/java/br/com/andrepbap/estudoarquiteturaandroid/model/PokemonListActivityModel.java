package br.com.andrepbap.estudoarquiteturaandroid.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PokemonListActivityModel {

    @NonNull
    @PrimaryKey()
    private String uid;

    private String nextPage;

    private int lastSeemListPosition;

    public PokemonListActivityModel(@NonNull String uid, String nextPage) {
        this.uid = uid;
        this.nextPage = nextPage;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public int getLastSeemListPosition() {
        return lastSeemListPosition;
    }

    public void setLastSeemListPosition(int lastSeemListPosition) {
        this.lastSeemListPosition = lastSeemListPosition;
    }
}
