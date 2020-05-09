package com.caronte.diarios.business.newdetallediario;

import com.caronte.diarios.entities.DetalleDiario;
import com.caronte.diarios.entities.Diario;
import com.caronte.diarios.entities.Periodo;

import java.util.List;

public interface IntBusNewDetalleDiario {

    //Find Periodo
    void setPeriodo(Periodo periodo);
    void findDiario(boolean ayer);
    //Find Diario
    void setDiario(Diario diario);
    void setDiarioAyer(Diario diario);
    void createDiario();
    //Find Detalle Diario
    void findDetallesDiarios();
    void setDetallesDiarios(List<DetalleDiario> listDetallesDiarios);
    //Excepciones
    void raiseException(String mensaje);
    //Update movimiento
    void updateMovimiento(Periodo periodo, Diario diario);

}
