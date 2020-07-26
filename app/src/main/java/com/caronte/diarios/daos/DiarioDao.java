package com.caronte.diarios.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.caronte.diarios.entities.Diario;

import java.util.Date;
import java.util.List;

@Dao
public interface DiarioDao {
    @Insert
    void insertAll(Diario... diarios);

    @Update
    void update(Diario diario);

    @Query("SELECT * FROM diario where diarioId = :hoy")
    Diario getDiario(Date hoy);

    @Query("SELECT * FROM diario WHERE diarioId < :hoy ORDER BY diarioId DESC limit 1")
    Diario getLastDiario(Date hoy);

    @Query("SELECT * FROM diario where diarioId < :hoy and diarioId >= :inicioPeriodo")
    List<Diario> getDiariosAnteriores(Date inicioPeriodo, Date hoy);

    @Query("DELETE FROM diario")
    void deleteAll();
}
