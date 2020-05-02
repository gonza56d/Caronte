package com.caronte.diarios.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.caronte.diarios.entities.Diario;

import java.util.Date;

@Dao
public interface DiarioDao {
    @Insert
    void insertAll(Diario... diarios);

    @Query("SELECT * FROM diario where diarioId = :hoy")
    Diario getDiario(Date hoy);
}
