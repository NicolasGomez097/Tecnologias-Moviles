package org.moviles.persistance;

import com.google.gson.Gson;

import org.moviles.Constants;
import org.moviles.Context;
import org.moviles.utils.Util;
import org.moviles.model.Configuracion;

import java.io.File;

public class ConfiguracionDAO implements IConfiguracionDAO {

    public boolean save(Configuracion user, String username) {
        String json = getJSON(user);
        File file = new File(Context.getDataDir(),
                username+"/"+Constants.USER_CONFIG);

        return Util.writeFile(file,json);
    }

    public Configuracion getConfiguracion(String username) {
        File userConfig = new File(Context.getDataDir(),
                username + "/" + Constants.USER_CONFIG);
        if(!userConfig.exists())
            return null;

        String json = Util.readFile(userConfig);

        return getFromJSON(json);
    }

    public Configuracion getFromJSON(String jsonConf){
        Configuracion conf;
        conf = new Gson().fromJson(jsonConf,Configuracion.class);
        return conf;
    }

    public String getJSON(Configuracion conf){
        String json = new Gson().toJson(conf);
        return json;
    }
}
