package com.mycompany.clockplus;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import static com.mycompany.clockplus.AlarmFragment.EXTRA_HOUR;
import static com.mycompany.clockplus.AlarmFragment.EXTRA_ID;
import static com.mycompany.clockplus.AlarmFragment.EXTRA_MIN;


public class CreateAlarm extends AppCompatActivity {
    int alarm_hour;
    int alarm_min;
    Button displayTimeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_alarm_layout);
        Intent intent = getIntent();
//        int hour = intent.getStringExtra(EXTRA_HOUR);
//        int min = intent.getStringExtra(EXTRA_MIN);
//        int id = intent.getStringExtra(EXTRA_ID);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        displayTimeDialog = (Button) findViewById(R.id.alarmTime);
        displayTimeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                new TimePickerDialog(CreateAlarm.this, onTimeSetListener, alarm_hour, alarm_min, true).show();
            }
        }) ;

        Button deleteAlarm = (Button) findViewById(R.id.deleteAlarm);
        deleteAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_delete:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
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
