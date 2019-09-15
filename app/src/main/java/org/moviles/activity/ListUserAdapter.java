package org.moviles.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;
import org.moviles.data.Clima;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ListUserAdapter extends RecyclerView.Adapter<ListUserAdapter.ListUsuarioViewHolder> {

    private List<JSONObject> usersList;

    public ListUserAdapter() {
        usersList = new ArrayList<JSONObject>();

        JSONObject aux = new JSONObject();
        try{
            aux.put("usuario","Crear usuario");
        }catch (Exception e){

        }

        File file = new File(getApplicationContext().getFilesDir().toString()+"/"+userInput.getText().toString()+"/password.txt");
        String user = "";

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;

        while ((line = br.readLine()) != null) {
            user += line;
        }
        br.close();



        climaList.add(aux);
    }

    @Override
    public ClimaAdapter.ClimaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clima_dia, parent, false);
        return new ClimaAdapter.ClimaViewHolder(row);
    }

    @Override
    public void onBindViewHolder(ClimaAdapter.ClimaViewHolder holder, int position) {
        Clima aux = climaList.get(position);
        holder.getDia().setText(aux.getDia());
        holder.getFecha().setText(aux.getDiaNumero()+" de "+aux.getMes() + " de " + aux.getAnio());
        holder.getDiaCondicion().setText(aux.getCondicion());
        holder.getDiaHumedad().setText(aux.getHumedad().toString());
        holder.getDiaTemp().setText(aux.getTemperatura().toString());
        holder.getDiaViento().setText(aux.getViento());
        holder.getDiaDescripcion().setText(aux.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return climaList.size();
    }


    public class ListUsuarioViewHolder extends RecyclerView.ViewHolder {

        private ImageView avatar;
        private TextView nombreUsuario;
        private TextView usuarioEmail;


        public ListUsuarioViewHolder(View itemView) {
            super(itemView);

            avatar = itemView.findViewById(R.id.avatar);
            nombreUsuario = itemView.findViewById(R.id.nombreUsuario);
            usuarioEmail = itemView.findViewById(R.id.correoUsuario);

        }

        public ImageView getAvatar() {
            return avatar;
        }

        public void setAvatar(ImageView avatar) {
            this.avatar = avatar;
        }

        public TextView getNombreUsuario() {
            return nombreUsuario;
        }

        public void setNombreUsuario(TextView nombreUsuario) {
            this.nombreUsuario = nombreUsuario;
        }

        public TextView getUsuarioEmail() {
            return usuarioEmail;
        }

        public void setUsuarioEmail(TextView usuarioEmail) {
            this.usuarioEmail = usuarioEmail;
        }
    }
}
