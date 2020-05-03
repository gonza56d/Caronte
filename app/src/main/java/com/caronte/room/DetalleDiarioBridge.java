package com.caronte.room;

import android.app.Activity;
import android.os.AsyncTask;

import com.caronte.diarios.Diarios;
import com.caronte.diarios.entities.DetalleDiario;
import com.caronte.diarios.entities.Diario;

import java.lang.ref.WeakReference;

public class DetalleDiarioBridge extends AsyncTask<Void, Void,DetalleDiario> implements IntDetalleDiarioBridge {

    private WeakReference<Activity> weakActivity;
    private IntDetalleDiarioBridge llamador;
    private AppDatabase db;
    private Diario diario;
    private DetalleDiario detalleDiario;
    private String descripcion;
    private String gasto;

    public DetalleDiarioBridge(Activity activity, IntDetalleDiarioBridge llamador, AppDatabase db,
                               Diario diario, String descripcion, String gasto) {
        weakActivity = new WeakReference<>(activity);
        this.llamador = llamador;
        this.db = db;
        this.diario = diario;
        this.descripcion = descripcion;
        this.gasto = gasto;
    }

    @Override
    protected DetalleDiario doInBackground(Void... voids) {
        return this.detalleDiario = Diarios.insertDetalleDiario(db, descripcion, gasto);
    }

    @Override
    protected void onPostExecute(DetalleDiario detalleDiario) {
        Activity activity = weakActivity.get();
        if (activity == null) {
            return;
        }
        if (detalleDiario != null) {
            diario.getDetalles().add(detalleDiario);
            llamador.addDetalleDiarioToTable();
        }
    }

    @Override
    public void addDetalleDiarioToTable() {}
}
