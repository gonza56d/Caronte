package com.caronte.diarios.business;

public class Factory {

    private static IntPeriodo intPeriodo;
    public static IntPeriodo getIntPeriodo() {
        return (intPeriodo == null) ? intPeriodo = new BusPeriodo() : intPeriodo;
    }

    private static IntDiario intDiario;
    public static IntDiario getIntDiario() {
        return (intDiario == null) ? intDiario = new BusDiario() : intDiario;
    }

    private static IntDetalleDiario intDetalleDiario;
    public static IntDetalleDiario getIntDetalleDiario() {
        return (intDetalleDiario == null) ? intDetalleDiario = new BusDetalleDiario() : intDetalleDiario;
    }

}
