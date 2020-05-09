package com.caronte.diarios.business.newdetallediario;

import android.app.Activity;
import android.os.AsyncTask;

import com.caronte.Utils;
import com.caronte.diarios.entities.DetalleDiario;
import com.caronte.room.AppDatabase;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FindDetallesDiarios extends AsyncTask<Void, Void, List<DetalleDiario>> {

    private WeakReference<Activity> weakActivity;
    private IntBusNewDetalleDiario llamador;
    private AppDatabase db;

    public FindDetallesDiarios(Activity activity, IntBusNewDetalleDiario llamador, AppDatabase db) {
        weakActivity = new WeakReference<>(activity);
        this.llamador = llamador;
        this.db = db;
        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    protected List<DetalleDiario> doInBackground(Void... voids) {
        return db.detalleDiarioDao().getDetallesDiarios(Utils.getToday());
    }

    @Override
    protected void onPostExecute(List<DetalleDiario> listDetallesDiarios) {
        System.out.println("SELECT: " + listDetallesDiarios);
        Activity activity = weakActivity.get();
        if (activity == null) {
            return;
        }
        if (listDetallesDiarios != null && listDetallesDiarios.size() > 0) {
            llamador.setDetallesDiarios(listDetallesDiarios);
        } else {
            llamador.setDetallesDiarios(new ArrayList<DetalleDiario>());
        }
    }

}
