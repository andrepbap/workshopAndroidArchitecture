package br.com.andrepbap.estudoarquiteturaandroid.repository;

public interface BaseCallback<T> {
    void success(T object);
    void error(String error);
}
