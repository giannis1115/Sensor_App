package com.apps.mj.sensor_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.action;

public class SensorApp extends AppCompatActivity {

    //Strings to register to create intent filter for registering the recivers
    private static final String ACTION_STRING_SERVICE = "ToService";
    private static final String ACTION_STRING_ACTIVITY = "ToActivity";
    private com.apps.mj.sensor_app.MyBroadcastReciever Receiver;
    IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_app);
        Button stopButton = (Button) findViewById(R.id.button);
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //stopService(new Intent(SensorApp.this, MyService.class));
                stopService(new Intent(SensorApp.this, MyService.class));
            }
        });

        Button exitButton = (Button) findViewById(R.id.exitbutton);
        exitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new AlertDialog.Builder(SensorApp.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Closing Activity")
                        .setMessage("Are you sure you want to exit?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                System.exit(0);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });




        //Start the service on launching the application
        TextView textview = (TextView) findViewById(R.id.textView);
        textview.setText("ACELLERATOR:NO READINGS");
        textview = (TextView) findViewById(R.id.textView2);
        textview.setText("LIGHT:NO READINGS");
        Receiver= new com.apps.mj.sensor_app.MyBroadcastReciever();
        intentFilter = new IntentFilter(ACTION_STRING_ACTIVITY);
        registerReceiver(Receiver,intentFilter);
        startService(new Intent(this, MyService.class));
    }

    @Override
    public void onResume(){

        super.onResume();
        registerReceiver(Receiver,intentFilter);
    }

    @Override
    public void onPause(){
        unregisterReceiver(Receiver);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this, MyService.class);

        stopService(intent);
        Log.d("Service", "onDestroy");
// Unregister the receiver
        // unregisterReceiver(Receiver);
    }

    //send broadcast from activity to all receivers listening to the action "ACTION_STRING_SERVICE"

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }



    public void setText(String msg) {
        TextView textview = (TextView) findViewById(R.id.textView);
        textview.setText(msg);
    }

    public void setText2(String msg) {
        TextView textview = (TextView) findViewById(R.id.textView2);
        textview.setText(msg);
    }
}
