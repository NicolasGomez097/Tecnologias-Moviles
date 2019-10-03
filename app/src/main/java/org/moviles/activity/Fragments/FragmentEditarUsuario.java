package org.moviles.activity.Fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import org.moviles.activity.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentEditarUsuario extends Fragment {


    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2 ;
    private CircleImageView imgPerfil;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View editarUsuarioView = inflater.inflate(R.layout.fragment_editar_usuario, container, false);
        imgPerfil = editarUsuarioView.findViewById(R.id.profile_image);
        FloatingActionButton fabCamera = editarUsuarioView.findViewById(R.id.floatingButtonCamera);

        fabCamera.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             selectImage();
                                         }
                                     }
        );
        return editarUsuarioView;
    }

    private void selectImage(){
        final CharSequence[] items = {"Camara", "Galeria", "Cancelar"};

        AlertDialog.Builder adBuilder = new AlertDialog.Builder(getActivity());
        adBuilder.setTitle("Agregar Imagen");
        adBuilder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (items[i].equals("Camara")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);

                } else if (items[i].equals("Galeria")) {

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent, SELECT_FILE);

                } else if (items[i].equals("Cancelar")) {
                    dialogInterface.dismiss();
                }
            }
        });
        adBuilder.show();
    }
    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);

        if(resultCode== Activity.RESULT_OK){

            if(requestCode==REQUEST_CAMERA){

                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                imgPerfil.setImageBitmap(bmp);

            }else if(requestCode==SELECT_FILE){

                Uri selectedImageUri = data.getData();
                imgPerfil.setImageURI(selectedImageUri);

            }

        }
    }
}
