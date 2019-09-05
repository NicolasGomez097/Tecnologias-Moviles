package org.moviles.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText userInput;
    private EditText passInput;
    private Button btnIngresar;
    private Button btnCrearUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userInput = findViewById(R.id.userInput);
        passInput = findViewById(R.id.passInput);
        btnCrearUsuario = findViewById(R.id.btnCrearUsuario);
        btnIngresar = findViewById(R.id.btnIngresar);

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

    }

    private void ingresar(){
        Toast.makeText(
                getApplicationContext(),
                getString(R.string.errorLogin),
                Toast.LENGTH_SHORT)
                    .show();
    }

    private void crearUsuario(){
        Intent i = new Intent(LoginActivity.this, RegistrarUsuarioActivity.class);
        startActivity(i);
    }
}
