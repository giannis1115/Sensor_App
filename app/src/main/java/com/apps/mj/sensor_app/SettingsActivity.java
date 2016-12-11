package com.apps.mj.sensor_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Switch;

import android.widget.CompoundButton;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    Switch accSwitch ;
    Switch ligSwitch ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        accSwitch = (Switch) findViewById(R.id.accSwitch_id);
        ligSwitch = (Switch) findViewById(R.id.ligSwitch_id);

        //set switches to ON
        accSwitch.setChecked(true);
        ligSwitch.setChecked(true);




        //attach a listener to check for changes in state
        accSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    SensorApp sensorApp = new SensorApp();
                    sensorApp.accelerometer();
                }else{
                    SensorApp sensorApp = new SensorApp();
                    sensorApp.accelerometer();                }
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

}

