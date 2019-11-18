package org.moviles.activity.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.moviles.Context;
import org.moviles.activity.R;
import org.moviles.business.ClimaBusiness;
import org.moviles.model.Ciudad;
import org.moviles.model.Clima;
import org.moviles.model.Configuracion;

import java.util.List;

public class FragmentHome extends Fragment {

    private TextView txt_ciudad;
    private TextView txt_temp;
    private TextView txt_condicion;
    private TextView txt_humedad;
    private TextView txt_descripcion;
    private TextView txt_viento;
    private TextView txt_actualizacion;
    private SwipeRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contenedor = inflater.inflate(R.layout.fragment_home,container,false);

        txt_ciudad = contenedor.findViewById(R.id.txt_ciudad);
        txt_temp = contenedor.findViewById(R.id.txt_temperatura);
        txt_condicion = contenedor.findViewById(R.id.txt_condicion);
        txt_humedad = contenedor.findViewById(R.id.txt_humedad);
        txt_descripcion = contenedor.findViewById(R.id.txt_descripcion);
        txt_viento = contenedor.findViewById(R.id.txt_viento);
        txt_actualizacion = contenedor.findViewById(R.id.txt_actualizacion);
        refreshLayout = contenedor.findViewById(R.id.refresh);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargarHome();
            }
        });

        cargarHome();

        return contenedor;
    }

    private void cargarHome(){
        String username = Context.getUsuarioBusiness().getCurrentUser().getUsuario();
        Configuracion conf = Context.getConfiguracionBusiness().getConfiguracion(username);
        Ciudad c = conf.getCiudad();
        if(c != null){
            if(!refreshLayout.isRefreshing())
                refreshLayout.setRefreshing(true);

            txt_ciudad.setText(c.getName()+","+c.getCountry());
            new getCliamActualAsyncTask(c).execute();
        }else{
            Toast.makeText(getContext(),"Seleccionar una ciudad",Toast.LENGTH_LONG).show();
        }
    }

    private class getCliamActualAsyncTask extends AsyncTask<Void,Void, Clima>{
        private Ciudad ciudad;

        public getCliamActualAsyncTask(Ciudad c){
            this.ciudad = c;
        }

        @Override
        protected Clima doInBackground(Void... voids) {
            ClimaBusiness climaBusiness = Context.getClimaBusiness(getActivity().getApplication());
            Clima clima = climaBusiness.getClimaActual(getContext(),ciudad);

            if(clima == null) {
                clima = climaBusiness.getUltimoClimaActualGuardado();
            }else {
                climaBusiness.limpiarActualDB();
                climaBusiness.insertarClima(clima);
            }
            return clima;
        }

        @Override
        protected void onPostExecute(Clima clima) {
            txt_temp.setText(clima.getTemperatura()+"ºC");
            txt_condicion.setText(clima.getCondicion());
            txt_humedad.setText(clima.getHumedad()+"%");
            txt_descripcion.setText(clima.getDescripcion());
            txt_viento.setText(clima.getViento());
            txt_actualizacion.setText(clima.getFechaNumeros()+ " " +clima.getHora()+":"+clima.getMinuto());

            if(refreshLayout.isRefreshing())
                refreshLayout.setRefreshing(false);
        }
    }
}
