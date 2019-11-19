package org.moviles.config;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;


public class AlarmService {

    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;
    private Context context;
    public static final int REQUEST_CODE=101;
    private Intent intent;

    public AlarmService(Context context, Intent intent){
        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        this.context = context;
        this.intent = intent;
    }

    public void SetRepitingDayAlarm(Calendar calendar){
        alarmIntent = PendingIntent.getBroadcast(context, REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setInexactRepeating(AlarmManager.RTC,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                alarmIntent);
    }

    public void cancelAlarm(){
        alarmIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, PendingIntent.FLAG_NO_CREATE);

        if(alarmIntent != null)
            alarmManager.cancel(alarmIntent);
    }
}
