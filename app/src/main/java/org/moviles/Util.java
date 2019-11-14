package org.moviles;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.core.content.ContextCompat;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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

    public static String GetHttp(Context context,String urlGet) throws NotConnectedExeption{
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



}
