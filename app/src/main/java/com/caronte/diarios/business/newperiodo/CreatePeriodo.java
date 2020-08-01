package com.caronte.diarios.business.newperiodo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.caronte.NewDetalleDiarioActivity;
import com.caronte.NewPeriodoActivity;
import com.caronte.Utils;
import com.caronte.diarios.entities.Periodo;
import com.caronte.exceptions.BusinessException;
import com.caronte.room.AppDatabase;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class CreatePeriodo extends AsyncTask<Void, Void, Periodo> {

    private WeakReference<Activity> weakActivity;
    private IntBusNewPeriodo llamador;
    private AppDatabase db;
    private Periodo periodo;

    public CreatePeriodo(Activity activity, IntBusNewPeriodo llamador, AppDatabase db,
                         String hasta, String balanceInicial) {
        try {
            weakActivity = new WeakReference<>(activity);
            this.llamador = llamador;
            this.db = db;
            periodo = buildPeriodo(hasta, balanceInicial);
            executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } catch (Exception e) {
            this.llamador.createPeriodoException(e.getMessage());
        }
    }

    @Override
    protected Periodo doInBackground(Void... voids) {
        db.periodoDao().insertAll(periodo);
        System.out.println("INSERT: " + periodo);
        return periodo;
    }

    @Override
    protected void onPostExecute(Periodo periodo) {
        Activity activity = weakActivity.get();
        if (activity == null) {
            return;
        }
        if (periodo != null) {
            Intent intent = new Intent(activity, NewDetalleDiarioActivity.class);
            activity.startActivity(intent);
        } else {
            llamador.createPeriodoException("Ocurrió un error.");
        }
    }

    private Periodo buildPeriodo(String hasta, String sBalanceInicial) throws BusinessException {
        Periodo periodo = null;
        try {
            validarFecha(hasta);
            validarBalanceInicial(sBalanceInicial);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            long diferenciaTiempo = Objects.requireNonNull(formato.parse(hasta)).getTime() - new Date().getTime();
            long dias = (diferenciaTiempo / (1000 * 60 * 60 * 24));
            long balanceInicial = Long.parseLong(sBalanceInicial);
            String hoy = formato.format(new Date());
            periodo = new Periodo();
            periodo.setDesde(Utils.getToday());
            periodo.setHasta(Utils.getDate(formato.parse(hasta)));
            periodo.setBalanceInicial(balanceInicial);
            periodo.setBalance(0);
            periodo.setSaldoRestante(balanceInicial);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return periodo;
    }

    /**
     * Valida que la fecha ingresada para el nuevo período no sea anterior ni igual a la fecha actual.
     * */
    private void validarFecha(String hasta) throws BusinessException, ParseException {
        if (!Utils.hasData(hasta)) {
            throw new BusinessException("Debe ingresar fecha.");
        }
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
            throw new BusinessException("Balance menor a $100.");
        }
    }

}