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

import org.moviles.activity.Adapters.ListUserAdapter;
import org.moviles.activity.R;
import org.moviles.model.Usuario;

import java.util.List;

public class FragmentListaUsuarios extends Fragment{

    private List<Usuario> usersList;
    private IFragmentListaUsuariosListener onClick;

    public FragmentListaUsuarios(IFragmentListaUsuariosListener onClick, List<Usuario> lista){
        this.onClick = onClick;
        this.usersList = lista;
    }

    public interface IFragmentListaUsuariosListener {
        public void onClickItem(int position);
        public void onClickDelete(int position);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contenedor = inflater.inflate(R.layout.fragment_lista_usuarios, container, false);

        RecyclerView rv = contenedor.findViewById(R.id.ListaUsuarios);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new ListUserAdapter(usersList, onClick));

        return contenedor;
    }


}
