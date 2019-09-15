package org.moviles.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class MenuActivity extends AppCompatActivity {

    private Button btnCerrarUsuario;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnCerrarUsuario = findViewById(R.id.btnCerrarSesion);
        btnCerrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion();
            }
        });

        setTitle("Menu");
    }

    private void cerrarSesion(){
        File fl = new File(getApplicationContext().getDataDir(),"loggedSession.txt");
        fl.delete();
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
        finish();
    }
}
