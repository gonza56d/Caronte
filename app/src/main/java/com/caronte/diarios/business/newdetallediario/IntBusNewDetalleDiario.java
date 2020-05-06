package com.caronte.diarios.business.newdetallediario;

import com.caronte.diarios.entities.Diario;
import com.caronte.diarios.entities.Periodo;

public interface IntBusNewDetalleDiario {

    //Find Periodo
    void setPeriodo(Periodo periodo);
    void findDiario();
    //Find Diario
    void setDiario(Diario diario);
    void createDiario();

}
