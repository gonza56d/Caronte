package com.caronte.diarios.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.caronte.diarios.entities.DetalleDiario;

import java.util.Date;
import java.util.List;

@Dao
public interface DetalleDiarioDao {

    @Query("SELECT * FROM detallediario WHERE diarioId = :hoy")
    List<DetalleDiario> getDetallesDiarios(Date hoy);

    @Insert
    void insertAll(DetalleDiario ... detallesDiarios);

    @Query("DELETE FROM detallediario")
    void deleteAll();

}
