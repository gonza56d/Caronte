package com.caronte.diarios.business.newdetallediario;

import android.app.Activity;
import android.os.AsyncTask;

import com.caronte.Utils;
import com.caronte.diarios.entities.Diario;
import com.caronte.room.AppDatabase;

import java.lang.ref.WeakReference;

public class FindDiario extends AsyncTask<Void, Void, Diario> {

    private WeakReference<Activity> weakActivity;
    private IntBusNewDetalleDiario llamador;
    private AppDatabase db;
    private boolean ayer;

    /**
     * Búsqueda asíncrona de diario. Devuelve el actual (setDiario()), y en caso de no existir,
     * llamará al método de la vista para crear uno nuevo y persistirlo (createDiario()).
     * */
    public FindDiario(Activity activity, IntBusNewDetalleDiario llamador, AppDatabase db, boolean ayer) {
        weakActivity = new WeakReference<>(activity);
        this.llamador = llamador;
        this.db = db;
        this.ayer = ayer;
        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    protected Diario doInBackground(Void... voids) {
        if (!ayer) {
            return db.diarioDao().getDiario(Utils.getToday());
        } else {
            return db.diarioDao().getDiario(Utils.getAyer());
        }
    }

    @Override
    protected void onPostExecute(Diario diario) {
        System.out.println("SELECT: " + diario);
        Activity activity = weakActivity.get();
        if (activity == null) {
            return;
        }
        if (diario == null && !ayer) { //Si no encontró un diario, y no es el de ayer, llama a la vista a crear uno.
            llamador.createDiario();
        } else {    // Si lo encontró, lo setea
            if (!ayer) {
                llamador.setDiario(diario);
            } else {
                llamador.setDiarioAyer(diario);
                llamador.findDiario(false);
            }
        }
    }

}
