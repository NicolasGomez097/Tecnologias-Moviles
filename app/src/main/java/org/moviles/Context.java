package org.moviles;

import android.app.Application;

import org.moviles.business.ClimaBusiness;
import org.moviles.business.ConfiguracionBusiness;
import org.moviles.business.UsuarioBusiness;

import java.io.File;

public class Context {
    private static UsuarioBusiness usuarioBusiness;
    private static ConfiguracionBusiness configuracionBusiness;
    private static ClimaBusiness climaBusiness;
    private static File dataDir;

    public static void setDataDir(File dir){
        dataDir = dir;
    }

    public static File getDataDir(){
        return dataDir;
    }

    public static UsuarioBusiness getUsuarioBusiness(){
        if (usuarioBusiness == null)
            usuarioBusiness = new UsuarioBusiness();

        return usuarioBusiness;
    }

    public static ConfiguracionBusiness getConfiguracionBusiness(){
        if (configuracionBusiness == null)
            configuracionBusiness = new ConfiguracionBusiness();

        return configuracionBusiness;
    }

    public static ClimaBusiness getClimaBusiness(Application app){
        if (climaBusiness == null)
            climaBusiness = new ClimaBusiness(app);

        return climaBusiness;
    }

}
