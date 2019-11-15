package org.moviles.business;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import org.moviles.activity.R;
import org.moviles.model.Ciudad;
import org.moviles.model.Clima;
import org.moviles.persistance.ClimaRepository;

import java.util.List;

public class ClimaBusiness {

    private ClimaRepository repo;
    private Application app;

    public ClimaBusiness(Application app){
        this.app = app;
        repo = new ClimaRepository(app);
        repo.LimpiarDB();
        cargarClimaIncial();
    }

    public Clima getClimaActual(Context context,Ciudad ciudad){
        return repo.getClimaAcual(context,ciudad);
    }

    public void insertarClima(Clima clima){
        repo.InsertarClima(clima);
    }

    public void eliminarClima(Integer idClima){
        repo.EliminarClima(idClima);
    }

    public List<Clima> listaClima(){
        return repo.ObtenerListaClima();
    }

    public void limpiarDB(){
        repo.LimpiarDB();
    }

    private void cargarClimaIncial(){
        /*Clima aux = new Clima();
        aux.setDia("Lunes");
        aux.setDiaNumero(28);
        aux.setMes("Octubre");
        aux.setAnio(2019);
        aux.setCondicion(app.getString(R.string.tormenta_electrica_granizo));
        aux.setHumedad(50);
        aux.setTempMaxima(20.2);
        aux.setTempMinima(5.2);
        aux.setViento("NE 15");
        aux.setDescripcion("Corre a guardar el auto que cae piedra");

        repo.InsertarClima(aux);

        aux = new Clima();
        aux.setDia("Martes");
        aux.setDiaNumero(29);
        aux.setMes("Octubre");
        aux.setAnio(2019);
        aux.setCondicion(app.getString(R.string.tormenta_electrica));
        aux.setHumedad(100);
        aux.setTempMaxima(10.7);
        aux.setTempMinima(-5.7);
        aux.setViento("E 30");
        aux.setDescripcion("te acordaste de guardar el auto?");

        repo.InsertarClima(aux);

        aux = new Clima();
        aux.setDia("Miercoles");
        aux.setDiaNumero(30);
        aux.setMes("Octubre");
        aux.setAnio(2019);
        aux.setCondicion(app.getString(R.string.nublado));
        aux.setHumedad(100);
        aux.setTempMaxima(15.7);
        aux.setTempMinima(2.0);
        aux.setViento("N 5");
        aux.setDescripcion("El seguro te cubrió?");
        repo.InsertarClima(aux);

        aux = new Clima();
        aux.setDia("Jueves");
        aux.setDiaNumero(31);
        aux.setMes("Octubre");
        aux.setAnio(2019);
        aux.setCondicion(app.getString(R.string.parcialmente_nublado));
        aux.setHumedad(100);
        aux.setTempMaxima(20.1);
        aux.setTempMinima(6.9);
        aux.setViento("SE 2");
        aux.setDescripcion("Esta lindo el día para el asado.");

        repo.InsertarClima(aux);*/
    }
}
