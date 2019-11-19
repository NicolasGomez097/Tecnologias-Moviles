package org.moviles.business;

import org.moviles.Context;
import org.moviles.model.Configuracion;
import org.moviles.model.Usuario;
import org.moviles.persistance.ConfiguracionDAO;
import org.moviles.persistance.IConfiguracionDAO;

public class ConfiguracionBusiness {
    private IConfiguracionDAO configuracionDAO;
    private Usuario currentUser;
    private Configuracion currentConf;

    public ConfiguracionBusiness() {
        configuracionDAO = new ConfiguracionDAO();
    }

    public boolean save(Configuracion c) {
        if(currentUser == null)
            currentUser = Context.getUsuarioBusiness().getCurrentUser();

        if(!configuracionDAO.save(c,currentUser.getUsuario()))
            return false;

        currentConf = c;
        return true;
    }

    public boolean createConf(Configuracion conf, String username){

        if(!configuracionDAO.save(conf,username))
            return false;

        return true;
    }

    public Configuracion getConfiguracion() {
        if(currentConf == null){
            currentUser = Context.getUsuarioBusiness().getCurrentUser();
            currentConf = configuracionDAO.getConfiguracion(currentUser.getUsuario());
        }

        return currentConf;
    }
}
