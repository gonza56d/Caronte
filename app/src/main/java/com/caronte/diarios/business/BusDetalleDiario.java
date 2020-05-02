package com.caronte.diarios.business;

import com.caronte.Utils;
import com.caronte.room.AppDatabase;

public class BusDetalleDiario implements IntDetalleDiario {
    @Override
    public void insertDetalleDiario(final AppDatabase db, String descripcion, String gasto) {
        try {
            if (Utils.hasData(descripcion, gasto)) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
