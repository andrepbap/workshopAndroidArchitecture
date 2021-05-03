package br.com.andrepbap.estudoarquiteturaandroid.webclient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokemonService {

    @GET("/api/v2/pokemon")
    Call<ResponseBody> getAll(@Query("limit") int limit, @Query("offset") int offset);
}
