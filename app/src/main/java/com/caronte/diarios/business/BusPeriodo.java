package com.caronte.diarios.business;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.caronte.Utils;
import com.caronte.diarios.entities.Periodo;
import com.caronte.exceptions.BusinessException;
import com.caronte.room.AppDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class BusPeriodo implements IntPeriodo {
    @Override
    public void insertPeriodo(final AppDatabase db, String hasta, String balanceInicial) throws BusinessException {
        try {
            if (Utils.hasData(hasta, balanceInicial)) {
                validarFecha(hasta);
                validarBalanceInicial(balanceInicial);
                final Periodo periodo = buildPeriodo(hasta, balanceInicial);
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        db.periodoDao().insertAll(periodo);
                    }
                });
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public Periodo getPeriodo(final AppDatabase db) {
        final Periodo[] periodo = new Periodo[1];
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                periodo[0] = db.periodoDao().getPeriodo(new Date());
            }
        });
        return periodo[0];
    }

    /**
     * Valida que la fecha ingresada para el nuevo período no sea anterior ni igual a la fecha actual.
     * */
    private void validarFecha(String hasta) throws BusinessException, ParseException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        boolean fechaInvalida = Objects.requireNonNull(formato.parse(hasta)).getTime() <= new Date().getTime();
        if (fechaInvalida) {
            throw new BusinessException("Fecha inválida.");
        }
    }

    /**
     * Valida que el balance ingresado sea al menos $100.
     * */
    private void validarBalanceInicial(String balanceInicial) throws BusinessException {
        if (Utils.isEmptyOrNull(balanceInicial) || Long.parseLong(balanceInicial) < 100) {
            throw new BusinessException("Balance menor a $99.");
        }
    }

    /**
     * Construye el período nuevo a insertar a partir de la fecha y el balance.
     * */
    private Periodo buildPeriodo(@NonNull String hasta, @NonNull String sBalanceInicial) {
        Periodo periodo = null;
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            long diferenciaTiempo = Objects.requireNonNull(formato.parse(hasta)).getTime() - new Date().getTime();
            long dias = (diferenciaTiempo / (1000 * 60 * 60 * 24));
            long balanceInicial = Long.parseLong(sBalanceInicial);
            String hoy = formato.format(new Date());
            periodo = new Periodo();
            periodo.setDesde(formato.parse(hoy));
            periodo.setHasta(formato.parse(hasta));
            periodo.setBalanceInicial(balanceInicial);
            periodo.setDisponibleDiario(balanceInicial / dias);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return periodo;
    }

}
