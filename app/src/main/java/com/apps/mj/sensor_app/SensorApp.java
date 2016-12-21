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
import android.widget.Switch;
import android.widget.TextView;

public class SensorApp extends AppCompatActivity {

    //Strings to register to create intent filter for registering the recivers
    private static final String ACTION_STRING_SERVICE = "ToService";
    private static final String ACTION_STRING_ACTIVITY = "ToActivity";
    private static final String Stop_Service = "Stop Service";
    private static final String Start_Service = "Start Service";
    private MyBroadcastReceiver Receiver;
    IntentFilter intentFilter;

    private static TextView accText;
    private static TextView accTitle;
    private static TextView ligTitle;
    private static TextView ligText;
    public static boolean accFlag = true ;
    public static boolean ligFlag = true ;

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

        //titles
        accTitle = (TextView) findViewById(R.id.title1);
        ligTitle = (TextView) findViewById(R.id.title2);

        //switches
        //SensorApp.accFlag=true;
      //  SensorApp.ligFlag=true;

        //Start the service on launching the application
        accText = (TextView) findViewById(R.id.textView);
        accText.setText("ACCELERATOR:NO READINGS");
        ligText = (TextView) findViewById(R.id.textView2);
        ligText.setText("LIGHT:NO READINGS");
        Receiver = new MyBroadcastReceiver();
        intentFilter = new IntentFilter(ACTION_STRING_ACTIVITY);
        registerReceiver(Receiver, intentFilter);
        startService(new Intent(this, MyService.class));


        //Message test
        Message.setAttribute("ip","182.100.100");

        //accTitle.setText(Message.getAttribute("gth"));

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


    public static void accelerometer() {

        if (accText.getVisibility()==View.VISIBLE) {
            accFlag=false;

            accText.setVisibility(View.INVISIBLE);
            accTitle.setVisibility(View.INVISIBLE);
        }
        else {
            accFlag=true;

            accText.setVisibility(View.VISIBLE);
            accTitle.setVisibility(View.VISIBLE);
        }
    }

    public static void light() {
        if (ligText.getVisibility()==View.VISIBLE) {
            ligFlag=false;
            ligText.setVisibility(View.INVISIBLE);
            ligTitle.setVisibility(View.INVISIBLE);
        }
        else {
            ligFlag=true;

            ligText.setVisibility(View.VISIBLE);
            ligTitle.setVisibility(View.VISIBLE);
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