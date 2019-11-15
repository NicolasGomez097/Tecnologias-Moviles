package org.moviles.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import org.moviles.Constants;
import org.moviles.Context;
import org.moviles.Util;
import org.moviles.activity.Fragments.FragmentCiudad;
import org.moviles.activity.Fragments.FragmentClimaExtendido;
import org.moviles.activity.Fragments.FragmentConfiguracion;
import org.moviles.activity.Fragments.FragmentEditarUsuario;
import org.moviles.activity.Fragments.FragmentHome;
import org.moviles.activity.Fragments.FragmentMap;
import org.moviles.business.ConfiguracionBusiness;
import org.moviles.model.Ciudad;
import org.moviles.model.Configuracion;
import org.moviles.model.Usuario;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        FragmentEditarUsuario.IFragmentEditarUsuarioListener,
        FragmentConfiguracion.IFragmentConfiguracionListener,
        FragmentCiudad.IFragmentCiudadListener{


    private DrawerLayout drawer;
    private TextView nombreUsuarioMenu;
    private TextView emailUsuarioMenu;
    private CircleImageView avatar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        setTitle("Menu");

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        nombreUsuarioMenu = headerView.findViewById(R.id.nombreUsuarioMenu);
        emailUsuarioMenu = headerView.findViewById(R.id.emailUsuarioMenu);
        avatar = headerView.findViewById(R.id.avatar);

        cargarUsuario();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        navigationView.setNavigationItemSelectedListener(this);

        FragmentHome  fh = new FragmentHome();
        loadFragment(fh);

        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment f = fragmentManager.findFragmentById(R.id.fragment_container);
            if(!(f instanceof FragmentHome)) {
                cargarHome();
                return;
            }

            if(!Context.getUsuarioBusiness().isMantenerSesion())
                Context.getUsuarioBusiness().setCurrentUser(null);
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if(!Context.getUsuarioBusiness().isMantenerSesion())
            Context.getUsuarioBusiness().setCurrentUser(null);
        super.onDestroy();
    }

    private void cargarUsuario(){
        Usuario user = Context.getUsuarioBusiness().getCurrentUser();
        nombreUsuarioMenu.setText(user.getUsuario());
        emailUsuarioMenu.setText(user.getEmail());

        File img = new File(Context.getDataDir(),user.getUsuario()+"/"+ Constants.USER_AVATAR);
        Bitmap bmp = Util.getImage(img);
        avatar.setImageBitmap(bmp);
    }

    private void cerrarSesion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.cerrarSesionMensaje)
                .setPositiveButton(R.string.ACEPTAR, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(!Context.getUsuarioBusiness().setCurrentUser(null))
                            return;
                        Intent i = new Intent(MenuActivity.this,LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                })
                .setNegativeButton(R.string.CANCELAR, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        builder.create();
        builder.show();
    }

    private void cargarHome(){
        FragmentHome  fhome = new FragmentHome();
        loadFragment(fhome);
    }

    private void cargarEditar(){
        FragmentEditarUsuario  fEdit = new FragmentEditarUsuario(this);
        loadFragment(fEdit);
    }

    private void cargarDetalle(){
        FragmentClimaExtendido fce = new FragmentClimaExtendido();
        loadFragment(fce);
    }

    private void cargarMapa(){
        FragmentMap fmap = new FragmentMap();
        loadFragment(fmap);
    }

    private void cargarCiudad(){
        FragmentCiudad floc = new FragmentCiudad(this);
        loadFragment(floc);
    }

    private void cargarConfig(){
        FragmentConfiguracion fconf = new FragmentConfiguracion(this);
        loadFragment(fconf);
    }

    private void loadFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container,fragment);
        ft.commit();
    }

    private void mandarEmail() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"ngomez057@alumnos.iua.edu.ar"});
        i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.consulta));
        i.putExtra(Intent.EXTRA_TEXT   , getString(R.string.ingreseConsulta));
        try {
            startActivity(Intent.createChooser(i, getString(R.string.enviando)));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), getString(R.string.noClienteEmail), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_logout:
                cerrarSesion();
                break;
            case R.id.nav_map:
                cargarMapa();
                break;
            case R.id.nav_home:
                cargarHome();
                break;
            case R.id.nav_extend:
                cargarDetalle();
                break;
            case R.id.nav_ciudad:
                cargarCiudad();
                break;
            case R.id.nav_email:
                mandarEmail();
                break;
            case R.id.nav_config:
                cargarConfig();
                break;
            case R.id.nav_edit_profile:
                cargarEditar();


        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void cerrarFramgemntEditarUsuario() {
        cargarHome();
    }

    @Override
    public void guardarConfiguracionClick(Configuracion conf) {
        ConfiguracionBusiness cBO = Context.getConfiguracionBusiness();
        String username = Context.getUsuarioBusiness().getCurrentUser().getUsuario();
        boolean valid = cBO.save(conf,username);
        cargarHome();
        String msg;
        if(valid)
            msg = getString(R.string.confGuardada);
        else
            msg = getString(R.string.confErroGuardado);

        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void actualizarUsuario() {
        cargarUsuario();
    }

    @Override
    public void guardarCiudadClick(Ciudad ciudad) {
        ConfiguracionBusiness cBO = Context.getConfiguracionBusiness();
        String username = Context.getUsuarioBusiness().getCurrentUser().getUsuario();

        Configuracion conf = cBO.getConfiguracion(username);
        conf.setCiudad(ciudad);
        guardarConfiguracionClick(conf);
    }
}
