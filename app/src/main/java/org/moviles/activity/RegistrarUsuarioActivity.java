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
import org.moviles.Util;

import java.io.File;
import java.io.FileWriter;

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
        if(!Util.isAlphaNumeric(partes[0]) || !Util.isAlphaNumeric(partes[1])){
            Toast.makeText(getApplicationContext(),R.string.contrasenaNoValido,Toast.LENGTH_LONG).show();
            return;
        }

        File dir = new File(getApplicationContext().getDataDir(),nuevoUsuario.getText().toString());
        if(!dir.exists()) {
            dir.mkdir();
            try {
                File lista = new File(getApplicationContext().getDataDir(),"ListaUsuarios.txt");
                FileWriter fr = new FileWriter(lista,true);
                fr.append(nuevoUsuario.getText().toString()+"\n");
                fr.close();
            }catch (Exception e){
                e.printStackTrace();
            }

        }else{
            Toast.makeText(v.getContext(),"El usuario ya existe", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(dir,"datos.txt");

        try{
            FileWriter fw = new FileWriter(file);
            JSONObject json = new JSONObject();
            json.put("usuario",nuevoUsuario.getText());
            json.put("contrasena",nuevaPassword.getText());
            json.put("email",nuevoCorreo.getText());
            fw.write(json.toString());
            fw.close();

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(v.getContext(),"No se creo correctamente", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(v.getContext(),"Se creo correctamente", Toast.LENGTH_SHORT).show();

        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
        finish();
    }
}
