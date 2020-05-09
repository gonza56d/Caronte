package com.caronte;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Utils {

    public static boolean hasData(String string) {
        return string != null && string.length() > 0;
    }

    public static boolean isEmptyOrNull(String string) {
        return string == null || string.length() < 1;
    }

    public static boolean hasData(String... string) {
        for (String s : string) {
            if (s == null || s.length() < 1) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasData(ArrayList<Object> lista) {
        return lista != null && lista.size() > 0;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(
                Objects.requireNonNull(activity.getCurrentFocus()).getWindowToken(), 0);
    }

    public static Date getToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getAyer() {
        SimpleDateFormat format = new SimpleDateFormat("dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(format.format(new Date()))-1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getDate(Date fecha) {
        SimpleDateFormat dia = new SimpleDateFormat("dd");
        SimpleDateFormat mes = new SimpleDateFormat("MM");
        SimpleDateFormat ano = new SimpleDateFormat("yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dia.format(fecha)));
        calendar.set(Calendar.MONTH, Integer.parseInt(mes.format(fecha))-1);
        calendar.set(Calendar.YEAR, Integer.parseInt(ano.format(fecha)));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static long contarDias(Date desde, Date hasta) {
        long diferencia = hasta.getTime() - desde.getTime();
        long resultado = diferencia / (1000 * 60 * 60 * 24) + 2;
        return resultado;
    }

}
