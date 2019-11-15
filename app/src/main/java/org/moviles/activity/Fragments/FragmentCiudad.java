package org.moviles.activity.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.moviles.Context;
import org.moviles.activity.R;
import org.moviles.business.ConfiguracionBusiness;
import org.moviles.model.Ciudad;
import org.moviles.model.Configuracion;

import java.util.ArrayList;
import java.util.List;

public class FragmentCiudad extends Fragment implements AdapterView.OnItemSelectedListener{

    private Button btnGuardar;
    private Button btnBuscar;
    private Spinner sp_ciudades;
    private EditText txt_ciudad;
    private List<Ciudad> ciudadList;
    private Ciudad ciudadSeleccionada;

    private IFragmentCiudadListener onClick;

    public interface IFragmentCiudadListener{
        public void guardarCiudadClick(Ciudad ciudad);
    }

    public FragmentCiudad(IFragmentCiudadListener onClick){
        this.onClick = onClick;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contenedor = inflater.inflate(R.layout.fragment_seleccionar_ciudad,container,false);


        btnGuardar = contenedor.findViewById(R.id.btnGuardar);
        btnBuscar = contenedor.findViewById(R.id.btnBuscar);
        sp_ciudades = contenedor.findViewById(R.id.spCiudades);
        txt_ciudad = contenedor.findViewById(R.id.txt_ciudad);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCiudad();
            }
        });
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarCiudad();
            }
        });

        sp_ciudades.setOnItemSelectedListener(this);

        cargarCiudad();

        return contenedor;
    }

    private void cargarCiudad(){
        ConfiguracionBusiness cBO = Context.getConfiguracionBusiness();
        String username = Context.getUsuarioBusiness().getCurrentUser().getUsuario();

        Configuracion conf = cBO.getConfiguracion(username);
        if(conf.getCiudad()!=null){
            Ciudad c = conf.getCiudad();
            txt_ciudad.setText(c.getName()+","+c.getCountry());
            ciudadSeleccionada = c;
        }
    }

    private void buscarCiudad(){
        String ciudad = txt_ciudad.getText().toString();
        new BuscarCiudadAsyncTask().execute(ciudad);
    }

    private class BuscarCiudadAsyncTask extends AsyncTask<String,Void,List<Ciudad>>{
        @Override
        protected List<Ciudad> doInBackground(String... strings) {
            ciudadList = Context.getCiudadBusiness().getCiudadLike(
                    getActivity().getApplicationContext(),strings[0]);
            return ciudadList;
        }

        @Override
        protected void onPostExecute(List<Ciudad> ciudads) {
            List<String> list;
            if(ciudads == null)
                return;
            if(ciudads.size() > 1){
                list = new ArrayList<String>();
                for(Ciudad c:ciudads)
                    list.add(c.getName()+","+c.getCountry());

                ArrayAdapter<String> comboAdapter = new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.simple_spinner_item,
                        list);

                sp_ciudades.setAdapter(comboAdapter);
                sp_ciudades.setVisibility(View.VISIBLE);
            }else{
                sp_ciudades.setVisibility(View.GONE);
                if(ciudads.size() == 1){
                    Ciudad c = ciudads.get(0);
                    txt_ciudad.setText(c.getName()+","+c.getCountry());
                    ciudadSeleccionada = c;
                }else
                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "No hay ciudades con ese nombre",
                            Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void guardarCiudad() {
        if(ciudadSeleccionada != null)
            onClick.guardarCiudadClick(ciudadSeleccionada);
        else
            Toast.makeText(
                    getActivity().getApplicationContext(),
                    "Se debe seleccionar una ciudad para guardarla",
                    Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Ciudad c = ciudadList.get(position);
        txt_ciudad.setText(c.getName()+","+c.getCountry());
        ciudadSeleccionada = c;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
