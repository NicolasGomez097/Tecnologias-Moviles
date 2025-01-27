package org.moviles.activity.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.moviles.utils.Util;
import org.moviles.activity.R;
import org.moviles.model.Ciudad;
import org.moviles.model.Clima;

import java.util.List;

public class ClimaAdapter extends RecyclerView.Adapter<ClimaAdapter.ClimaViewHolder>{

    private List<Clima> climaList;
    private Context contexto;
    private String ciudad;

    public ClimaAdapter(List<Clima> climas) {
        climaList = climas;
    }

    @Override
    public ClimaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clima_dia, parent, false);
        contexto = parent.getContext();

        return new ClimaViewHolder(row);
    }

    @Override
    public void onBindViewHolder(ClimaViewHolder holder, int position) {
        if(ciudad == null){
            Ciudad aux = org.moviles.Context.getConfiguracionBusiness().getConfiguracion().getCiudad();
            ciudad = aux.getName()+","+aux.getCountry();
        }

        Clima aux = climaList.get(position);
        holder.getFecha().setText(
                aux.getDia()+" de "+
                aux.getMesLetras() + " de " +
                aux.getAnio()+ " " +
                aux.getHora() + ":" + aux.getMinuto() +
                "," + ciudad
        );
        holder.getDiaCondicion().setText(aux.getCondicion());
        holder.getDiaHumedad().setText(aux.getHumedad().toString() + "%");
        holder.getDiaTempMax().setText(aux.getTempMaxima());
        holder.getDiaTempMin().setText(aux.getTempMinima());
        holder.getDiaViento().setText(aux.getViento());
        holder.getDiaDescripcion().setText(aux.getDescripcion());

        holder.getImg().setImageResource(Util.getImagenDeCondicionClima(aux.getCondicion()));
    }

    @Override
    public int getItemCount() {
        return climaList.size();
    }

    public class ClimaViewHolder extends RecyclerView.ViewHolder {

        private TextView fecha;
        private TextView diaCondicion;
        private TextView diaTempMax;
        private TextView diaTempMin;
        private TextView diaViento;
        private TextView diaHumedad;
        private TextView diaDescripcion;
        private ImageView img;


        public ClimaViewHolder(View itemView) {
            super(itemView);

            fecha = itemView.findViewById(R.id.fecha);
            diaCondicion = itemView.findViewById(R.id.diaCondicion);
            diaTempMax = itemView.findViewById(R.id.diaTempMax);
            diaTempMin = itemView.findViewById(R.id.diaTempMin);
            diaViento = itemView.findViewById(R.id.diaViento);
            diaHumedad = itemView.findViewById(R.id.diaHumedad);
            diaDescripcion = itemView.findViewById(R.id.diaDesc);
            img = itemView.findViewById(R.id.diaImg);
        }

        public TextView getFecha() {
            return fecha;
        }

        public TextView getDiaCondicion() {
            return diaCondicion;
        }

        public TextView getDiaViento() {
            return diaViento;
        }

        public TextView getDiaHumedad() {
            return diaHumedad;
        }

        public TextView getDiaDescripcion() {
            return diaDescripcion;
        }

        public TextView getDiaTempMax() {
            return diaTempMax;
        }

        public TextView getDiaTempMin() {
            return diaTempMin;
        }

        public ImageView getImg() {
            return img;
        }
    }
}

