package br.com.andrepbap.estudoarquiteturaandroid.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class PokemonListPreferences {

    public static final String LAST_SEEM_POSITION = "lastSeemPosition";
    public static final String OFFSET = "offset";
    private static final String PREF_NAME = PokemonListPreferences.class.getSimpleName();

    private final SharedPreferences preference;

    private int lastSeemPosition;
    private int offset;

    public PokemonListPreferences(Context context) {
        preference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        lastSeemPosition = preference.getInt(LAST_SEEM_POSITION, 0);
        offset = preference.getInt(OFFSET, 0);
    }

    public void setLastSeenPosition(int lastSeemPosition) {
        this.lastSeemPosition = lastSeemPosition;

        SharedPreferences.Editor editor = preference.edit();
        editor.putInt(LAST_SEEM_POSITION, lastSeemPosition);
        editor.apply();
    }

    public void setOffset(int offset) {
        this.offset = offset;

        SharedPreferences.Editor editor = preference.edit();
        editor.putInt(OFFSET, offset);
        editor.apply();
    }

    public int getLastSeemPosition() {
        return lastSeemPosition;
    }

    public int getOffset() {
        return offset;
    }
}
