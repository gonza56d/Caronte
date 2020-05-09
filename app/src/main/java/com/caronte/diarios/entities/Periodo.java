package com.caronte.diarios.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * Clase que representa un período de control. La cantidad de días que dura es definida por el
 * usuario, así como la cantidad de dinero que va a disponer para todo el período.
 * @author Gonza
 * */
@Entity
public class Periodo {

    @PrimaryKey(autoGenerate = true)
    private long periodoId;
    @ColumnInfo(name = "desde")
    private Date desde;
    @ColumnInfo(name = "hasta")
    private Date hasta;
    @ColumnInfo(name = "balanceInicial")
    private long balanceInicial;
    @ColumnInfo(name = "saldoRestante")
    private long saldoRestante;
    @ColumnInfo(name = "balance")
    private long balance;

    public Periodo() {}

    public Periodo(Date desde, Date hasta, long balanceInicial) {
        this.desde = desde;
        this.hasta = hasta;
        this.balanceInicial = balanceInicial;
    }

    @Override
    public String toString() {
        return "Periodo{" +
                "periodoId=" + periodoId +
                ", desde=" + desde +
                ", hasta=" + hasta +
                ", balanceInicial=" + balanceInicial +
                ", balance=" + balance +
                '}';
    }

    /*Getters & Setters*/
    /**
     * Devuelve la clave primaria de un período de movimientos.
     * */
    public long getPeriodoId() {
        return periodoId;
    }

    /**
     * Setea la clave primaria de un período de movimientos.
     * */
    public void setPeriodoId(long periodoId) {
        this.periodoId = periodoId;
    }

    /**
     * Devuelve la fecha en la que el período de movimientos va a comenzar.
     * */
    public Date getDesde() {
        return desde;
    }

    /**
     * Setea la fecha en la que el período de movimientos va a comenzar.
     * */
    public void setDesde(Date desde) {
        this.desde = desde;
    }

    /**
     * Devuelve la fecha en la que el período de movimientos va a finalizar.
     * */
    public Date getHasta() {
        return hasta;
    }

    /**
     * Setea la fecha en la que el período de movimientos va a finalizar.
     * */
    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    /**
     * Devuelve la plata disponible con la que el período de movimientos ha comenzado.
     * */
    public long getBalanceInicial() {
        return balanceInicial;
    }

    /**
     * Setea la plata disponible con la que el período de movimientos va a comenzar.
     * */
    public void setBalanceInicial(long balanceInicial) {
        this.balanceInicial = balanceInicial;
    }

    /**
     * Devuelve la plata que el período dispone a diario mediante la división del balance inicial
     * por la cantidad de días que dura el período.
     * */
    public long getDisponibleDiario() {
        long dias = hasta.getTime() - desde.getTime();
        dias = dias / (1000*60*60*24) + 1;
        if (dias == 0) { return 0; }
        return balanceInicial / dias;
    }

    /**
     * Balance que se va modificando luego del cierre de cada Diario.
     * */
    public long getBalance() {
        return balance;
    }

    /**
     * Balance que se va modificando luego del cierre de cada Diario.
     * */
    public void setBalance(long balance) {
        this.balance = balance;
    }

    public long getSaldoRestante() {
        return saldoRestante;
    }

    public void setSaldoRestante(long saldoRestante) {
        this.saldoRestante = saldoRestante;
    }
}
