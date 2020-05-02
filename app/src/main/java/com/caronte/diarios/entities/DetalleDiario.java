package com.caronte.diarios.entities;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase que representa un gasto específico del día.
 * Posee un padre al cuál pertenece dentro de su lista (Dario.detalles).
 * Lleva un nombre descriptivo para el gasto, y su monto.
 * @author Gonza
 * */
@Entity(primaryKeys = {"diarioId", "detalleDiarioId"})
public class DetalleDiario {

    @NonNull
    private Date diarioId = new Date();
    @NonNull
    private Long detalleDiarioId = 0L;
    @ColumnInfo(name = "descripcion")
    private String descripcion;
    @ColumnInfo(name = "gasto")
    private Long gasto;
    @ColumnInfo(name = "hora")
    private Date hora;
    @SuppressLint("SimpleDateFormat")
    @Ignore
    private final SimpleDateFormat formato = new SimpleDateFormat("HH:mm");

    public DetalleDiario() {}

    public DetalleDiario(@NonNull Date diarioId, @NonNull Long detalleDiarioId, String descripcion, Long gasto, Date hora) {
        this.diarioId = diarioId;
        this.detalleDiarioId = detalleDiarioId;
        this.descripcion = descripcion;
        this.gasto = gasto;
        this.hora = hora;
    }

    /*Getters & Setters*/
    /**
     * Devuelve parte de la clave primaria compuesta (diarioId, detalleDiarioId).
     * La cual pertenece al id de su padre (Diario.diarioId) que indica la fecha a la que pertenece,
     * y la comparte con todos sus hermanos.
     * */
    @NonNull
    public Date getDiarioId() {
        return diarioId;
    }

    /**
     * Setea parte de la clave primaria compuesta (diarioId, detalleDiarioId).
     * La cual pertenece al id de su padre (Diario.diarioId) que indica la fecha a la que pertenece,
     * y la comparte con todos sus hermanos.
     * */
    public void setDiarioId(@NonNull Date diarioId) {
        this.diarioId = diarioId;
    }

    /**
     * Devuelve parte de la clave primaria compuesta (diarioId, detalleDiarioId).
     * La cual pertenece al id único entre sus hermanos (DetalleDiario.detalleDiarioId).
     * */
    @NonNull
    public Long getDetalleDiarioId() {
        return detalleDiarioId;
    }

    /**
     * Setea parte de la clave primaria compuesta (diarioId, detalleDiarioId).
     * La cual pertenece al id único entre sus hermanos (DetalleDiario.detalleDiarioId).
     * */
    public void setDetalleDiarioId(@NonNull Long detalleDiarioId) {
        this.detalleDiarioId = detalleDiarioId;
    }

    /**
     * Devuelve la descripción de este detalle de gasto.
     * */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Setea la descripción para este detalle de gasto.
     * */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Devuelve el gasto de este detalle.
     * */
    public Long getGasto() {
        return gasto;
    }

    /**
     * Setea el gasto de este detalle.
     * */
    public void setGasto(Long gasto) {
        this.gasto = gasto;
    }

    /**
     * Devuelve la hora del gasto formateada en String (HH:mm) lista para ser mostrada al UI.
     * */
    public String getHoraFormateada() {
        if (hora != null) {
            return formato.format(hora);
        }
        return null;
    }

    /**
     * Devuelve la hora del gasto en objeto Date.
     * */
    public Date getHora() {
        return hora;
    }

    /**
     * Setea la hora en formato Date puro. Se formatea automáticamente al mostrar (al hacer get).
     * Indica la hora en la que se produce el gasto.
     * */
    public void setHora(Date hora) {
        this.hora = hora;
    }

    /**
     * Devuelve el formato utilizado por esta clase para mostrar la hora a la que se produjo
     * el gasto (HH:mm).
     * */
    public SimpleDateFormat getFormato() {
        return formato;
    }
}
