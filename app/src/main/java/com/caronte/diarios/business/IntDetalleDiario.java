package com.caronte.diarios.business;

import com.caronte.diarios.entities.DetalleDiario;
import com.caronte.room.AppDatabase;

public interface IntDetalleDiario {

    DetalleDiario insertDetalleDiario(AppDatabase db, String descripcion, String gasto);
    void deleteAll(AppDatabase db);

}
