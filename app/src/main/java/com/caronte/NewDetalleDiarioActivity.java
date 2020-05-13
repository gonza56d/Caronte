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
import com.caronte.diarios.business.newdetallediario.FindDiariosAnteriores;
import com.caronte.diarios.business.newdetallediario.FindPeriodo;
import com.caronte.diarios.business.newdetallediario.IntBusNewDetalleDiario;
import com.caronte.diarios.entities.DetalleDiario;
import com.caronte.diarios.entities.Diario;
import com.caronte.diarios.entities.Periodo;
import com.caronte.room.AppDatabase;

import java.util.List;

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
    private List<Diario> diariosAnteriores;
    private TableLayout tableListDiariosAnteriores;
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

    @Override
    public void findDiariosAnteriores() {
        new FindDiariosAnteriores(this, this, db, periodo);
    }

    @Override
    public void setDiariosAnteriores(List<Diario> diariosAnteriores) {
        this.diariosAnteriores = diariosAnteriores;
        updateTablaDiariosAnteriores();
    }

    /**
     * Método llamado desde el business (FindDiario) en caso de no haberse encontrado un Diario,
     * para así crear uno.
     * */
    @Override
    public void createDiario() {
        new CreateDiario(this, this, db, periodo, diarioAyer);
    }

    /*********************************** Inicialización de XML ***********************************/
    private void initXml() {
        initTxtDetalleDiarioDescripcion();
        initTxtDetalleDiarioGasto();
        initBtnNewDetalleDiario();
        initTableListDetallesDiarios();
        initTableListDiariosAnteriores();
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

    private void initTableListDiariosAnteriores() {
        tableListDiariosAnteriores = findViewById(R.id.table_list_diarios_anteriores);
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
        if (diario.getSobra() < 0) {
            lblDiarioSobra.setTextColor(getResources().getColor(R.color.colorRed));
        }
        lblDiarioBalance.setText("$ " + String.valueOf(diario.getBalance()));
        lblDiarioBalance.setTextColor(getResources().getColor(R.color.softgray));
        if (diario.getBalance() > 0) {
            lblDiarioBalance.setTextColor(getResources().getColor(R.color.colorGreen));
        } else if (diario.getBalance() < 0) {
            lblDiarioBalance.setTextColor(getResources().getColor(R.color.colorRed));
        }
    }

    private void updateTablaDiariosAnteriores() {
        tableListDiariosAnteriores.removeAllViews();
        if (diariosAnteriores != null && diariosAnteriores.size() > 0) {
            for (Diario diarioAnterior : diariosAnteriores) {
                TableRow row = new TableRow(this);
                TextView txtFecha = new TextView(this);
                TextView txtGasto = new TextView(this);
                TextView txtSobra = new TextView(this);
                TextView txtBalance = new TextView(this);
                txtFecha.setTextColor(getResources().getColor(R.color.softgray));
                txtFecha.setTextSize(18);
                txtFecha.setText(diarioAnterior.getFechaFormateada());
                txtGasto.setTextColor(getResources().getColor(R.color.softgray));
                txtGasto.setTextSize(18);
                txtGasto.setText("$ " + String.valueOf(diarioAnterior.getGasto()));
                txtSobra.setTextColor(getResources().getColor(R.color.softgray));
                txtSobra.setTextSize(18);
                txtSobra.setText("$ " + String.valueOf(diarioAnterior.getSobra()));
                if (diarioAnterior.getSobra() > 0) {
                    txtSobra.setTextColor(getResources().getColor(R.color.colorGreen));
                } else if (diarioAnterior.getSobra() < 0) {
                    txtSobra.setTextColor(getResources().getColor(R.color.colorRed));
                }
                txtBalance.setTextColor(getResources().getColor(R.color.softgray));
                txtBalance.setTextSize(18);
                txtBalance.setText("$ " + String.valueOf(diarioAnterior.getBalance()));
                row.addView(txtFecha);
                row.addView(txtGasto);
                row.addView(txtSobra);
                row.addView(txtBalance);
                tableListDiariosAnteriores.addView(row);
            }
        }
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
            txtHora.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            txtDescripcion.setText(detalleDiario.getDescripcion());
            txtDescripcion.setTextSize(18);
            txtDescripcion.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
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
        updateTablaDiario();
        findDetallesDiarios();
    }

    @Override
    public void setDiarioAyer(Diario diario) {
        this.diarioAyer = diario;
    }

}
