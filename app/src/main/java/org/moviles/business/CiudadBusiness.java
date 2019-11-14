package org.moviles.business;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.moviles.NotConnectedExeption;
import org.moviles.Util;
import org.moviles.model.Ciudad;

import java.lang.reflect.Type;
import java.util.List;

public class CiudadBusiness {

    public CiudadBusiness(){

    }

    public List<Ciudad> getCiudadLike(Context context,String ciudad){
        String url = "http://143.0.100.212:8080/ciudades?ciudad="+ciudad;
        try{
            String res = Util.GetHttp(context,url);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Ciudad>>(){}.getType();
            List<Ciudad> list = gson.fromJson(res,listType);
            return list;
        }catch (NotConnectedExeption e){
            e.printStackTrace();
            return null;
        }

    }
}
