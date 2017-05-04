package com.mycompany.clockplus;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.mycompany.clockplus.database.AlarmContract;
import com.mycompany.clockplus.database.AlarmReaderDbHelper;

import java.io.Serializable;

public class EditAlarm extends AppCompatActivity implements Serializable {
    int alarm_hour;
    int alarm_min;
    Button displayTimeDialog;
    AlarmReaderDbHelper mDbHelper;
    private Alarm alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_alarm_layout);
        Intent intent = getIntent();
        final int position = intent.getIntExtra("position",0);
        alarm = (Alarm) intent.getSerializableExtra("alarm");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        displayTimeDialog = (Button) findViewById(R.id.alarmTime);
        displayTimeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                new TimePickerDialog(EditAlarm.this, onTimeSetListener, alarm_hour, alarm_min, true).show();
            }
        }) ;
        displayTimeDialog.setText(alarm.getTime());

        Button deleteAlarm = (Button) findViewById(R.id.deleteAlarm);
        deleteAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", "delete");
                returnIntent.putExtra("position", position);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();

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
