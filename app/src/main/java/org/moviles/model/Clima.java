package org.moviles.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "clima")
public class Clima {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Integer id;

    @NonNull
    private Integer dia;

    @NonNull
    private String mes;

    @NonNull
    private Integer anio;

    @NonNull
    private String fechaNumeros;

    @NonNull
    private String descripcion;

    private Double temperatura;

    private Double tempMaxima;

    private Double tempMinima;

    private String hora;

    private String minuto;

    @NonNull
    private Double humedad;

    @NonNull
    private String condicion;

    @NonNull
    private String viento;

    @NonNull
    private Boolean esExtendido;

    public Clima(){
        esExtendido = true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public Integer getDia() {
        return dia;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public Double getHumedad() {
        return humedad;
    }

    public void setHumedad(Double humedad) {
        this.humedad = humedad;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public String getViento() {
        return viento;
    }

    public void setViento(String viento) {
        this.viento = viento;
    }

    public Double getTempMaxima() {
        return tempMaxima;
    }

    public void setTempMaxima(Double tempMaxima) {
        this.tempMaxima = tempMaxima;
    }

    public Double getTempMinima() {
        return tempMinima;
    }

    public void setTempMinima(Double tempMinima) {
        this.tempMinima = tempMinima;
    }

    @NonNull
    public String getFechaNumeros() {
        return fechaNumeros;
    }

    public void setFechaNumeros(@NonNull String fechaNumeros) {
        this.fechaNumeros = fechaNumeros;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(Integer hora) {
        if(hora < 10)
            this.hora = "0"+hora;
        else{
            this.hora = hora.toString();
        }
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMinuto() {
        return minuto;
    }

    public void setMinuto(Integer minuto) {
        if(minuto < 10)
            this.minuto = "0"+minuto;
        else{
            this.minuto = minuto.toString();
        }
    }

    public void setMinuto(String minuto) {
        this.minuto = minuto;
    }

    @NonNull
    public Boolean getEsExtendido() {
        return esExtendido;
    }

    public void setEsExtendido(@NonNull Boolean esExtendido) {
        this.esExtendido = esExtendido;
    }
}
