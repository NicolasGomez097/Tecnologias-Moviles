package org.moviles.config;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import org.moviles.activity.R;
import org.moviles.model.Ciudad;
import org.moviles.model.Clima;
import org.moviles.model.Usuario;

public class NotificationService extends BroadcastReceiver {

    private String NOTIFICATION_CHANNEL_ID = "Clima app";

    public void createChanelIfNotExists(Context context){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            if(notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID) != null)
                return;

            CharSequence name = context.getString(R.string.app_name);
            String description = context.getString(R.string.channel_description);

            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void sendNotification(Context context,String text) {
        createChanelIfNotExists(context);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,
                NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.BigTextStyle());

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        new MakeNotificationAsyncTask().execute(context);
    }

    private class MakeNotificationAsyncTask extends AsyncTask<Context,Void,Void>{
        @Override
        protected Void doInBackground(Context... contexts) {
            Log.d("Notificacion Background","Inicio");


            try{
                Context context = contexts[0];
                org.moviles.Context.setContext(context);

                Ciudad ciudad = org.moviles.Context.getConfiguracionBusiness().getConfiguracion().getCiudad();
                Clima clima = org.moviles.Context.getClimaBusiness(context).getClimaActual(
                        context,ciudad);
                if(clima == null)
                    return null;

                Usuario usuario = org.moviles.Context.getUsuarioBusiness().getCurrentUser();
                String msg = context.getString(R.string.msgNotificacion);
                msg = String.format(msg,
                        usuario.getUsuario(), ciudad.getName(),
                        clima.getTemperatura(),clima.getTempMaxima(),
                        clima.getTempMinima());

                sendNotification(context,msg);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
}
