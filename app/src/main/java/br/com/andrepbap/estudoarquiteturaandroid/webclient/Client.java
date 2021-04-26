package br.com.andrepbap.estudoarquiteturaandroid.webclient;

import br.com.andrepbap.estudoarquiteturaandroid.repository.BaseCallback;

public interface Client<T> {
    void get(String url, Class<T> clazz, BaseCallback<T> callback);
}
