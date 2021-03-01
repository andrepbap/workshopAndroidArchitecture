package br.com.andrepbap.estudoarquiteturaandroid;

import okhttp3.OkHttpClient;

public class OkHttpClientProvider {

    private static OkHttpClientProvider instance;

    private final OkHttpClient okHttpClient;

    private OkHttpClientProvider() {
        okHttpClient = new OkHttpClient();
    }

    public static OkHttpClientProvider getInstance() {
        if (instance == null) {
            instance = new OkHttpClientProvider();
        }
        return instance;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}
