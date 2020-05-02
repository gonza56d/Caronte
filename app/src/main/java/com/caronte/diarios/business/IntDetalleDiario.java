package com.caronte.diarios.business;

import com.caronte.room.AppDatabase;

public interface IntDetalleDiario {

    void insertDetalleDiario(AppDatabase db, String descripcion, String gasto);

}
