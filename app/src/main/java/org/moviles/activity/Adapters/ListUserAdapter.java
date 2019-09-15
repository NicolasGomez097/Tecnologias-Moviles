package org.moviles.activity.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;
import org.moviles.activity.Interfaces.ListaUsuarioRecyclerViewOnItemClickListener;
import org.moviles.activity.LoginActivity;
import org.moviles.activity.LoginActivityBackup;
import org.moviles.activity.R;
import org.moviles.activity.RegistrarUsuarioActivity;

import java.util.List;

public class ListUserAdapter extends RecyclerView.Adapter<ListUserAdapter.ListUsuarioViewHolder> {

    private List<JSONObject> lista;
    private ListaUsuarioRecyclerViewOnItemClickListener onClick;

    public ListUserAdapter(List<JSONObject> usersList,@NonNull ListaUsuarioRecyclerViewOnItemClickListener onClick) {
        this.lista = usersList;
        this.onClick = onClick;
    }

    @Override
    public ListUserAdapter.ListUsuarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item_usuario, parent, false);
        return new ListUserAdapter.ListUsuarioViewHolder(row);
    }

    @Override
    public void onBindViewHolder(ListUserAdapter.ListUsuarioViewHolder holder, int position) {
        JSONObject user = lista.get(position);

        try{
            if(position == 0)
                holder.getAvatar().setImageResource(R.drawable.add_user);

            if(position == 0)
                holder.getBtnBorrar().setVisibility(View.GONE);

            holder.getNombreUsuario().setText(user.get("usuario").toString());
            holder.getUsuarioEmail().setText(user.get("email").toString());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }


    public class ListUsuarioViewHolder extends RecyclerView.ViewHolder {

        private ImageView avatar;
        private TextView nombreUsuario;
        private TextView usuarioEmail;
        private Button btnBorrar;


        public ListUsuarioViewHolder(View itemView) {
            super(itemView);

            avatar = itemView.findViewById(R.id.avatar);
            nombreUsuario = itemView.findViewById(R.id.nombreUsuario);
            usuarioEmail = itemView.findViewById(R.id.correoUsuario);
            btnBorrar = itemView.findViewById(R.id.BtnEliminarUsuario);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.onClickItem(getAdapterPosition());
                }
            });

            btnBorrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.onClickDelete(getAdapterPosition());
                }
            });

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

        public Button getBtnBorrar() {
            return btnBorrar;
        }

        public void setBtnBorrar(Button btnBorrar) {
            this.btnBorrar = btnBorrar;
        }
    }
}
