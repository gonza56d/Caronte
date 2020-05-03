package com.caronte.diarios.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.caronte.diarios.entities.DetalleDiario;

@Dao
public interface DetalleDiarioDao {
    @Insert
    void insertAll(DetalleDiario ... detallesDiarios);

    @Query("DELETE FROM detallediario")
    void deleteAll();
}
