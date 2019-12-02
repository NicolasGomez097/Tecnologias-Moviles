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


    /**
     * Este metodo debe ser ejecutado si o si para el correcto funcionamiento de la aplicacion.
     * */
    public static void setContext(android.content.Context c){
        context = c;
        //dataDir = c.getDataDir();
        dataDir = c.getFilesDir();
    }
    public static android.content.Context getContext(){return context;}

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

    public static ClimaBusiness getClimaBusiness(android.content.Context context){
        if (climaBusiness == null)
            climaBusiness = new ClimaBusiness(context);

        return climaBusiness;
    }

    public static CiudadBusiness getCiudadBusiness(){
        if (ciudadBusiness == null)
            ciudadBusiness = new CiudadBusiness();

        return ciudadBusiness;
    }


    public static android.content.Context getStringContext(){
        return context;
    }

}
