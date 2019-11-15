package org.moviles.activity.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.moviles.Context;
import org.moviles.activity.R;
import org.moviles.model.Ciudad;
import org.moviles.model.Clima;
import org.moviles.model.Configuracion;

public class FragmentHome extends Fragment {

    private TextView txt_ciudad;
    private TextView txt_temp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contenedor = inflater.inflate(R.layout.fragment_home,container,false);

        txt_ciudad = contenedor.findViewById(R.id.txt_ciudad);

        cargarHome();

        return contenedor;
    }

    private void cargarHome(){
        String username = Context.getUsuarioBusiness().getCurrentUser().getUsuario();
        Configuracion conf = Context.getConfiguracionBusiness().getConfiguracion(username);
        Ciudad c = conf.getCiudad();
        if(c != null){
            txt_ciudad.setText(c.getName()+","+c.getCountry());
            new getCliamActualAsyncTask(c).execute();
        }
    }

    private class getCliamActualAsyncTask extends AsyncTask<Void,Void, Clima>{
        private Ciudad ciudad;

        public getCliamActualAsyncTask(Ciudad c){
            this.ciudad = c;
        }
        @Override
        protected Clima doInBackground(Void... voids) {
            return Context.getClimaBusiness(getActivity().getApplication())
                    .getClimaActual(getContext(),ciudad);
        }

        @Override
        protected void onPostExecute(Clima clima) {
            super.onPostExecute(clima);
        }
    }
}
