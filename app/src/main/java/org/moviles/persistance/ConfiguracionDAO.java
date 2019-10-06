package org.moviles.persistance;

import org.json.JSONObject;
import org.moviles.Constants;
import org.moviles.Context;
import org.moviles.Util;
import org.moviles.model.Configuracion;
import org.moviles.model.Usuario;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
        try {
            JSONObject json = new JSONObject(jsonConf);
            conf = new Configuracion();
            conf.setUnidad(json.getString("unidad"));
            conf.setNotificaciones(json.getBoolean("notificacacion"));
            if(conf.isNotificaciones()){
                conf.setDias(json.getString("dias"));
                conf.setHora(json.getString("hora"));
            }
            return conf;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String getJSON(Configuracion conf){
        try{
            JSONObject json = new JSONObject();
            json.put("unidad",conf.getUnidad());
            json.put("notificacacion",conf.isNotificaciones());
            if(conf.isNotificaciones()){
                json.put("dias",conf.getDias());
                json.put("hora",conf.getHora());
            }
            return json.toString();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
