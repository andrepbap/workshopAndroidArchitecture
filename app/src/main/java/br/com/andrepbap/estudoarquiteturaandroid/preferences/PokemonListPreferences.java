package br.com.andrepbap.estudoarquiteturaandroid.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class PokemonListPreferences {

    public static final String LAST_SEEM_POSITION = "lastSeemPosition";
    public static final String NEXT_PAGE_LINK = "nextPageLink";
    private static final String PREF_NAME = PokemonListPreferences.class.getSimpleName();

    private final SharedPreferences preference;

    private int lastSeemPosition;
    private String nextPageLink;

    public PokemonListPreferences(Context context) {
        preference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        lastSeemPosition = preference.getInt(LAST_SEEM_POSITION, 0);
        nextPageLink = preference.getString(NEXT_PAGE_LINK, "");
    }

    public void setLastSeenPosition(int lastSeemPosition) {
        this.lastSeemPosition = lastSeemPosition;

        SharedPreferences.Editor editor = preference.edit();
        editor.putInt(LAST_SEEM_POSITION, lastSeemPosition);
        editor.apply();
    }

    public void setNextPageLink(String nextPageLink) {
        this.nextPageLink = nextPageLink;

        SharedPreferences.Editor editor = preference.edit();
        editor.putString(NEXT_PAGE_LINK, nextPageLink);
        editor.apply();
    }

    public int getLastSeemPosition() {
        return lastSeemPosition;
    }

    public String getNextPageLink() {
        return nextPageLink;
    }
}
