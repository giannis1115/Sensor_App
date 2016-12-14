package com.apps.mj.sensor_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Switch;

import android.widget.CompoundButton;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        final Switch accSwitch = (Switch) findViewById(R.id.accSwitch_id);
        final Switch ligSwitch = (Switch) findViewById(R.id.ligSwitch_id);

        accSwitch.setChecked(SensorApp.accFlag);
        ligSwitch.setChecked(SensorApp.ligFlag);

        //attach a listener to check for changes in state

        accSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    SensorApp.accelerometer();
                    accSwitch.setChecked(SensorApp.accFlag);
            }
        });


        ligSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        SensorApp.light();
                        ligSwitch.setChecked(SensorApp.ligFlag);
                }
        });
    }




    @Override
    protected void onResume(){

        super.onResume();
    }

}

