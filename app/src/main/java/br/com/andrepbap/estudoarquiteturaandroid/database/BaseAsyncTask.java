package br.com.andrepbap.estudoarquiteturaandroid.database;

import android.os.AsyncTask;

public class BaseAsyncTask<T> extends AsyncTask<Void, Void, T> {

    BaseAsyncTaskDelegate<T> baseAsyncTaskDelegate;

    public BaseAsyncTask(BaseAsyncTaskDelegate<T> baseAsyncTaskDelegate) {
        this.baseAsyncTaskDelegate = baseAsyncTaskDelegate;
    }

    @Override
    protected T doInBackground(Void... voids) {
        return baseAsyncTaskDelegate.onAsyncTaskInBackground();
    }

    @Override
    protected void onPostExecute(T result) {
        super.onPostExecute(result);
        baseAsyncTaskDelegate.onAsyncTaskFinish(result);
    }

    public interface BaseAsyncTaskDelegate<T> {
        T onAsyncTaskInBackground();
        void onAsyncTaskFinish(T result);
    }
}
