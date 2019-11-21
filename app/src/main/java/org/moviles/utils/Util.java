package org.moviles.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.core.content.ContextCompat;

import org.moviles.NotConnectedExeption;
import org.moviles.activity.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.regex.Pattern;

public class Util {

    private static Pattern p = Pattern.compile("^[a-zA-Z0-9_]*$");

    public static boolean deleteFileOnPath(File root,String file){
        File dir = new File(root,file);
        if (dir.isDirectory()){
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++)
            {
                new File(dir, children[i]).delete();
            }
        }

        return dir.delete();
    }

    public static boolean renameFile(File old,String newName){
        File newFile = new File(old.getParentFile(),newName);
        return old.renameTo(newFile);
    }

    public static boolean isAlphaNumeric(String s) {
        return p.matcher(s).find();
    }

    public static String readFile(File file){
        String out = "";
        String line;
        try {
            out = convertStreamToString(new FileInputStream(file));
            out = out.substring(0,out.length()-1);
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }

        return out;
    }

    public static boolean writeFile(File file, String text){
        try {
            if(!file.exists())
                file.getParentFile().mkdirs();

            FileWriter fl = new FileWriter(file);
            fl.append(text);
            fl.close();
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean saveImage(File file, Bitmap img){
        try{
            if(!file.exists())
                file.getParentFile().mkdirs();

            FileOutputStream fos = new FileOutputStream(file);
            img.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static Bitmap getImage(File file){
        if(file.exists()){
            Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath());
            return bmp;
        }else
            return null;
    }

    public static  Bitmap getImageFromGallery(ContentResolver c, Uri uri){
        Bitmap bmp = null;
        try{
           bmp = MediaStore.Images.Media.getBitmap(c,uri);
        }catch (Exception e){
            e.printStackTrace();
        }

        return bmp;
    }

    private static String convertStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static String GetHttp(Context context,String urlGet) throws NotConnectedExeption {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo(); //2
        if(networkInfo == null || !networkInfo.isConnected())
            throw new NotConnectedExeption();
        try{
            URL url = new URL(urlGet);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            String res = convertStreamToString(inputStream);
            return res;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static String getMesString(int mes){
        Context c = org.moviles.Context.getStringContext();
        switch (mes){
            case 1:
                return c.getString(R.string.Enero);
            case 2:
                return c.getString(R.string.Febrero);
            case 3:
                return c.getString(R.string.Marzo);
            case 4:
                return c.getString(R.string.Abril);
            case 5:
                return c.getString(R.string.Mayo);
            case 6:
                return c.getString(R.string.Junio);
            case 7:
                return c.getString(R.string.Julio);
            case 8:
                return c.getString(R.string.Agosto);
            case 9:
                return c.getString(R.string.Septiembre);
            case 10:
                return c.getString(R.string.Octubre);
            case 11:
                return c.getString(R.string.Noviembre);
            case 12:
                return c.getString(R.string.Diciembre);
            default:
                return "";
        }
    }

    public static Integer getImagenDeCondicionClima(String condicion){
        Context contexto = org.moviles.Context.getStringContext();
        if(condicion.equals(contexto.getString(R.string.despejado)))
           return R.drawable.soleado;

        if(condicion.equals(contexto.getString(R.string.lluvia)))
            return R.drawable.lluvia;

        if(condicion.equals(contexto.getString(R.string.lluvia_leve)))
            return R.drawable.lluvia_leve;

        if(condicion.equals(contexto.getString(R.string.nublado)))
            return R.drawable.nublado;

        if(condicion.equals(contexto.getString(R.string.parcialmente_nublado)))
            return R.drawable.parcialmente_nublado;

        if(condicion.equals(contexto.getString(R.string.tormenta_electrica)))
            return R.drawable.tormenta_electrica;

        if(condicion.equals(contexto.getString(R.string.tormenta_electrica_granizo)))
            return R.drawable.tormenta_electrica_granizo;

        return R.drawable.soleado;
    }
}
