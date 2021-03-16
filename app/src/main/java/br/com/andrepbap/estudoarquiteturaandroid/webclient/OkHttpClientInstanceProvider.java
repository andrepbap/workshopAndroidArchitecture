package br.com.andrepbap.estudoarquiteturaandroid.webclient;

import okhttp3.OkHttpClient;

public class OkHttpClientInstanceProvider {

    private static OkHttpClientInstanceProvider instance;

    private final OkHttpClient okHttpClient;

    private OkHttpClientInstanceProvider() {
        okHttpClient = new OkHttpClient();
    }

    public static OkHttpClientInstanceProvider getInstance() {
        if (instance == null) {
            instance = new OkHttpClientInstanceProvider();
        }
        return instance;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}
