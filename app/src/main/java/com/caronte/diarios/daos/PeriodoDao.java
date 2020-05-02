package com.caronte.diarios.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.caronte.diarios.entities.Periodo;

import java.util.Date;

@Dao
public interface PeriodoDao {
    @Insert
    void insertAll(Periodo... periodos);

    @Query("SELECT * FROM periodo WHERE desde <= :hoy AND hasta >= :hoy")
    Periodo getPeriodo(Date hoy);

}
