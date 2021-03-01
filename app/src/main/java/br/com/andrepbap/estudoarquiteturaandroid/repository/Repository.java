package br.com.andrepbap.estudoarquiteturaandroid.repository;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import br.com.andrepbap.estudoarquiteturaandroid.OkHttpClientProvider;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Repository<T> {

    private final OkHttpClient okHttpClient;
    private final static String API_URL = "https://pokeapi.co/api/v2/";

    public Repository() {
        okHttpClient = OkHttpClientProvider.getInstance().getOkHttpClient();
    }

    public void get(String path, Class<T> clazz, Callback<T> callback) {

        HttpUrl.Builder urlBuilder = HttpUrl.parse(API_URL + path).newBuilder();
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.error(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    callback.error(response.message());
                    return;
                }

                callback.success(new Gson().fromJson(response.body().string(), clazz));
            }
        });
    }

    public interface Callback<T> {
        void success(T object);
        void error(String error);
    }
}
