package br.com.andrepbap.estudoarquiteturaandroid.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PokemonModel {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "name")
    private String name = "";

    private String activityId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
}
