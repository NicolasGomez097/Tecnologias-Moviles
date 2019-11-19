package org.moviles.business;

import android.content.Context;

import org.moviles.model.Ciudad;
import org.moviles.model.Clima;
import org.moviles.persistance.repository.ClimaRepository;

import java.util.List;

public class ClimaBusiness {

    private ClimaRepository repo;
    private Context context;

    public ClimaBusiness(Context context){
        this.context = context;
        repo = new ClimaRepository(context);
    }

    public Clima getClimaActual(Context context,Ciudad ciudad){
        return repo.getClimaAcual(context,ciudad);
    }

    public Clima getUltimoClimaActualGuardado(){
        return repo.obtenerLastClimaActual();
    }

    public void insertarClima(Clima clima){
        repo.InsertarClima(clima);
    }

    public void eliminarClima(Integer idClima){
        repo.EliminarClima(idClima);
    }

    public List<Clima> getListaClima(Context context, Ciudad ciudad){
        return repo.getClimaExtendido(context,ciudad);
    }
    public List<Clima> getListaClimaGuardado() {return repo.obtenerListaClima();}

    public void limpiarActualDB(){
        repo.LimpiarActualDB();
    }

    public void limpiarExtendidoDB(){
        repo.LimpiarExtendidoDB();
    }
}
