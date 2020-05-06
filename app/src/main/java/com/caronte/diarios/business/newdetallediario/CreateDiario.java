package com.caronte.diarios.business.newdetallediario;

import android.app.Activity;
import android.os.AsyncTask;

import com.caronte.diarios.entities.Diario;
import com.caronte.diarios.entities.Periodo;
import com.caronte.room.AppDatabase;

import java.lang.ref.WeakReference;

public class CreateDiario extends AsyncTask<Void, Void, Diario> {

    private WeakReference<Activity> weakActivity;
    private IntBusNewDetalleDiario llamador;
    private AppDatabase db;
    private Periodo periodo;

    public CreateDiario(Activity activity, IntBusNewDetalleDiario llamador, AppDatabase db, Periodo periodo) {
        weakActivity = new WeakReference<>(activity);
        this.llamador = llamador;
        this.db = db;
        this.periodo = periodo;
        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    protected Diario doInBackground(Void... voids) {
        return null;
    }

}
