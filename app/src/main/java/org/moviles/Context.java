package org.moviles;

import android.app.Application;

import org.moviles.business.CiudadBusiness;
import org.moviles.business.ClimaBusiness;
import org.moviles.business.ConfiguracionBusiness;
import org.moviles.business.UsuarioBusiness;

import java.io.File;

public class Context {
    private static UsuarioBusiness usuarioBusiness;
    private static ConfiguracionBusiness configuracionBusiness;
    private static ClimaBusiness climaBusiness;
    private static CiudadBusiness ciudadBusiness;
    private static File dataDir;
    private static android.content.Context context;


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

    public static CiudadBusiness getCiudadBusiness(){
        if (ciudadBusiness == null)
            ciudadBusiness = new CiudadBusiness();

        return ciudadBusiness;
    }

    public static void setContext(android.content.Context c){
        context = c;
    }

    public static android.content.Context getStringContext(){
        return context;
    }

}
