package org.moviles.activity.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.moviles.Constants;
import org.moviles.Context;
import org.moviles.Util;
import org.moviles.activity.R;
import org.moviles.business.ConfiguracionBusiness;
import org.moviles.model.Ciudad;
import org.moviles.model.Configuracion;

import java.util.List;

public class FragmentConfiguracion extends Fragment {

    private RadioButton radioMetrica;
    private RadioButton radioImperial;
    private RadioButton radioTempC;
    private RadioButton radioTempF;
    private Switch switch_notificaicon;
    private LinearLayout contenedorNotificacion;
    private CheckBox ckLunes;
    private CheckBox ckMartes;
    private CheckBox ckMiercoles;
    private CheckBox ckJueves;
    private CheckBox ckViernes;
    private CheckBox ckSabado;
    private CheckBox ckDomingo;
    private TimePicker hora;
    private Button btnGuardar;

    private IFragmentConfiguracionListener onClick;


    public interface IFragmentConfiguracionListener{
        public void guardarConfiguracionClick(Configuracion conf);
    }

    public FragmentConfiguracion(IFragmentConfiguracionListener onClick){
        this.onClick = onClick;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contenedor = inflater.inflate(R.layout.fragment_config,container,false);

        radioMetrica = contenedor.findViewById(R.id.radioMetrica);
        radioImperial = contenedor.findViewById(R.id.radioImperial);
        radioTempC = contenedor.findViewById(R.id.radioTempC);
        radioTempF = contenedor.findViewById(R.id.radioTempF);
        switch_notificaicon = contenedor.findViewById(R.id.switch_notificaicon);
        contenedorNotificacion = contenedor.findViewById(R.id.contenedorNotificacion);

        ckLunes = contenedor.findViewById(R.id.ckLunes);
        ckMartes = contenedor.findViewById(R.id.ckMartes);
        ckMiercoles = contenedor.findViewById(R.id.ckMiercoles);
        ckJueves = contenedor.findViewById(R.id.ckJueves);
        ckViernes = contenedor.findViewById(R.id.ckViernes);
        ckSabado = contenedor.findViewById(R.id.ckSabado);
        ckDomingo = contenedor.findViewById(R.id.ckDomingo);

        hora = contenedor.findViewById(R.id.horaNotificacion);
        btnGuardar = contenedor.findViewById(R.id.btnGuardar);



        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarConfiguracion();
            }
        });

        switch_notificaicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarEstadoContenedorNotificacion();
            }
        });

        cargarConfiguracion();

        return contenedor;
    }

    private void cargarConfiguracion(){
        ConfiguracionBusiness cBO = Context.getConfiguracionBusiness();
        String username = Context.getUsuarioBusiness().getCurrentUser().getUsuario();

        Configuracion conf = cBO.getConfiguracion(username);

        if(conf.getUnidadTemp().equals(Constants.UNIDAD_C))
            radioTempC.setChecked(true);
        else
            radioTempF.setChecked(true);

        if(conf.getUnidad().equals(Constants.UNIDAD_METRICA))
            radioMetrica.setChecked(true);
        else
            radioImperial.setChecked(true);

        switch_notificaicon.setChecked(conf.isNotificaciones());

        if(conf.isNotificaciones()){
            String[] aux = conf.getDias().split(",");

            for(String dia:aux){
                if(dia.equals("Lun"))
                    ckLunes.setChecked(true);
                if(dia.equals("Mar"))
                    ckMartes.setChecked(true);
                if(dia.equals("Mie"))
                    ckMiercoles.setChecked(true);
                if(dia.equals("Jue"))
                    ckJueves.setChecked(true);
                if(dia.equals("Vie"))
                    ckViernes.setChecked(true);
                if(dia.equals("Sab"))
                    ckSabado.setChecked(true);
                if(dia.equals("Dom"))
                    ckDomingo.setChecked(true);
            }

            aux = conf.getHora().split(":");
            hora.setHour(Integer.parseInt(aux[0]));
            hora.setMinute(Integer.parseInt(aux[1]));
        }else{
            contenedorNotificacion.setVisibility(View.GONE);
        }

    }

    private void cambiarEstadoContenedorNotificacion(){
        if(switch_notificaicon.isChecked()){
            contenedorNotificacion.setVisibility(View.VISIBLE);
            contenedorNotificacion.setScaleY(0);

            contenedorNotificacion.animate().scaleY(1);
            contenedorNotificacion.animate().setDuration(250);
        }else{
            contenedorNotificacion.setScaleY(1);

            contenedorNotificacion.animate().scaleY(0);
            contenedorNotificacion.animate().setDuration(250);

            contenedorNotificacion.setVisibility(View.GONE);
        }
    }

    private void guardarConfiguracion() {
        Configuracion conf = new Configuracion();

        if (radioTempC.isChecked())
            conf.setUnidadTemp(Constants.UNIDAD_C);
        else
            conf.setUnidadTemp(Constants.UNIDAD_F);

        if (radioMetrica.isChecked())
            conf.setUnidad(Constants.UNIDAD_METRICA);
        else
            conf.setUnidad(Constants.UNIDAD_IMPERIAL);

        conf.setNotificaciones(switch_notificaicon.isChecked());

        if (switch_notificaicon.isChecked()) {
            String aux = "";

            if (ckLunes.isChecked())
                aux += "Lun,";
            if (ckMartes.isChecked())
                aux += "Mar,";
            if (ckMiercoles.isChecked())
                aux += "Mie,";
            if (ckJueves.isChecked())
                aux += "Jue,";
            if (ckViernes.isChecked())
                aux += "Vie,";
            if (ckSabado.isChecked())
                aux += "Sab,";
            if (ckDomingo.isChecked())
                aux += "Dom";

            if (aux.endsWith(","))
                aux = aux.substring(0, aux.length() - 1);

            conf.setDias(aux);

            conf.setHora(hora.getHour() + ":" + hora.getMinute());
        }

        onClick.guardarConfiguracionClick(conf);
    }
}
