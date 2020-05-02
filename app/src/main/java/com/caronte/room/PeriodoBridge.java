package com.caronte.room;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.caronte.NewPeriodoActivity;
import com.caronte.diarios.Diarios;
import com.caronte.diarios.entities.Periodo;

import java.lang.ref.WeakReference;

/**
 * Clase que se utiliza para realizar consultas asíncronas a la base de datos con períodos.
 * */
public class PeriodoBridge extends AsyncTask<Void, Void, Periodo> implements IntPeriodoBridge {

    private WeakReference<Activity> weakActivity;
    private IntPeriodoBridge llamador;
    private AppDatabase db;
    private Periodo periodo;

    public PeriodoBridge(Activity activity, IntPeriodoBridge llamador, AppDatabase db, Periodo periodo) {
        weakActivity = new WeakReference<>(activity);
        this.llamador = llamador;
        this.db = db;
        this.periodo = periodo;
    }

    @Override
    protected Periodo doInBackground(Void... voids) {
        periodo = Diarios.getPeriodo(db);
        return periodo;
    }

    @Override
    protected void onPostExecute(Periodo periodo) {
        Activity activity = weakActivity.get();
        if (activity == null) {
            return;
        }
        if (periodo == null) {
            Intent intent = new Intent(activity, NewPeriodoActivity.class);
            activity.startActivity(intent);
        } else {
            setPeriodo(periodo);
        }
    }

    @Override
    public void setPeriodo(Periodo periodo) {
        llamador.setPeriodo(periodo);
    }
}
