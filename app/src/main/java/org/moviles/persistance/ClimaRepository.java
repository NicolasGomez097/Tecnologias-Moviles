package org.moviles.persistance;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.moviles.NotConnectedExeption;
import org.moviles.Util;
import org.moviles.model.Ciudad;
import org.moviles.model.Clima;
import org.moviles.model.dto.ClimaDTO;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ClimaRepository {
    private ClimaDAO climaDAO;
    private final String apiKey = "&APPID=b4398f533661e8496cc708daa69e3ac8";
    private final String webURL = "http://api.openweathermap.org/data/2.5";
    private final String URLConf = "&units=metric&lang=es";
    private final String URLEnd = apiKey + URLConf;


    public ClimaRepository(Application application) {
        Database database = Database.getDatabase(application.getApplicationContext());
        climaDAO = database.climaDAO();
    }

    public Clima getClimaAcual(Context context, Ciudad c){
        String url = webURL+"/weather?id="+c.getId()+ URLEnd;
        try{
            String res = Util.GetHttp(context,url);
            Gson gson = new Gson();
            ClimaDTO clima = gson.fromJson(res,ClimaDTO.class);
            return clima.getClima();
        }catch (NotConnectedExeption e){
            List<Clima> list = ObtenerListaClima();
            if(list != null && list.size() >= 1)
                return list.get(0);
            return ObtenerListaClima().get(0);
        }
    }

    public List<Clima> ObtenerListaClima() {
        List<Clima> out = null;
        try {
            out = new ObtenerTodoClimaAsyncTask(climaDAO).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        return out;
    }

    public void InsertarClima(Clima clima) {
        new InsertarClimaAsyncTask(climaDAO).execute(clima);
    }

    public void EliminarClima(Integer idClima) {
        new EliminarClimaAsyncTask(climaDAO).execute(idClima);
    }

    public void LimpiarDB() {
        new LimpiarDBAsyncTask(climaDAO).execute();
    }

    private static class InsertarClimaAsyncTask extends AsyncTask<Clima, Void, Void> {
        private ClimaDAO asyncTaskClimaDao;
        InsertarClimaAsyncTask(ClimaDAO climaDAO) {
            asyncTaskClimaDao = climaDAO;
        }
        @Override
        protected Void doInBackground(Clima... climas) {
            asyncTaskClimaDao.insertar(climas[0]);
            return null;
        }
    }

    private static class EliminarClimaAsyncTask extends AsyncTask<Integer, Void, Void> {
        private ClimaDAO asyncTaskClimaDao;
        EliminarClimaAsyncTask(ClimaDAO climaDAO) {
            asyncTaskClimaDao = climaDAO;
        }
        @Override
        protected Void doInBackground(Integer... id) {
            asyncTaskClimaDao.eliminar(id[0]);
            return null;
        }
    }

    private static class ObtenerTodoClimaAsyncTask extends AsyncTask<Void, Void, List<Clima>> {
        private ClimaDAO asyncTaskClimaDao;
        ObtenerTodoClimaAsyncTask(ClimaDAO climaDAO) {
            asyncTaskClimaDao = climaDAO;
        }
        @Override
        protected List<Clima> doInBackground(Void... voids) {
            return asyncTaskClimaDao.getAll();
        }
    }

    private static class LimpiarDBAsyncTask extends AsyncTask<Void, Void, Void> {
        private ClimaDAO asyncTaskClimaDao;
        LimpiarDBAsyncTask(ClimaDAO climaDAO) {
            asyncTaskClimaDao = climaDAO;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            asyncTaskClimaDao.limpiarDB();
            return null;
        }
    }

}
