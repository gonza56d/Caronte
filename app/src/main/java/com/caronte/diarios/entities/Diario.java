package com.caronte.diarios.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
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
    private long gasto;
    @ColumnInfo(name = "sobra")
    private long sobra;
    @ColumnInfo(name = "balance")
    private long balance;
    @Ignore
    private List<DetalleDiario> detalles;
    @Ignore
    private final SimpleDateFormat formato = new SimpleDateFormat("dd/MM");

    public Diario() {}

    public Diario(Date diarioId, long gasto, long sobra, long balance) {
        this.diarioId = diarioId;
        this.gasto = gasto;
        this.sobra = sobra;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Diario{" +
                "diarioId=" + diarioId +
                ", gasto=" + gasto +
                ", sobra=" + sobra +
                ", balance=" + balance +
                ", detalles=" + detalles +
                '}';
    }
    /*Getters & Setters*/
    /**
     * Devuelve la clave primaria, que es la fecha a la que pertenece el único diario para tal fecha.
     * */
    public Date getDiarioId() {
        return diarioId;
    }

    public String getFechaFormateada() {
        if (diarioId != null) {
            return formato.format(diarioId);
        }
        return null;
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
    public long getGasto() {
        return gasto;
    }

    /**
     * Setea el gasto total diario producido.
     * */
    public void setGasto(long gasto) {
        this.gasto = gasto;
    }

    /**
     * Devuelve la sobra del día, que es el monto disponible diario del período, menos el gasto del día.
     * (Periodo.disponibleDiario - Diario.gasto)
     * */
    public long getSobra() {
        return sobra;
    }

    /**
     * Setea la sobra del día, que es el monto disponible diario del período, menos el gasto del día.
     * (Periodo.disponibleDiario - Diario.gasto)
     * */
    public void setSobra(long sobra) {
        this.sobra = sobra;
    }

    /**
     * Devuelve suma de sobras de todos los diarios anteriores hasta la fecha de este diario.
     * */
    public long getBalance() {
        return balance;
    }

    /**
     * Setea suma de sobras de todos los diarios anteriores hasta la fecha de este diario.
     * */
    public void setBalance(long balance) {
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
