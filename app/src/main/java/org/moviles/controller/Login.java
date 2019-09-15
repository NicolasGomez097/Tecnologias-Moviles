package org.moviles.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Login extends AppCompatActivity {

    private EditText userInput;
    private EditText passInput;
    private Button btnIngresar;
    private Button btnCrearUsuario;
    private FloatingActionButton fl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userInput = findViewById(R.id.userInput);
        passInput = findViewById(R.id.passInput);
        btnCrearUsuario = findViewById(R.id.btnCrearUsuario);
        btnIngresar = findViewById(R.id.btnIngresar);
        fl = findViewById(R.id.Floating);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingresar();
            }
        });

        btnCrearUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearUsuario();
            }
        });

        fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingresar();
            }
        });
    }

    private void ingresar(){
        Toast t = Toast.makeText(
                getApplicationContext(),
                getString(R.string.errorLogin),
                Toast.LENGTH_SHORT);
        View v = t.getView();
        v.setBackgroundColor(getColor(android.R.color.background_dark));
        t.show();
    }

    private void crearUsuario(){

    }
}
