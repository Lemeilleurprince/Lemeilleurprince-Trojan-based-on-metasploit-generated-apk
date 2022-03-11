package com.mine.msf_apk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MainBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        MainService.startService(context);
        /*

        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            MainService.startService(context);
        }

         */
    }
}
