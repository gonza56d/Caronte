package com.caronte.room;

import android.app.Activity;
import android.os.AsyncTask;

import com.caronte.diarios.entities.Diario;
import com.caronte.diarios.entities.Periodo;

import java.lang.ref.WeakReference;
import java.util.Date;

public class DiarioBridge extends AsyncTask<Void, Void, Diario> implements IntDiarioBridge {

    private WeakReference<Activity> weakActivity;
    private IntDiarioBridge llamador;
    private AppDatabase db;
    private Diario diario;

    public DiarioBridge(Activity activity, IntDiarioBridge llamador, AppDatabase db, Diario diario) {
        weakActivity = new WeakReference<>(activity);
        this.llamador = llamador;
        this.db = db;
        this.diario = diario;
    }

    @Override
    protected Diario doInBackground(Void... voids) {
        diario = db.diarioDao().getDiario(new Date());
        return diario;
    }

    @Override
    protected void onPostExecute(Diario diario) {
        Activity activity = weakActivity.get();
        if (activity == null) {
            return;
        }
        if (diario == null) {
            diario = buildDiario();
            db.diarioDao().insertAll(diario);
            setDiario(diario);
        } else {
            setDiario(diario);
        }
    }

    @Override
    public void setDiario(Diario driario) {
        llamador.setDiario(diario);
    }

    private Diario buildDiario() {
        diario = new Diario();
        diario.setDiarioId(new Date());
        diario.setGasto(0L);
        diario.setSobra(0L);
        return diario;
    }

}
