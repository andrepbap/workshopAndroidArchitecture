package br.com.andrepbap.estudoarquiteturaandroid.repository;

public class Resource<T> {
    private T values;
    private String error;

    public T getValues() {
        return values;
    }

    public void setValues(T values) {
        this.values = values;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
