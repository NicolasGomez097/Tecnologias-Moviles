package org.moviles.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.util.Date;

@Entity(tableName = "clima")
public class Clima {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Integer clave;

    @NonNull
    private String description;

    private Double temperatura;

    private Double tempMaxima;

    private Double tempMinima;

    private Long dt;

    @NonNull
    private Integer humedad;

    @NonNull
    private String condicion;

    @NonNull
    private String viento;

    @NonNull
    public Integer getClave() {
        return clave;
    }

    public void setClave(@NonNull Integer clave) {
        this.clave = clave;
    }

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String descripcion) {
        this.description = descripcion;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public Integer getHumedad() {
        return humedad;
    }

    public void setHumedad(Integer humedad) {
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
}
