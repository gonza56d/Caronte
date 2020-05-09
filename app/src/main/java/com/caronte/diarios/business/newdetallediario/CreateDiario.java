package com.caronte.diarios.business.newdetallediario;

import android.app.Activity;
import android.os.AsyncTask;

import com.caronte.Utils;
import com.caronte.diarios.entities.DetalleDiario;
import com.caronte.diarios.entities.Diario;
import com.caronte.diarios.entities.Periodo;
import com.caronte.room.AppDatabase;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;

public class CreateDiario extends AsyncTask<Void, Void, Diario> {

    private WeakReference<Activity> weakActivity;
    private IntBusNewDetalleDiario llamador;
    private AppDatabase db;
    private Periodo periodo;
    private Diario diarioAyer;

    public CreateDiario(Activity activity, IntBusNewDetalleDiario llamador, AppDatabase db,
                        Periodo periodo, Diario diarioAyer) {
        weakActivity = new WeakReference<>(activity);
        this.llamador = llamador;
        this.db = db;
        this.periodo = periodo;
        this.diarioAyer = diarioAyer;
        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    protected Diario doInBackground(Void... voids) {
        Diario diario = buildDiario();
        db.diarioDao().insertAll(diario);
        System.out.println("INSERT: " + diario);
        return diario;
    }

    @Override
    protected void onPostExecute(Diario diario) {
        Activity activity = weakActivity.get();
        if (activity == null) {
            return;
        }
        if (diario != null) {
            llamador.setDiario(diario);
        } else {
            llamador.raiseException("Error inesperado.");
        }
    }

    private Diario buildDiario() {
        Diario diario = new Diario();
        diario.setDiarioId(Utils.getToday());
        diario.setGasto(0L);
        diario.setSobra(periodo.getDisponibleDiario());
        diario.setBalance(periodo.getDisponibleDiario() - periodo.getSaldoRestante() / Utils.contarDias(new Date(), periodo.getHasta()));
        diario.setDetalles(new ArrayList<DetalleDiario>());
        if (diarioAyer != null) {
            diario.setBalance(diarioAyer.getBalance()+diarioAyer.getSobra());
        }
        return diario;
    }

}
