package com.caronte;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.caronte.diarios.Diarios;
import com.caronte.diarios.entities.Diario;
import com.caronte.diarios.entities.Periodo;
import com.caronte.room.AppDatabase;
import com.caronte.room.DiarioBridge;
import com.caronte.room.IntDiarioBridge;
import com.caronte.room.IntPeriodoBridge;
import com.caronte.room.PeriodoBridge;

/**
 * Actividad que contiene la creación de un nuevo detalle diario, más la lista de detalles del día,
 * es decir sus hermanos.
 * @author Gonza
 * */
public class NewDetalleDiarioActivity extends AppCompatActivity implements IntPeriodoBridge, IntDiarioBridge {

    private Context context;
    private AppDatabase db;
    private EditText txtDetalleDiarioDescripcion;
    private EditText txtDetalleDiarioGasto;
    private Button btnNewDetalleDiario;
    private Periodo periodo;
    private Diario diario;
    private PeriodoBridge periodoBridge;
    private DiarioBridge diarioBridge;

    /******************************** Implementación de actividad ********************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_detalle_diario_activity);
        context = this;
        initXml();
    }

    @Override
    protected void onStart() {
        super.onStart();
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "caronte").build();
        findPeriodo();
        findDiario();
    }

    /**
     * Consulta a la base de datos por el período actual, y en caso de ser devuelto nulo, cambia
     * la actividad a NewPeriodoActivity para que el usuario comience un nuevo período y el mismo
     * sea persistido.
     */
    private void findPeriodo() {
        periodoBridge = new PeriodoBridge(this, this, db, new Periodo());
    }

    /**
     * Consulta a la base de datos por el diario actual, y en caso de ser devuelto nulo, crea el
     * nuevo diario y lo persiste.
     * */
    private void findDiario() {
        diarioBridge = new DiarioBridge(this, this, db, new Diario());
    }

    /*********************************** Inicialización de XML ***********************************/
    private void initXml() {
        initTxtDetalleDiarioDescripcion();
        initTxtDetalleDiarioGasto();
        initBtnNewDetalleDiario();
    }

    private void initTxtDetalleDiarioDescripcion() {
        txtDetalleDiarioDescripcion = findViewById(R.id.txt_detalle_diario_descripcion);
    }

    private void initTxtDetalleDiarioGasto() {
        txtDetalleDiarioGasto = findViewById(R.id.txt_detalle_diario_gasto);
    }

    private void initBtnNewDetalleDiario() {
        btnNewDetalleDiario = findViewById(R.id.btn_new_detalle_diario);
        btnNewDetalleDiario.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btnNewDetalleDiarioClick();
            }
        });
    }

    /************************************ Interacción con XML ************************************/
    private void btnNewDetalleDiarioClick() {
        String descripcion = txtDetalleDiarioDescripcion.getText().toString();
        String gasto = txtDetalleDiarioGasto.getText().toString();
        Diarios.insertDetalleDiario(db, descripcion, gasto);
    }

    /************************************* Getters & Setters *************************************/
    public Periodo getPeriodo() {
        return periodo;
    }

    @Override
    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Diario getDiario() {
        return diario;
    }

    @Override
    public void setDiario(Diario driario) {

    }
}
