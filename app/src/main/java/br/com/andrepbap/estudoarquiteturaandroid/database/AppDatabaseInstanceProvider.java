package br.com.andrepbap.estudoarquiteturaandroid.database;

import android.content.Context;

import androidx.room.Room;

public class AppDatabaseInstanceProvider {

    private final static String DATABASE_NAME = "pokemon.db";

    private static AppDatabaseInstanceProvider appDatabaseInstanceProvider;
    private final AppDatabase database;

    private AppDatabaseInstanceProvider(Context context) {
        database = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).build();
    }

    public static AppDatabaseInstanceProvider getInstance(Context context) {
        if (appDatabaseInstanceProvider == null) {
            appDatabaseInstanceProvider = new AppDatabaseInstanceProvider(context);
        }

        return appDatabaseInstanceProvider;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
