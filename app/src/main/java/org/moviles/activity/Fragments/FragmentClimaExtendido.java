package org.moviles.activity.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.moviles.activity.Adapters.ClimaAdapter;
import org.moviles.activity.R;
import org.moviles.model.Clima;

import java.util.ArrayList;
import java.util.List;

public class FragmentClimaExtendido extends Fragment {

    private List<Clima> climaList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clima_extendido,container,false);

        cargarLista();

        RecyclerView recyclerView = view.findViewById(R.id.contenedorClimaPorDia);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ClimaAdapter(climaList));

        return view;
    }

    private void cargarLista(){
        climaList = new ArrayList<Clima>();

        Clima aux = new Clima();
        aux.setDia("Jueves");
        aux.setDiaNumero(5);
        aux.setMes("Septiembre");
        aux.setAnio(2019);
        aux.setCondicion(getString(R.string.tormenta_electrica_granizo));
        aux.setHumedad(50);
        aux.setTempMaxima(20.2);
        aux.setTempMinima(5.2);
        aux.setViento("NE 15Km/h ");
        aux.setDescripcion("Corre a guardar el auto que cae piedra");

        climaList.add(aux);

        aux = new Clima();
        aux.setDia("Vienes");
        aux.setDiaNumero(6);
        aux.setMes("Septiembre");
        aux.setAnio(2019);
        aux.setCondicion(getString(R.string.tormenta_electrica));
        aux.setHumedad(100);
        aux.setTempMaxima(10.7);
        aux.setTempMinima(-5.7);
        aux.setViento("E 30Km/h ");
        aux.setDescripcion("te acordaste de guardar el auto?");

        climaList.add(aux);

        aux = new Clima();
        aux.setDia("Sabado");
        aux.setDiaNumero(7);
        aux.setMes("Septiembre");
        aux.setAnio(2019);
        aux.setCondicion(getString(R.string.nublado));
        aux.setHumedad(100);
        aux.setTempMaxima(15.7);
        aux.setTempMinima(2.0);
        aux.setViento("N 5Km/h ");
        aux.setDescripcion("El seguro te cubrió?");

        climaList.add(aux);

        aux = new Clima();
        aux.setDia("Domingo");
        aux.setDiaNumero(8);
        aux.setMes("Septiembre");
        aux.setAnio(2019);
        aux.setCondicion(getString(R.string.parcialmente_nublado));
        aux.setHumedad(100);
        aux.setTempMaxima(20.1);
        aux.setTempMinima(6.9);
        aux.setViento("SE 2Km/h ");
        aux.setDescripcion("Esta lindo el día para el asado.");

        climaList.add(aux);
    }
}
