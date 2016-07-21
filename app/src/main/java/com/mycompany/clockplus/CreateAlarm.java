package com.mycompany.clockplus;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


public class CreateAlarm extends AppCompatActivity {
    int alarm_hour;
    int alarm_min;
    Button displayTimeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_alarm_layout);

        displayTimeDialog = (Button) findViewById(R.id.alarmTime);
        displayTimeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                new TimePickerDialog(CreateAlarm.this, onTimeSetListener, alarm_hour, alarm_min, true).show();
            }
        }) ;
    }


    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener(){
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute){
            if (minute>9) {
                displayTimeDialog.setText(hourOfDay + ":" + minute);
            }else{
                displayTimeDialog.setText(hourOfDay+ ":0" + minute);
            }
        }
    };
}
