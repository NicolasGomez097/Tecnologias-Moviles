package org.moviles.activity.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.moviles.Context;
import org.moviles.activity.Adapters.ClimaAdapter;
import org.moviles.activity.R;
import org.moviles.business.ClimaBusiness;
import org.moviles.model.Ciudad;
import org.moviles.model.Clima;
import org.moviles.model.Configuracion;

import java.util.ArrayList;
import java.util.List;

public class FragmentClimaExtendido extends Fragment {

    private List<Clima> climaList;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contenedor = inflater.inflate(R.layout.fragment_clima_extendido,container,false);

        refreshLayout = contenedor.findViewById(R.id.refresh);

        recyclerView = contenedor.findViewById(R.id.contenedorClimaPorDia);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                actualizarLista();
            }
        });

        actualizarLista();

        return contenedor;
    }

    private void actualizarLista(){
        Configuracion conf = Context.getConfiguracionBusiness().getConfiguracion();
        Ciudad c = conf.getCiudad();
        if(c != null){
            if(!refreshLayout.isRefreshing())
                refreshLayout.setRefreshing(true);

            new updateListaAsyncTask(c).execute();

        }else{
            Toast.makeText(getContext(),"Seleccionar una ciudad",Toast.LENGTH_LONG).show();
        }
    }

    private class updateListaAsyncTask extends AsyncTask<Void,Void,Void>{

        private Ciudad ciudad;

        public updateListaAsyncTask(Ciudad ciudad){
            this.ciudad = ciudad;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            ClimaBusiness climaBusiness = Context.getClimaBusiness(getActivity().getApplication());

            climaList = climaBusiness.getListaClima(getContext(),ciudad);
            if(climaList == null){
                climaList = climaBusiness.getListaClimaGuardado();
            }else{
                climaBusiness.limpiarExtendidoDB();
                for(Clima clima:climaList){
                    climaBusiness.insertarClima(clima);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void voids) {
            recyclerView.setAdapter(new ClimaAdapter(climaList));
            if(refreshLayout.isRefreshing())
                    refreshLayout.setRefreshing(false);
        }
    }
}
