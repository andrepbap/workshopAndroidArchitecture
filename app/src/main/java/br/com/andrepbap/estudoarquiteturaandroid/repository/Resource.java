package br.com.andrepbap.estudoarquiteturaandroid.repository;

public class Resource<T> {
    public T data;
    public String error;

    public Resource(T data) {
        this.data = data;
    }

    public Resource(String error) {
        this.error = error;
    }

    public Resource(T data, String error) {
        this.data = data;
        this.error = error;
    }
}
