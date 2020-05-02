package com.caronte.room;

import com.caronte.diarios.entities.Periodo;

/**
 * Interfaz que se utiliza para realizar consúltas asíncronas y poder así setear valores a la
 * actividad asincrónicanamente.
 * */
public interface IntPeriodoBridge {

    void setPeriodo(Periodo periodo);

}
