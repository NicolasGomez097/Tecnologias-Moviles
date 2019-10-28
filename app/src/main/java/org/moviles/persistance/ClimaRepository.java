package org.moviles.persistance;

import android.app.Application;
import android.os.AsyncTask;

import org.moviles.model.Clima;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ClimaRepository {
    private ClimaDAO climaDAO;

    public ClimaRepository(Application application) {
        Database database = Database.getDatabase(application.getApplicationContext());
        climaDAO = database.climaDAO();
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
