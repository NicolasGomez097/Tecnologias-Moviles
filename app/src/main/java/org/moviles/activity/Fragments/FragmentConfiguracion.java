package org.moviles.activity.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.moviles.Constants;
import org.moviles.Context;
import org.moviles.activity.R;
import org.moviles.business.ConfiguracionBusiness;
import org.moviles.config.AlarmService;
import org.moviles.config.NotificationService;
import org.moviles.model.Configuracion;

import java.util.Calendar;

public class FragmentConfiguracion extends Fragment {

    private RadioButton radioMetrica;
    private RadioButton radioImperial;
    private Switch switch_notificaicon;
    private LinearLayout contenedorNotificacion;
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
        switch_notificaicon = contenedor.findViewById(R.id.switch_notificaicon);
        contenedorNotificacion = contenedor.findViewById(R.id.contenedorNotificacion);

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
        Configuracion conf = cBO.getConfiguracion();

        if(conf.getUnidad().equals(Constants.UNIDAD_METRICA))
            radioMetrica.setChecked(true);
        else
            radioImperial.setChecked(true);

        switch_notificaicon.setChecked(conf.isNotificaciones());

        if(conf.isNotificaciones()){
            String[] aux = conf.getHora().split(":");
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
        Configuracion conf = Context.getConfiguracionBusiness().getConfiguracion();

        if (radioMetrica.isChecked())
            conf.setUnidad(Constants.UNIDAD_METRICA);
        else
            conf.setUnidad(Constants.UNIDAD_IMPERIAL);

        conf.setNotificaciones(switch_notificaicon.isChecked());

        if (switch_notificaicon.isChecked()) {
            Intent intent = new Intent(getContext(), NotificationService.class);
            AlarmService alarmService = new AlarmService(getContext(),intent);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            if(calendar.before(Calendar.getInstance()))
                calendar.add(Calendar.DATE,1);

            calendar.set(Calendar.HOUR_OF_DAY,hora.getHour());
            calendar.set(Calendar.MINUTE,hora.getMinute());
            calendar.set(Calendar.SECOND,0);
            alarmService.SetRepitingDayAlarm(calendar);

            conf.setHora(hora.getHour()+":"+hora.getMinute());
        }else{
            Intent intent = new Intent(getContext(), NotificationService.class);
            AlarmService alarmService = new AlarmService(getContext(),intent);
            alarmService.cancelAlarm();
        }

        onClick.guardarConfiguracionClick(conf);
    }
}
