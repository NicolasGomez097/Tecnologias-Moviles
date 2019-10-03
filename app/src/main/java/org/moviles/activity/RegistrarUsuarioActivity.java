package org.moviles.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;
import org.moviles.Context;
import org.moviles.Util;
import org.moviles.business.UsuarioBusiness;
import org.moviles.model.Usuario;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class RegistrarUsuarioActivity extends AppCompatActivity {

    private EditText nuevoUsuario;
    private EditText nuevaPassword;
    private EditText nuevoCorreo;
    private Button btnAgregar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);
        setTitle("Crear usuario");

        nuevoUsuario = findViewById(R.id.nuevoUsuario);
        nuevaPassword = findViewById(R.id.nuevaPassword);
        nuevoCorreo = findViewById(R.id.nuevoCorreo);
        btnAgregar = findViewById(R.id.btnAgregarUsuario);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario(v);
            }
        });
    }

    private void registrarUsuario(View v){

        boolean valid;

        if(nuevoUsuario.getText().length() < 5){
            Toast.makeText(getApplicationContext(),R.string.nombreMenor5Letras,Toast.LENGTH_LONG).show();
            return;
        }
        if(nuevoUsuario.getText().length() > 15){
            Toast.makeText(getApplicationContext(),R.string.nombreMayor15Letras,Toast.LENGTH_LONG).show();
            return;
        }
        if(!Util.isAlphaNumeric(nuevoUsuario.getText().toString())){
            Toast.makeText(getApplicationContext(),R.string.nombreNoValido,Toast.LENGTH_LONG).show();
            return;
        }

        if(nuevaPassword.getText().length() < 6){
            Toast.makeText(getApplicationContext(),R.string.contrasenaMenor6Letras,Toast.LENGTH_LONG).show();
            return;
        }
        if(nuevaPassword.getText().length() > 20){
            Toast.makeText(getApplicationContext(),R.string.contrasenaMayor20Letras,Toast.LENGTH_LONG).show();
            return;
        }
        if(!Util.isAlphaNumeric(nuevaPassword.getText().toString())){
            Toast.makeText(getApplicationContext(),R.string.contrasenaNoValido,Toast.LENGTH_LONG).show();
            return;
        }

        String[] partes = nuevoCorreo.getText().toString().split("@");
        if(partes.length != 2){
            Toast.makeText(getApplicationContext(),R.string.emailIncorrecto,Toast.LENGTH_LONG).show();
            return;
        }
        if(!Util.isAlphaNumeric(partes[0].replaceAll(".",""))
                || !Util.isAlphaNumeric(partes[1].replaceAll(".",""))){
            Toast.makeText(getApplicationContext(),R.string.correoNoValido,Toast.LENGTH_LONG).show();
            return;
        }

        UsuarioBusiness userBO = Context.getUsuarioBusiness();
        Usuario u = userBO.getUsuario(nuevoUsuario.getText().toString());
        if(u == null) {
            u = new Usuario();

            u.setUsuario(nuevoUsuario.getText().toString());
            u.setPassword(nuevaPassword.getText().toString());
            u.setEmail(nuevoCorreo.getText().toString());

            valid = userBO.save(u);


        }else{
            Toast.makeText(v.getContext(),"El usuario ya existe", Toast.LENGTH_SHORT).show();
            return;
        }

        if(valid)
            Toast.makeText(v.getContext(),"Se creo correctamente", Toast.LENGTH_SHORT).show();

        else
            Toast.makeText(v.getContext(),"No se creo correctamente", Toast.LENGTH_SHORT).show();


        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
        finish();
    }
}
