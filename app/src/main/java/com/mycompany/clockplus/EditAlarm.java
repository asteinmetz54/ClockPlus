package com.mycompany.clockplus;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mycompany.clockplus.database.AlarmContract;
import com.mycompany.clockplus.database.AlarmReaderDbHelper;

import java.io.Serializable;

public class EditAlarm extends AppCompatActivity implements Serializable {
    int alarm_hour;
    int alarm_min;
    TextView displayTimeDialog;
    AlarmReaderDbHelper mDbHelper;
    private Alarm alarm;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_alarm_layout);
        Intent intent = getIntent();
        position = intent.getIntExtra("position",0);
        alarm = (Alarm) intent.getSerializableExtra("alarm");

        displayTimeDialog = (TextView) findViewById(R.id.alarmTime);
        displayTimeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                android.text.format.DateFormat dateFormat = new android.text.format.DateFormat();
                boolean is24HourFormat= dateFormat.is24HourFormat(getApplicationContext());
                new TimePickerDialog(EditAlarm.this, onTimeSetListener, alarm.getHour(), alarm.getMinute(), is24HourFormat).show();
            }
        }) ;
        displayTimeDialog.setText(alarm.getTime());

        Button saveAlarm = (Button) findViewById(R.id.saveAlarm);
        saveAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", "save");
                returnIntent.putExtra("position", position);
                returnIntent.putExtra("alarm", alarm);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

  /*
     * One way to create Aciton Bar Buttons is to use xml menu specification. Create the file:
     * res/menu/main_activity_actions.xml to include contents as in this project.
     * reference to any images for the action bar should be created by right clicking on res folder
     * in the project and creating a new image asset. Be sure to specify Action Bar & Tab Icon
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_alarm_delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*
     * Implement onOptionsItemSelected(MenuItem item){} to handle clicks of buttons that are
     * in the action bar.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", "delete");
                returnIntent.putExtra("position", position);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener(){
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute){
            alarm.setHour(hourOfDay);
            alarm.setMinute(minute);
            displayTimeDialog.setText(alarm.getTime());
        }
    };
}
