package br.com.andrepbap.estudoarquiteturaandroid.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PokemonDetailModel implements Serializable {

    private Species species;
    private Sprites sprites;

    public Species getSpecies() {
        return species;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public static class Species {
        private String name;

        public String getName() {
            return name;
        }
    }

    public static class Sprites {
        private Other other;

        public Other getOther() {
            return other;
        }

        public static class Other {
            @SerializedName("official-artwork")
            private OfficialArtwork officialArtwork;

            public OfficialArtwork getOfficialArtwork() {
                return officialArtwork;
            }

            public static class OfficialArtwork {
                @SerializedName("front_default")
                private String frontDefault;

                public String getFrontDefault() {
                    return frontDefault;
                }
            }
        }
    }
}
