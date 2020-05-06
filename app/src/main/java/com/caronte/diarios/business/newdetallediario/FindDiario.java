package com.caronte.diarios.business.newdetallediario;

import android.app.Activity;
import android.os.AsyncTask;

import com.caronte.diarios.entities.Diario;
import com.caronte.room.AppDatabase;

import java.lang.ref.WeakReference;
import java.util.Date;

public class FindDiario extends AsyncTask<Void, Void, Diario> {

    private WeakReference<Activity> weakActivity;
    private IntBusNewDetalleDiario llamador;
    private AppDatabase db;

    /**
     * Búsqueda asíncrona de diario. Devuelve el actual (setDiario()), y en caso de no existir,
     * llamará al método de la vista para crear uno nuevo y persistirlo (createDiario()).
     * */
    public FindDiario(Activity activity, IntBusNewDetalleDiario llamador, AppDatabase db) {
        weakActivity = new WeakReference<>(activity);
        this.llamador = llamador;
        this.db = db;
        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    protected Diario doInBackground(Void... voids) {
        return db.diarioDao().getDiario(new Date());
    }

    @Override
    protected void onPostExecute(Diario diario) {
        Activity activity = weakActivity.get();
        if (activity == null) {
            return;
        }
        if (diario == null) { //Si no encontró un diario, llama a la vista a crear uno.
            llamador.createDiario();
        } else {    // Si lo encontró, simplemente lo setea y deja seguir la actividad.
            llamador.setDiario(diario);
        }
    }

}
