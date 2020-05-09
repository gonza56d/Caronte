package com.caronte.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.caronte.diarios.daos.DetalleDiarioDao;
import com.caronte.diarios.daos.DiarioDao;
import com.caronte.diarios.daos.PeriodoDao;
import com.caronte.diarios.daos.Transactional;
import com.caronte.diarios.entities.DetalleDiario;
import com.caronte.diarios.entities.Diario;
import com.caronte.diarios.entities.Periodo;

@Database(entities = {Periodo.class, Diario.class, DetalleDiario.class}, version = 1 )
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract PeriodoDao periodoDao();
    public abstract DiarioDao diarioDao();
    public abstract DetalleDiarioDao detalleDiarioDao();
    public abstract Transactional getTransactional();
}
