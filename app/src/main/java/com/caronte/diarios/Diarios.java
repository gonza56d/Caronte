package com.caronte.diarios;

import com.caronte.diarios.business.Factory;
import com.caronte.diarios.entities.DetalleDiario;
import com.caronte.diarios.entities.Diario;
import com.caronte.diarios.entities.Periodo;
import com.caronte.exceptions.BusinessException;
import com.caronte.room.AppDatabase;

public class Diarios {

    /**
     * Intenta persistir un nuevo período a la base de datos.
     * */
    public static void insertPeriodo(AppDatabase db, String hasta, String balanceInicial) throws BusinessException {
        Factory.getIntPeriodo().insertPeriodo(db, hasta, balanceInicial);
    }

    /**
     * Consulta a la base de datos por un período actual, el cual automáticamente será consultado
     * mediante la fecha actual del dispositivo.
     * */
    public static Periodo getPeriodo(AppDatabase db) {
        return Factory.getIntPeriodo().getPeriodo(db);
    }

    /**
     * Intenta persistir un nuevo detalle diario a la base de datos para el corriente diario,
     * si la persistencia ocurre, devuelve el objeto.
     * */
    public static DetalleDiario insertDetalleDiario(AppDatabase db, String descripcion, String gasto) {
        return Factory.getIntDetalleDiario().insertDetalleDiario(db, descripcion, gasto);
    }

    /**
     * Consulta a la base de datos por el diario actual, el cuál automáticamente será consultado
     * mediante la fecha actual del dispositivo.
     * */
    public static Diario getDiario(AppDatabase db) {
        return Factory.getIntDiario().getDiario(db);
    }

    /**
     * Borra todos de todos, usable para desarrollo.
     * */
    public static void deleteEverything(AppDatabase db) {
        Factory.getIntDetalleDiario().deleteAll(db);
        Factory.getIntDiario().deleteAll(db);
        Factory.getIntPeriodo().deleteAll(db);
    }

}
