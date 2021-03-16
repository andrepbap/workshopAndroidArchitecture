package br.com.andrepbap.estudoarquiteturaandroid.webclient;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import br.com.andrepbap.estudoarquiteturaandroid.repository.BaseCallback;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WebClient<T> {
    private final OkHttpClient okHttpClient;
    private final static String API_URL = "https://pokeapi.co/api/v2/";

    public WebClient() {
        okHttpClient = OkHttpClientInstanceProvider.getInstance().getOkHttpClient();
    }

    public void get(String path, Class<T> clazz, BaseCallback<T> callback) {

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
}
