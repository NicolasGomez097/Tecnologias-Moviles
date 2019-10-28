package org.moviles.model;

import org.moviles.Constants;

public class Configuracion {
    private String unidad;
    private String unidadTemp;
    private boolean notificaciones;
    private String dias;
    private String hora;

    public Configuracion(){
        unidadTemp = Constants.UNIDAD_C;
        unidad = Constants.UNIDAD_METRICA;
        notificaciones = false;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public boolean isNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(boolean notificaciones) {
        this.notificaciones = notificaciones;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getUnidadTemp() {
        return unidadTemp;
    }

    public void setUnidadTemp(String unidadTemp) {
        this.unidadTemp = unidadTemp;
    }
}
