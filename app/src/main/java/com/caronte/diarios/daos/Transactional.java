package com.caronte.diarios.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Transaction;
import androidx.room.Update;

import com.caronte.diarios.entities.DetalleDiario;
import com.caronte.diarios.entities.Diario;
import com.caronte.diarios.entities.Periodo;

@Dao
public abstract class Transactional {

    @Update
    public abstract void updatePeriodo(Periodo periodo);

    @Update
    public abstract void updateDiario(Diario diario);

    @Insert
    public abstract void insertDetalleDiario(DetalleDiario detalleDiario);

    @Transaction
    public void insertarMovimiento(Periodo periodo, Diario diario, DetalleDiario detalleDiario) {
        updatePeriodo(periodo);
        updateDiario(diario);
        insertDetalleDiario(detalleDiario);
    }

}
