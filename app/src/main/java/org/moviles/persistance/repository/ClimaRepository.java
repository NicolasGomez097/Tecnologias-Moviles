package org.moviles.persistance.repository;

import android.content.Context;

import com.google.gson.Gson;

import org.moviles.NotConnectedExeption;
import org.moviles.utils.Util;
import org.moviles.model.Ciudad;
import org.moviles.model.Clima;
import org.moviles.model.Configuracion;
import org.moviles.model.dto.ClimaDTO;
import org.moviles.model.dto.ListClimaDTO;
import org.moviles.persistance.Database;
import org.moviles.persistance.dao.ClimaDAO;

import java.util.ArrayList;
import java.util.List;

public class ClimaRepository {
    private ClimaDAO climaDAO;
    private final String apiKey = "&APPID=b4398f533661e8496cc708daa69e3ac8";
    private final String webURL = "http://api.openweathermap.org/data/2.5";
    private String URLLang = "&lang=es";
    private String URLUnit = "&units=";


    public ClimaRepository(Context application) {
        Database database = Database.getDatabase(application.getApplicationContext());
        climaDAO = database.climaDAO();
    }

    public Clima getClimaAcual(Context context, Ciudad c){
        String url = webURL+"/weather?id="+c.getId()+ getURLEnd();
        try{
            String res = Util.GetHttp(context,url);
            Gson gson = new Gson();
            ClimaDTO climaAux = gson.fromJson(res,ClimaDTO.class);
            Clima clima = climaAux.getClima();
            clima.setEsExtendido(false);
            return clima;
        }catch (NotConnectedExeption e){
            return null;
        }
    }

    public List<Clima> getClimaExtendido(Context context, Ciudad c){
        String url = webURL+"/forecast?id="+c.getId()+ getURLEnd();
        List<Clima> out = new ArrayList<Clima>();
        try{
            String res = Util.GetHttp(context,url);

            Gson gson = new Gson();
            List<ClimaDTO> climas = gson.fromJson(res,ListClimaDTO.class).list;
            for(ClimaDTO clima: climas)
                out.add(clima.getClima());

            return out;
        }catch (NotConnectedExeption e){
            return null;
        }
    }

    public List<Clima> obtenerListaClima() {
        List<Clima> out = climaDAO.getAllExtendido();
        return out;
    }

    public Clima obtenerLastClimaActual() {
        Clima out = climaDAO.getLastActual();
        return out;
    }

    public void InsertarClima(Clima clima) {
        climaDAO.insertar(clima);
    }

    public void EliminarClima(Integer idClima) {
        climaDAO.eliminar(idClima);
    }

    public void LimpiarExtendidoDB() {
        climaDAO.limpiarExtendidoDB();
    }

    public void LimpiarActualDB() {
        climaDAO.limpiarActualDB();
    }

    private String getURLEnd(){
        Configuracion conf = org.moviles.Context.getConfiguracionBusiness().getConfiguracion();
        return apiKey + URLLang + URLUnit + conf.getUnidad();
    }

}
