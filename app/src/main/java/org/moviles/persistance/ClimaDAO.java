package org.moviles.persistance;

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

    @Query("SELECT * FROM clima ORDER BY dt ASC")
    List<Clima> getAll();

    @Query("DELETE FROM clima WHERE clave == :idClima")
    void eliminar(Integer idClima);

    @Query("DELETE FROM clima")
    void limpiarDB();
}
