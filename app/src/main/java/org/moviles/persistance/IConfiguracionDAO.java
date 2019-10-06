package org.moviles.persistance;

import org.moviles.model.Configuracion;
import org.moviles.model.Usuario;

import java.util.List;

public interface IConfiguracionDAO {
    public Configuracion getConfiguracion(String username);
    public boolean save(Configuracion u, String username);
}
