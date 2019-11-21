package org.moviles.activity.Fragments;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.moviles.activity.R;

public class FragmentIngresar extends Fragment{

    private String user;
    private TextView nombreUsuario;
    private IFragmentIngresarListener onClick;
    private EditText inputPassword;
    private CheckBox mantenerSesion;
    private Button btnIngresar;

    public interface IFragmentIngresarListener {
        public void onClickIngresar(String user,String password, boolean mantenerSesion);
    }

    public FragmentIngresar(IFragmentIngresarListener onClick, String user){
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
        nombreUsuario = contenedor.findViewById(R.id.nombreUsuario);

        nombreUsuario.setText(nombreUsuario.getText() + " " + user);

        inputPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER) {
                    ingresar();
                    return true;
                }
                return false;
            }
        });

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingresar();
            }
        });

        return contenedor;
    }

    private void ingresar(){
        onClick.onClickIngresar(user,inputPassword.getText().toString(),mantenerSesion.isChecked());
    }
}
