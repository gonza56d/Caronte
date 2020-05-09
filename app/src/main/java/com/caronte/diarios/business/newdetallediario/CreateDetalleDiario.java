package com.caronte.diarios.business.newdetallediario;

import android.app.Activity;
import android.os.AsyncTask;

import com.caronte.Utils;
import com.caronte.diarios.daos.Transactional;
import com.caronte.diarios.entities.DetalleDiario;
import com.caronte.diarios.entities.Diario;
import com.caronte.diarios.entities.Periodo;
import com.caronte.room.AppDatabase;

import java.lang.ref.WeakReference;
import java.util.Date;

public class CreateDetalleDiario extends AsyncTask<Void, Void, DetalleDiario> {

    private WeakReference<Activity> weakActivity;
    private IntBusNewDetalleDiario llamador;
    private AppDatabase db;
    private String descripcion;
    private long gasto;
    private Periodo periodo;
    private Diario diario;

    public CreateDetalleDiario(Activity activity, IntBusNewDetalleDiario llamador, AppDatabase db,
                               Periodo periodo, Diario diario, String descripcion, String gasto) {
        weakActivity = new WeakReference<>(activity);
        this.llamador = llamador;
        if (!Utils.hasData(gasto) || !Utils.hasData((descripcion))) {
            llamador.raiseException("Debe completar los campos.");
            return;
        }
        this.db = db;
        this.descripcion = descripcion;
        this.gasto = Long.parseLong(gasto);
        this.periodo = periodo;
        this.diario = diario;
        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    protected DetalleDiario doInBackground(Void... voids) {
        DetalleDiario detalleDiario = buildDetalleDiario();
        periodo.setSaldoRestante(periodo.getSaldoRestante()-detalleDiario.getGasto());
        diario.setGasto(diario.getGasto()+detalleDiario.getGasto());
        diario.setSobra(diario.getSobra()-detalleDiario.getGasto());
        db.getTransactional().insertarMovimiento(periodo, diario, detalleDiario);
        System.out.println("MOVIMIENTO: " + periodo + " - " + diario + " - " + detalleDiario);
        return detalleDiario;
    }

    @Override
    protected void onPostExecute(DetalleDiario detalleDiario) {
        Activity activity = weakActivity.get();
        if (activity == null) {
            return;
        }
        diario.getDetalles().add(detalleDiario);
        llamador.updateMovimiento(periodo, diario);
    }

    private DetalleDiario buildDetalleDiario() {
        DetalleDiario detalleDiario = new DetalleDiario();
        detalleDiario.setDiarioId(Utils.getToday());
        detalleDiario.setDescripcion(descripcion);
        detalleDiario.setGasto(gasto);
        detalleDiario.setHora(new Date());
        return detalleDiario;
    }

}
