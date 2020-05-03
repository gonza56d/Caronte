package com.caronte.diarios.business;

import com.caronte.diarios.entities.Periodo;
import com.caronte.exceptions.BusinessException;
import com.caronte.room.AppDatabase;

public interface IntPeriodo {

    void insertPeriodo(AppDatabase db, String hasta, String balanceInicial) throws BusinessException;

    Periodo getPeriodo(AppDatabase db);

    void deleteAll(AppDatabase db);

}
