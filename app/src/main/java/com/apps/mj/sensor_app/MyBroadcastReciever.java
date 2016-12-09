package com.apps.mj.sensor_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by john on 24/11/2016.
 */

public class MyBroadcastReciever extends BroadcastReceiver  {
    //Strings to register to create intent filter for registering the recivers
    private static final String ACTION_STRING_SERVICE = "ToService";
    private static final String ACTION_STRING_ACTIVITY = "ToActivity";

    @Override
    public void onReceive(Context context, Intent intent) {
        //Log.d("FAILLLL", "onReceive: "+intent.getExtras().getString("sensor_msg"));
        if((intent.getExtras().getString("sensor_type").equals("LIGHT")))
            ( (SensorApp) context).setText2(intent.getExtras().getString("sensor_msg"));
        else
            ( (SensorApp) context).setText(intent.getExtras().getString("sensor_msg"));
    }
}
