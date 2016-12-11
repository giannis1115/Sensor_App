package com.apps.mj.sensor_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;



import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SensorApp extends AppCompatActivity {

    //Strings to register to create intent filter for registering the recivers
    private static final String ACTION_STRING_SERVICE = "ToService";
    private static final String ACTION_STRING_ACTIVITY = "ToActivity";
    private static final String Stop_Service = "Stop Service";
    private static final String Start_Service = "Start Service";
    private MyBroadcastReceiver Receiver;
    IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_app);
        final Button opButton = (Button) findViewById(R.id.button);
        Intent intent = new Intent(this, MyService.class);
        startService(intent);

        opButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //stopService(new Intent(SensorApp.this, MyService.class));
                if (opButton.getText().equals(Stop_Service)) {
                    stopService(new Intent(SensorApp.this, MyService.class));
                    opButton.setText(Start_Service);
                } else {
                    Intent intent = new Intent(SensorApp.this, MyService.class);
                    startService(intent);
                    opButton.setText(Stop_Service);
                }
            }
        });


        Button exitButton = (Button) findViewById(R.id.exitbutton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });


        //Start the service on launching the application
        TextView accText = (TextView) findViewById(R.id.textView);
        accText.setText("ACCELERATOR:NO READINGS");
        TextView ligText = (TextView) findViewById(R.id.textView2);
        ligText.setText("LIGHT:NO READINGS");
        Receiver = new MyBroadcastReceiver();
        intentFilter = new IntentFilter(ACTION_STRING_ACTIVITY);
        registerReceiver(Receiver, intentFilter);
        startService(new Intent(this, MyService.class));
    }


    //Inflate the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    //Handle the menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.settings_id:
                Intent intent = new Intent(this, SettingsActivity.class);
                this.startActivity(intent);
                break;
            case R.id.accelerometer_id:
                accelerometer();
                return true;
            case R.id.light_id:
                light();
                return true;
            case R.id.exit_id:
                exit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onResume() {

        super.onResume();
        registerReceiver(Receiver, intentFilter);
    }

    @Override
    public void onPause() {
        unregisterReceiver(Receiver);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
        Log.d("Service", "onDestroy");
    }

    //send broadcast from activity to all receivers listening to the action "ACTION_STRING_SERVICE"

    @Override
    public void onBackPressed() {
       exit();
    }

    //FUNCTIONS NEEDED


    public void setText(String msg) {
        TextView textview = (TextView) findViewById(R.id.textView);
        textview.setText(msg);
    }

    public void setText2(String msg) {
        TextView textview = (TextView) findViewById(R.id.textView2);
        textview.setText(msg);
    }


    public void accelerometer() {
        TextView textView = (TextView) findViewById(R.id.textView);
        TextView title = (TextView) findViewById(R.id.title1);
        if (textView.getVisibility()==View.VISIBLE) {
            textView.setVisibility(View.INVISIBLE);
            title.setVisibility(View.INVISIBLE);
        }
        else {
            textView.setVisibility(View.VISIBLE);
            title.setVisibility(View.VISIBLE);
        }
    }

    public void light() {
        TextView textView = (TextView) findViewById(R.id.textView2);
        TextView title = (TextView) findViewById(R.id.title2);
        if (textView.getVisibility()==View.VISIBLE) {
            textView.setVisibility(View.INVISIBLE);
            title.setVisibility(View.INVISIBLE);
        }
        else {
            textView.setVisibility(View.VISIBLE);
            title.setVisibility(View.VISIBLE);
        }
    }

    public void exit(){
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

}