package org.moviles.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import org.moviles.activity.R;
import org.moviles.business.ClimaBusiness;
import org.moviles.model.Ciudad;
import org.moviles.model.Clima;
import org.moviles.model.Configuracion;
import org.moviles.model.Usuario;
import org.moviles.utils.Util;

import java.util.Calendar;

public class ClimaWidget extends AppWidgetProvider {

    private static String WIDGET_BUTTON = "APPWIDGET_UPDATE";

    private Context context;
    private RemoteViews remoteView;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        org.moviles.Context.setContext(context);

        if(remoteView == null)
            remoteView = new RemoteViews(context.getPackageName(),
                    R.layout.widget_layout);

        if(intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_ENABLED))
            onCreate(context);

        if(intent.getAction().equals(WIDGET_BUTTON)){
            updateData();
        }
        if(intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)){
            onCreate(context);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        onCreate(context);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private void onCreate(Context context){
        Intent intent = new Intent(context,getClass());
        intent.setAction(WIDGET_BUTTON);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteView.setOnClickPendingIntent(R.id.btn_actializar,pendingIntent);
        remoteView.setOnClickPendingIntent(R.id.btn_actializar_error,pendingIntent);

        updateData();
    }

    private void updateData(){
        Usuario user = org.moviles.Context.getUsuarioBusiness().getCurrentUser();
        showUpdateAnim(true);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(user == null){
            String msg = context.getString(R.string.noUsuarioRegistrado);
            remoteView.setTextViewText(R.id.txt_error,msg);
            showError(true);
            return;
        }
        Configuracion conf = org.moviles.Context.getConfiguracionBusiness().getConfiguracion();
        Ciudad c = conf.getCiudad();
        if(c != null)
            new getCliamActualAsyncTask(c).execute();
        else{
            String msg = context.getString(R.string.seleccionarCiudad);

            remoteView.setTextViewText(R.id.txt_error,msg);
            showError(true);
        }
    }

    private void updateText(Context context,Clima clima,Ciudad ciudad){
        if(clima == null)
            return;

        remoteView.setTextViewText(R.id.txt_ciudad,ciudad.getName());
        remoteView.setTextViewText(R.id.txt_temperatura,clima.getTemperatura());
        remoteView.setTextViewText(R.id.txt_tempMax,clima.getTempMaxima());
        remoteView.setTextViewText(R.id.txt_tempMin,clima.getTempMinima());
        remoteView.setTextViewText(R.id.txt_actualizacion,clima.getHora()+":"+clima.getMinuto());
        remoteView.setImageViewResource(R.id.img_clima,
                Util.getImagenDeCondicionClima(clima.getCondicion()));

        //Actualizar widget
        AppWidgetManager.getInstance(context).updateAppWidget(
                new ComponentName(context, ClimaWidget.class),remoteView);
    }

    private void showError(boolean show){
        if(show){
            remoteView.setViewVisibility(R.id.contenedorPrincipal, View.GONE);
            remoteView.setViewVisibility(R.id.contenedor_error, View.VISIBLE);
        }else{
            remoteView.setViewVisibility(R.id.contenedorPrincipal, View.VISIBLE);
            remoteView.setViewVisibility(R.id.contenedor_error, View.GONE);
        }

        AppWidgetManager.getInstance(context).updateAppWidget(
                new ComponentName(context, ClimaWidget.class),remoteView);
    }

    private void showUpdateAnim(boolean show){
        if(show){
            remoteView.setViewVisibility(R.id.btn_actializar, View.GONE);
            remoteView.setViewVisibility(R.id.anim_update, View.VISIBLE);
        }else{
            remoteView.setViewVisibility(R.id.btn_actializar, View.VISIBLE);
            remoteView.setViewVisibility(R.id.anim_update, View.GONE);
        }

        AppWidgetManager.getInstance(context).updateAppWidget(
                new ComponentName(context, ClimaWidget.class),remoteView);
    }

    private class getCliamActualAsyncTask extends AsyncTask<Void,Void, Clima> {
        private Ciudad ciudad;

        public getCliamActualAsyncTask(Ciudad c){
            this.ciudad = c;
        }

        @Override
        protected Clima doInBackground(Void... voids) {
            ClimaBusiness climaBusiness = org.moviles.Context.getClimaBusiness(context);
            Clima clima = climaBusiness.getClimaActual(context,ciudad);

            if(clima == null) {
                clima = climaBusiness.getUltimoClimaActualGuardado();
            }else {
                climaBusiness.limpiarActualDB();

                Calendar calendar = Calendar.getInstance();

                clima.setHora(calendar.get(Calendar.HOUR_OF_DAY));
                clima.setMinuto(calendar.get(Calendar.MINUTE));
                climaBusiness.insertarClima(clima);
            }
            return clima;
        }

        @Override
        protected void onPostExecute(Clima clima) {
            showUpdateAnim(false);
            if(clima != null) {

                showError(false);

                updateText(context, clima, ciudad);

            }
            else {
                String msg = context.getString(R.string.errorActualizar);
                Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
            }
        }
    }
}
