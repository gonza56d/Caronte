package com.caronte;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.caronte.diarios.business.newdetallediario.CreateDetalleDiario;
import com.caronte.diarios.business.newdetallediario.CreateDiario;
import com.caronte.diarios.business.newdetallediario.FindDetallesDiarios;
import com.caronte.diarios.business.newdetallediario.FindDiario;
import com.caronte.diarios.business.newdetallediario.FindPeriodo;
import com.caronte.diarios.business.newdetallediario.IntBusNewDetalleDiario;
import com.caronte.diarios.entities.DetalleDiario;
import com.caronte.diarios.entities.Diario;
import com.caronte.diarios.entities.Periodo;
import com.caronte.room.AppDatabase;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Actividad que contiene la creación de un nuevo detalle diario, más la lista de detalles del día,
 * es decir sus hermanos.
 * @author Gonza
 * */
public class NewDetalleDiarioActivity extends AppCompatActivity implements IntBusNewDetalleDiario {

    private Context context;
    private AppDatabase db;
    private EditText txtDetalleDiarioDescripcion;
    private EditText txtDetalleDiarioGasto;
    private Button btnNewDetalleDiario;
    private Periodo periodo;
    private Diario diario;
    private Diario diarioAyer;
    private TableLayout tableListDetallesDiarios;
    private TextView lblExceptions;
    private TextView lblDiarioGasto;
    private TextView lblDiarioSobra;
    private TextView lblDiarioBalance;

    /******************************** Implementación de actividad ********************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_detalle_diario_activity);
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
        findPeriodo();
    }

    /**
     * Consulta a la base de datos por el período actual, y en caso de ser devuelto nulo, cambia
     * la actividad a NewPeriodoActivity para que el usuario comience un nuevo período y el mismo
     * sea persistido.
     */
    private void findPeriodo() {
        new FindPeriodo(this, this, db);
    }

    /**
     * Consulta a la base de datos por el diario actual, y en caso de ser devuelto nulo, crea el
     * nuevo diario y lo persiste.
     * */
    @Override
    public void findDiario(boolean ayer) {
        new FindDiario(this, this, db, ayer);
    }

    @Override
    public void findDetallesDiarios() {
        new FindDetallesDiarios(this, this, db);
    }

    @Override
    public void setDetallesDiarios(List<DetalleDiario> listDetallesDiarios) {
        diario.setDetalles(listDetallesDiarios);
        updateTablaDiario();
        updateTablaDetalles();
    }

    /**
     * Método llamado desde el business (FindDiario) en caso de no haberse encontrado un Diario,
     * para así crear uno.
     * */
    @Override
    public void createDiario() {
        new CreateDiario(this, this, db, periodo);
    }

    /*********************************** Inicialización de XML ***********************************/
    private void initXml() {
        initTxtDetalleDiarioDescripcion();
        initTxtDetalleDiarioGasto();
        initBtnNewDetalleDiario();
        initTableListDetallesDiarios();
        initLblExceptions();
        initLblDiarioGasto();
        initLblDiarioSobra();
        initLblDiarioBalance();
    }

    private void initLblDiarioGasto() {
        lblDiarioGasto = findViewById(R.id.lbl_diario_gasto);
    }

    private void initLblDiarioSobra() {
        lblDiarioSobra = findViewById(R.id.lbl_diario_sobra);
    }

    private void initLblDiarioBalance() {
        lblDiarioBalance = findViewById(R.id.lbl_diario_balance);
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

    private void initTableListDetallesDiarios() {
        tableListDetallesDiarios = findViewById(R.id.table_list_detalles_diarios);
    }

    private void initLblExceptions() {
        lblExceptions = findViewById(R.id.lbl_exception);
    }

    /************************************ Interacción con XML ************************************/
    private void btnNewDetalleDiarioClick() {
        Utils.hideSoftKeyboard(this);
        lblExceptions.setText(null);
        String descripcion = txtDetalleDiarioDescripcion.getText().toString();
        String gasto = txtDetalleDiarioGasto.getText().toString();
        new CreateDetalleDiario(this, this, db, periodo, diario, descripcion, gasto);
    }


    @Override
    public void raiseException(String mensaje) {
        clearFields();
        lblExceptions.setText(mensaje);
    }

    private void clearFields() {
        txtDetalleDiarioDescripcion.setText(null);
        txtDetalleDiarioDescripcion.clearFocus();
        txtDetalleDiarioGasto.setText(null);
        txtDetalleDiarioGasto.clearFocus();
        btnNewDetalleDiario.clearFocus();
    }

    @Override
    public void updateMovimiento(Periodo periodo, Diario diario) {
        this.periodo = periodo;
        this.diario = diario;
        updateTablaDiario();
        updateTablaDetalles();
        clearFields();
    }

    public void updateTablaDiario() {
        lblDiarioGasto.setText("$ " + String.valueOf(diario.getGasto()));
        lblDiarioBalance.setTextColor(getResources().getColor(R.color.softgray));
        lblDiarioSobra.setText("$ " + String.valueOf(diario.getSobra()));
        lblDiarioSobra.setTextColor(getResources().getColor(R.color.softgray));
        lblDiarioBalance.setText("$ " + String.valueOf(diario.getBalance()));
        lblDiarioBalance.setTextColor(getResources().getColor(R.color.softgray));
    }

    public void updateTablaDetalles() {
        tableListDetallesDiarios.removeAllViews();
        for (DetalleDiario detalleDiario : diario.getDetalles()) {
            TableRow row = new TableRow(this);
            TextView txtHora = new TextView(this);
            txtHora.setTextColor(getResources().getColor(R.color.softgray));
            TextView txtDescripcion = new TextView(this);
            txtDescripcion.setTextColor(getResources().getColor(R.color.softgray));
            TextView txtGasto = new TextView(this);
            txtGasto.setTextColor(getResources().getColor(R.color.softgray));
            txtGasto.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
            txtGasto.setText("$ " + String.valueOf(detalleDiario.getGasto()));
            txtGasto.setTextSize(18);
            txtHora.setText(detalleDiario.getHoraFormateada());
            txtHora.setTextSize(18);
            txtDescripcion.setText(detalleDiario.getDescripcion());
            txtDescripcion.setTextSize(18);
            row.addView(txtHora);
            row.addView(txtDescripcion);
            row.addView(txtGasto);
            tableListDetallesDiarios.addView(row);
        }
    }

    /************************************* Getters & Setters *************************************/
    @Override
    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    @Override
    public void setDiario(final Diario diario) {
        this.diario = diario;
        if (diarioAyer != null && !diario.isSaldoFijado()) {
            this.diario.setBalance(diario.getBalance() + diarioAyer.getBalance());
        }
        this.diario.setSaldoFijado(true);
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.diarioDao().update(diario);
            }
        });
        updateTablaDiario();
        findDetallesDiarios();
    }

    @Override
    public void setDiarioAyer(Diario diario) {
        this.diarioAyer = diario;
    }

}
