package com.caronte;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.caronte.diarios.business.newperiodo.CreatePeriodo;
import com.caronte.diarios.business.newperiodo.IntBusNewPeriodo;
import com.caronte.room.AppDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Actividad que contiene la creación de un nuevo período.
 * @author Gonza
 * */
public class NewPeriodoActivity extends AppCompatActivity implements IntBusNewPeriodo {

    private Context context;
    private AppDatabase db;
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    final Calendar calendar = Calendar.getInstance();
    private EditText txtPeriodoHasta;           //Fecha donde termina el período
    private EditText txtPeriodoBalanceInicial;  //Monto disponible inicial para el período
    private Button btnNewPeriodo;               //Botón para intentar insertar período
    private TextView lblExceptions;             //Label para mostrar excepciones

    /******************************** Implementación de actividad ********************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_periodo_activity);
        context = this;
        initXml();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @Override
    protected void onStart() {
        super.onStart();
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "caronte").build();
    }

    /*********************************** Inicialización de XML ***********************************/
    private void initXml() {
        initTxtPeriodoHasta();
        initTxtPeriodoBalanceInicial();
        initBtnNewPeriodo();
        initLblExceptions();
    }

    private void clearFocus() {
        txtPeriodoHasta.clearFocus();
        txtPeriodoBalanceInicial.clearFocus();
        btnNewPeriodo.clearFocus();
    }

    private void clearFields() {
        txtPeriodoHasta.setText(null);
        txtPeriodoBalanceInicial.setText(null);
        clearFocus();
    }

    private void initTxtPeriodoHasta() {
        txtPeriodoHasta = findViewById(R.id.txt_periodo_hasta);
        txtPeriodoHasta.setInputType(EditorInfo.TYPE_NULL);
        final DatePickerDialog.OnDateSetListener fecha = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                txtPeriodoHasta.setText(formato.format(calendar.getTime()));
                clearFocus();
            }
        };
        txtPeriodoHasta.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Utils.hideSoftKeyboard(NewPeriodoActivity.this);
                    new DatePickerDialog(context, fecha, calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });
    }

    private void initTxtPeriodoBalanceInicial() {
        txtPeriodoBalanceInicial = findViewById(R.id.txt_periodo_balance_inicial);
    }

    private void initBtnNewPeriodo() {
        btnNewPeriodo = findViewById(R.id.btn_new_periodo);
        btnNewPeriodo.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                btnNewPeriodoClick();
            }
        });
    }

    private void initLblExceptions() {
        lblExceptions = findViewById(R.id.lbl_exception);
    }

    /************************************ Interacción con XML ************************************/
    private void btnNewPeriodoClick() { //Clickea el botón para crear un nuevo período
        lblExceptions.setText(null);
        String hasta = txtPeriodoHasta.getText().toString();
        String balanceInicial = txtPeriodoBalanceInicial.getText().toString();
        Utils.hideSoftKeyboard(this);
        try {
            new CreatePeriodo(this, this, db, hasta, balanceInicial);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createPeriodoException(String exceptionMessage) {
        lblExceptions.setText(exceptionMessage);
    }

}
