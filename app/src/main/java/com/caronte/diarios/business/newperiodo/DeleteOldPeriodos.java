package com.caronte.diarios.business.newperiodo;

import android.app.Activity;
import android.os.AsyncTask;

import com.caronte.room.AppDatabase;

import java.lang.ref.WeakReference;

public class DeleteOldPeriodos extends AsyncTask<Void, Void, Void> {

    private WeakReference<Activity> weakActivity;
    private IntBusNewPeriodo llamador;
    private AppDatabase db;

    public DeleteOldPeriodos(Activity activity, IntBusNewPeriodo llamador, AppDatabase db) {
        weakActivity = new WeakReference<>(activity);
        this.llamador = llamador;
        this.db = db;
        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        db.periodoDao().deleteAll();
        db.diarioDao().deleteAll();
        db.detalleDiarioDao().deleteAll();
        return null;
    }

    @Override
    protected void onPostExecute(Void voids) {
        Activity activity = weakActivity.get();
        if (activity == null) {
            return;
        }
        llamador.createPeriodo();
    }

}
