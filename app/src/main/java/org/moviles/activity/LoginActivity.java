package org.moviles.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;
import org.moviles.Util;
import org.moviles.activity.Fragments.FragmentIngresarContraseña;
import org.moviles.activity.Fragments.FragmentListaUsuarios;
import org.moviles.activity.Interfaces.ListaUsuarioRecyclerViewOnItemClickListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements ListaUsuarioRecyclerViewOnItemClickListener {

    private List<JSONObject> usersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        File loged = new File(getApplicationContext().getDataDir(),"loggedSession.txt");
        if(loged.exists()){
            Intent i = new Intent(this,MenuActivity.class);
            startActivity(i);
            finish();
            return;
        }

        cargarFragmento(false);
    }

    public void cargarFragmento(boolean useAnimation){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        cargarLista();

        FragmentListaUsuarios fragmentListaUsuarios = new FragmentListaUsuarios(this, usersList);

        if(useAnimation)
            fragmentTransaction.setCustomAnimations(
                    R.anim.entrar_por_izquierda,
                    R.anim.salir_por_derecha,
                    R.anim.entrar_por_derecha,
                    R.anim.salir_por_izquierda);

        fragmentTransaction.replace(R.id.FragmentListaUsuarios, fragmentListaUsuarios);

        fragmentTransaction.commit();
    }

    public void cargarFragmentoContraseña(int position){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        try {
            FragmentIngresarContraseña fragmentIngresarContraseña = new FragmentIngresarContraseña(this,
                    usersList.get(position).getString("usuario"));

            fragmentTransaction.setCustomAnimations(R.anim.entrar_por_derecha,R.anim.salir_por_izquierda,R.anim.entrar_por_izquierda,R.anim.salir_por_derecha);
            fragmentTransaction.replace(R.id.FragmentListaUsuarios, fragmentIngresarContraseña);

            fragmentTransaction.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void cargarLista(){
        usersList = new ArrayList<JSONObject>();

        JSONObject aux = new JSONObject();
        try {
            aux.put("usuario", getString(R.string.crearUSuario));
            aux.put("email", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        usersList.add(aux);

        File file = new File(getApplicationContext().getDataDir(), "/ListaUsuarios.txt");
        File userFile;
        BufferedReader brLista;
        BufferedReader brUsuario;
        String user;
        String line;

        try {
            if (!file.exists()) {
                FileWriter newFile = new FileWriter(file);
                newFile.append("");
                newFile.close();
            }

            brLista = new BufferedReader(new FileReader(file));

            while ((line = brLista.readLine()) != null) {
                userFile = new File(getApplicationContext().getDataDir() + "/" + line + "/datos.txt");

                user = "";
                brUsuario = new BufferedReader(new FileReader(userFile));
                while ((line = brUsuario.readLine()) != null) {
                    user += line;
                }

                aux = new JSONObject(user);
                usersList.add(aux);

                brUsuario.close();
            }
            brLista.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClickItem(int position) {
        Intent i;
        if(position == 0) {
            i = new Intent(this, RegistrarUsuarioActivity.class);
            startActivity(i);
            finish();

        }else {
            cargarFragmentoContraseña(position);
        }
    }

    @Override
    public void onClickDelete(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.eliminarUsuarioMensaje)
                .setPositiveButton(R.string.ACEPTAR, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        borrarUsuario(position);
                    }
                })
                .setNegativeButton(R.string.CANCELAR, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        builder.create();
        builder.show();
    }

    public void borrarUsuario(int position){
        File file = new File(getApplicationContext().getDataDir(), "/ListaUsuarios.txt");

        try {
            boolean valid = Util.deleteFileOnPath(getApplicationContext().getDataDir(),
                    usersList.get(position).getString("usuario"));

            if(!valid){
                Toast.makeText(getApplicationContext(),getString(R.string.errorEliminarUsuario),Toast.LENGTH_SHORT).show();
                return;
            }

            FileWriter lista = new FileWriter(file,false);
            usersList.remove(position);

            for(int i = 1; i < usersList.size();i++){
                lista.append(usersList.get(i).getString("usuario")+System.lineSeparator());
            }

            lista.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        cargarFragmento(false);
        Toast.makeText(getApplicationContext(),getString(R.string.usuarioEliminado),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickIngresar(String user, String password, boolean mantenerSesion){
        File file = new File(getApplicationContext().getDataDir(),user+"/datos.txt");
        String aux = "";
        String line;
        JSONObject json;

        try {
            BufferedReader bf = new BufferedReader(new FileReader(file));
            while((line = bf.readLine()) != null){
                aux += line;
            }

            json = new JSONObject(aux);

            if(json.getString("contrasena").equals(password)){
                Intent i = new Intent(this,MenuActivity.class);

                if(mantenerSesion){
                    File loged = new File(getApplicationContext().getDataDir(),"loggedSession.txt");
                    FileWriter fr = new FileWriter(loged,false);
                    fr.append(user);
                    fr.close();
                }

                startActivity(i);
                finish();
            }else{
                Toast.makeText(getApplicationContext(),
                        getString(R.string.ingresoNoValido),
                        Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment f = fragmentManager.findFragmentById(R.id.FragmentListaUsuarios);

        if(f instanceof FragmentListaUsuarios)
            super.onBackPressed();
        else
            cargarFragmento(true);
    }
}
