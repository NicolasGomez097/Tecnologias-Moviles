package org.moviles.persistance.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import org.moviles.model.Clima;

import java.util.List;

@Dao
public interface ClimaDAO{

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertar(Clima clima);

    @Query("SELECT * FROM clima WHERE esExtendido = 0")
    Clima getLastActual();

    @Query("SELECT * FROM clima WHERE esExtendido = 1 ORDER BY anio,mes,dia,hora,minuto ASC")
    List<Clima> getAllExtendido();

    @Query("DELETE FROM clima WHERE id == :idClima")
    void eliminar(Integer idClima);

    @Query("DELETE FROM clima WHERE esExtendido = 1")
    void limpiarExtendidoDB();

    @Query("DELETE FROM clima WHERE esExtendido = 0")
    void limpiarActualDB();
}
