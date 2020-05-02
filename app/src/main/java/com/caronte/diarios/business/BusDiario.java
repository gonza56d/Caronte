package com.caronte.diarios.business;

import android.os.AsyncTask;

import com.caronte.diarios.entities.Diario;
import com.caronte.room.AppDatabase;

import java.util.Date;

public class BusDiario implements IntDiario {
    @Override
    public Diario getDiario(final AppDatabase db) {
        final Diario[] diario = new Diario[1];
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                diario[0] = db.diarioDao().getDiario(new Date());
            }
        });
        return diario[0];
    }
}
