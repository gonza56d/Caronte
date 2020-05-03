package com.caronte.diarios.business;

import android.os.AsyncTask;

import com.caronte.Utils;
import com.caronte.diarios.entities.DetalleDiario;
import com.caronte.room.AppDatabase;

import java.util.Date;

public class BusDetalleDiario implements IntDetalleDiario {
    @Override
    public DetalleDiario insertDetalleDiario(final AppDatabase db, String descripcion, String gasto) {
        DetalleDiario detalleDiario = null;
        try {
            if (Utils.hasData(descripcion, gasto)) {
                detalleDiario = buildDetalleDiario(descripcion, gasto);
                final DetalleDiario finalDetalleDiario = detalleDiario;
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        db.detalleDiarioDao().insertAll(finalDetalleDiario);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detalleDiario;
    }

    @Override
    public void deleteAll(final AppDatabase db) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                db.detalleDiarioDao().deleteAll();
            }
        });
    }

    /**
     * Construye el detalle para insertar a partir de la descripcion y el gasto.
     * */
    private DetalleDiario buildDetalleDiario(String descripcion, String gasto) {
        DetalleDiario detalleDiario = null;
        try {
            detalleDiario.setDiarioId(new Date());
            detalleDiario.setDescripcion(descripcion);
            detalleDiario.setGasto(Long.valueOf(gasto));
            detalleDiario.setHora(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detalleDiario;
    }

}
