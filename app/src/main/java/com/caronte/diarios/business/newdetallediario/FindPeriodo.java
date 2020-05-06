package com.caronte.diarios.business.newdetallediario;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.caronte.NewPeriodoActivity;
import com.caronte.diarios.entities.Periodo;
import com.caronte.room.AppDatabase;

import java.lang.ref.WeakReference;
import java.util.Date;

public class FindPeriodo extends AsyncTask<Void, Void, Periodo> {

    private WeakReference<Activity> weakActivity;
    private IntBusNewDetalleDiario llamador;
    private AppDatabase db;

    /**
     * Búsqueda asíncrona de período. En caso de existir, lo setea a la vista (setPeriodo()) y
     * llama al método de la misma a buscar el diario (findDiario()). En caso contrario, interrumpe
     * la actividad e inicia NewPeriodoActivity.
     * */
    public FindPeriodo(Activity activity, IntBusNewDetalleDiario llamador, AppDatabase db) {
        weakActivity = new WeakReference<>(activity);
        this.llamador = llamador;
        this.db = db;
        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    protected Periodo doInBackground(Void... voids) {
        return db.periodoDao().getPeriodo(new Date());
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
            llamador.setPeriodo(periodo);
            llamador.findDiario();
        }
    }
}
