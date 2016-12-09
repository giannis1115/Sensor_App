package com.apps.mj.sensor_app;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.text.DecimalFormat;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by john on 23/11/2016.
 */

public class MyService extends Service implements SensorEventListener {

    private TextView xText, yText, zText;
    private SensorManager SM ;



    //Strings to register to create intent filter for registering the recivers
    private static final String ACTION_STRING_SERVICE = "ToService";
    private static final String ACTION_STRING_ACTIVITY = "ToActivity";


    public void onCreate() {

        SM = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        //mSensorListener = new MySensorEventListener();
        SM.registerListener(this, SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        SM.registerListener(this, SM.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId) {
        // We don't provide binding, so return null
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }


    @Override
    public void onDestroy(){

        SM.unregisterListener(this);
        //super.onDestroy();
    }



    public void sendMessage(String action,String msg){
        Intent intent = new Intent(ACTION_STRING_ACTIVITY);
        intent.putExtra("sensor_type", action);
        intent.putExtra("sensor_msg", msg);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intent);
    }

    private String Message ;


    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            sendMessage("ACELEROMETER","X Orientation: " +event.values[0]+"\nY Orientation: "+event.values[1]+"\nZ Orientation: " +event.values[2]);
        }else if (sensor.getType() == Sensor.TYPE_LIGHT) {
            sendMessage("LIGHT","L:"+event.values[0]);
        }
    }

    public String toString() {
        return Message;
    }

}


