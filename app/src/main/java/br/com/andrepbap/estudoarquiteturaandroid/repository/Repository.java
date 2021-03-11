package br.com.andrepbap.estudoarquiteturaandroid.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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

    private final MutableLiveData<Resource<T>> liveData = new MutableLiveData<>();
    final Resource<T> resource = new Resource<>();

    public Repository() {
        okHttpClient = OkHttpClientProvider.getInstance().getOkHttpClient();
    }

    public LiveData<Resource<T>> get(String path, Class<T> clazz) {

        HttpUrl.Builder urlBuilder = HttpUrl.parse(API_URL + path).newBuilder();
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        updateResourceWithLastDataValue();

        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                resource.error = e.getMessage();
                liveData.postValue(resource);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    resource.error = response.message();
                    liveData.postValue(resource);
                    return;
                }

                resource.data = new Gson().fromJson(response.body().string(), clazz);

                liveData.postValue(resource);
            }
        });

        return liveData;
    }

    private void updateResourceWithLastDataValue() {
        if (resourceHaveData()) {
            resource.data = liveData.getValue().data;
        }

        resource.error = null;
    }

    private boolean resourceHaveData() {
        return liveData.getValue() != null && liveData.getValue().data != null;
    }
}
