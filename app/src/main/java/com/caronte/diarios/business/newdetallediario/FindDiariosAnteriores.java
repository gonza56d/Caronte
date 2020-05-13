package com.caronte.diarios.business.newdetallediario;

import android.app.Activity;
import android.os.AsyncTask;

import com.caronte.Utils;
import com.caronte.diarios.entities.Diario;
import com.caronte.diarios.entities.Periodo;
import com.caronte.room.AppDatabase;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class FindDiariosAnteriores extends AsyncTask<Void, Void, List<Diario>> {

    private WeakReference<Activity> weakActivity;
    private IntBusNewDetalleDiario llamador;
    private AppDatabase db;
    private Periodo periodo;

    public FindDiariosAnteriores(Activity activity, IntBusNewDetalleDiario llamador, AppDatabase db,
                                 Periodo periodo) {
        weakActivity = new WeakReference<>(activity);
        this.llamador = llamador;
        this.db = db;
        this.periodo = periodo;
        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    protected List<Diario> doInBackground(Void... voids) {
        return db.diarioDao().getDiariosAnteriores(Utils.getDate(periodo.getDesde()), Utils.getToday());
    }

    @Override
    protected void onPostExecute(List<Diario> diariosAnteriores) {
        System.out.println("SELECT: " + diariosAnteriores);
        Activity activity = weakActivity.get();
        if (activity == null) {
            return;
        }
        if (diariosAnteriores == null) { diariosAnteriores = new ArrayList<>(); }
        llamador.setDiariosAnteriores(diariosAnteriores);
    }

}
