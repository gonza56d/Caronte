package com.caronte.diarios.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;

/**
 * Clase que representa el balance total del dinero en un día en particular.
 * Su clave primaria es un Date del día al que pertenece.
 * Pertenece a un período, y dispone de hijos que son los DetalleDiario.
 * @author Gonza
 * */
@Entity
public class Diario {

    @PrimaryKey
    private Date diarioId;
    @ColumnInfo(name = "gasto")
    private Long gasto;
    @ColumnInfo(name = "sobra")
    private Long sobra;
    @ColumnInfo(name = "balance")
    private Long balance;
    @Ignore
    private List<DetalleDiario> detalles;

    public Diario() {}

    public Diario(Date diarioId, Long gasto, Long sobra, Long balance) {
        this.diarioId = diarioId;
        this.gasto = gasto;
        this.sobra = sobra;
        this.balance = balance;
    }

    /*Getters & Setters*/
    /**
     * Devuelve la clave primaria, que es la fecha a la que pertenece el único diario para tal fecha.
     * */
    public Date getDiarioId() {
        return diarioId;
    }

    /**
     * Setea la clave primaria, que es la fecha a la que va a pertenecer este único diario para tal fecha.
     * */
    public void setDiarioId(Date diarioId) {
        this.diarioId = diarioId;
    }

    /**
     * Devuelve el gasto total diario producido.
     * */
    public Long getGasto() {
        return gasto;
    }

    /**
     * Setea el gasto total diario producido.
     * */
    public void setGasto(Long gasto) {
        this.gasto = gasto;
    }

    /**
     * Devuelve la sobra del día, que es el monto disponible diario del período, menos el gasto del día.
     * (Periodo.disponibleDiario - Diario.gasto)
     * */
    public Long getSobra() {
        return sobra;
    }

    /**
     * Setea la sobra del día, que es el monto disponible diario del período, menos el gasto del día.
     * (Periodo.disponibleDiario - Diario.gasto)
     * */
    public void setSobra(Long sobra) {
        this.sobra = sobra;
    }

    /**
     * Devuelve suma de sobras de todos los diarios anteriores hasta la fecha de este diario.
     * */
    public Long getBalance() {
        return balance;
    }

    /**
     * Setea suma de sobras de todos los diarios anteriores hasta la fecha de este diario.
     * */
    public void setBalance(Long balance) {
        this.balance = balance;
    }

    /**
     * Devuelve la lista de detalles de gastos del día.
     * */
    public List<DetalleDiario> getDetalles() {
        return detalles;
    }

    /**
     * Setea la lista de detalles de gastos del día.
     * */
    public void setDetalles(List<DetalleDiario> detalles) {
        this.detalles = detalles;
    }
}
