package com.caronte.diarios.business;

import com.caronte.diarios.entities.Diario;
import com.caronte.room.AppDatabase;

public interface IntDiario {

    Diario getDiario(AppDatabase db);

    void deleteAll(AppDatabase db);

}
