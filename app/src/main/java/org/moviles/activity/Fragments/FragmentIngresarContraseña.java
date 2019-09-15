package org.moviles.activity.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;
import org.moviles.activity.Adapters.ListUserAdapter;
import org.moviles.activity.Interfaces.ListaUsuarioRecyclerViewOnItemClickListener;
import org.moviles.activity.R;

import java.util.List;

public class FragmentIngresarContraseña extends Fragment{

    private String user;
    private ListaUsuarioRecyclerViewOnItemClickListener onClick;
    private EditText inputPassword;
    private CheckBox mantenerSesion;
    private Button btnIngresar;

    public FragmentIngresarContraseña(ListaUsuarioRecyclerViewOnItemClickListener onClick, String user){
        this.onClick = onClick;
        this.user = user;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contenedor = inflater.inflate(R.layout.fragment_ingresar_contrasena, container, false);

        inputPassword = contenedor.findViewById(R.id.ingresoContrasena);
        btnIngresar = contenedor.findViewById(R.id.btnIngresar);
        mantenerSesion = contenedor.findViewById(R.id.mantenerSesion);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClickIngresar(user,inputPassword.getText().toString(),mantenerSesion.isChecked());
            }
        });

        return contenedor;
    }


}
