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

import org.moviles.Context;
import org.moviles.activity.Adapters.ClimaAdapter;
import org.moviles.activity.R;
import org.moviles.model.Clima;

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
        climaList = Context.getClimaBusiness(getActivity().getApplication()).listaClima();
    }
}
