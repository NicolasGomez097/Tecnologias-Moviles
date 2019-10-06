package org.moviles.business;

import org.moviles.Context;
import org.moviles.model.Configuracion;
import org.moviles.model.Usuario;
import org.moviles.persistance.ConfiguracionDAO;
import org.moviles.persistance.IConfiguracionDAO;

import java.util.List;

public class ConfiguracionBusiness {
    private IConfiguracionDAO configuracionDAO;
    private Usuario currentUser;
    private List<Usuario> listaUsuarios;
    private boolean mantenerSesion;

    public ConfiguracionBusiness() {
        configuracionDAO = new ConfiguracionDAO();
    }

    public boolean save(Configuracion c, String username) {
        if(!configuracionDAO.save(c,username))
            return false;
        return true;
    }

    public Configuracion getConfiguracion(String username) {
        Configuracion u = null;
        try {
            u = configuracionDAO.getConfiguracion(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }
}
