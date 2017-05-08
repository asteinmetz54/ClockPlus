package com.mycompany.clockplus;


import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TimePicker;

import com.mycompany.clockplus.database.AlarmContract;
import com.mycompany.clockplus.database.AlarmReaderDbHelper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlarmFragment extends Fragment implements Serializable{

    static final int EDIT_ALARM = 1;
    private ArrayList<Alarm> alarmList;

    private ListView listView;

    private AlarmAdapter alarmAdapter;
    AlarmReaderDbHelper mDbHelper;

    public AlarmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alarm_fragment_layout, container, false);
        // Inflate the layout for this fragment
        alarmList = new ArrayList<>();
        loadAlarmData();
        listView = (ListView)view.findViewById(R.id.listview_alarm);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Alarm alarm = (Alarm) alarmList.get(position);

                Intent intent = new Intent(getContext(), EditAlarm.class);
                intent.putExtra("position", position );
                intent.putExtra("alarm", alarm);
                startActivityForResult(intent,EDIT_ALARM);
            }
        });
        alarmAdapter = new AlarmAdapter(getContext(), alarmList);
        listView.setAdapter(alarmAdapter);

        FloatingActionButton newAlarmButton = (FloatingActionButton) view.findViewById(R.id.alarm_button);
        newAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                android.text.format.DateFormat dateFormat = new android.text.format.DateFormat();
                boolean is24HourFormat= dateFormat.is24HourFormat(getContext());
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int min = calendar.get(Calendar.MINUTE);
                new TimePickerDialog(getContext(), onTimeSetListener, hour, min, is24HourFormat).show();
            }
        });
        return view;
    }


    //Handle what to do with time picker info
    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

            mDbHelper = new AlarmReaderDbHelper(getContext());
            // Gets the data repository in write mode
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(AlarmContract.AlarmEntry.COLUMN_NAME_HOUR, hourOfDay);
            values.put(AlarmContract.AlarmEntry.COLUMN_NAME_MINUTE, minute);

            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(AlarmContract.AlarmEntry.TABLE_NAME, null, values);
            Alarm newAlarm = new Alarm();
            newAlarm.setHour(hourOfDay);
            newAlarm.setMinute(minute);
            alarmList.add(newAlarm);
            alarmAdapter.notifyDataSetChanged();

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == EDIT_ALARM) {
            if(resultCode == Activity.RESULT_OK){
                String result = data.getStringExtra("result");
                int position = data.getIntExtra("position", 0);
                if(result.equals("delete")){
                    mDbHelper = new AlarmReaderDbHelper(getContext());
                    // Gets the data repository in write mode
                    SQLiteDatabase db = mDbHelper.getWritableDatabase();
                    String selection = AlarmContract.AlarmEntry._ID + " LIKE ?";
                    String[] selectionArgs = {Integer.toString(alarmList.get(position).getId())};
                    db.delete(AlarmContract.AlarmEntry.TABLE_NAME, selection, selectionArgs);
                    alarmList.remove(position);
                    alarmAdapter.notifyDataSetChanged();
                }else if (result.equals("save")){
                    Alarm alarm = (Alarm) data.getSerializableExtra("alarm");
                    mDbHelper = new AlarmReaderDbHelper(getContext());
                    SQLiteDatabase db = mDbHelper.getReadableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(AlarmContract.AlarmEntry.COLUMN_NAME_HOUR, alarm.getHour());
                    values.put(AlarmContract.AlarmEntry.COLUMN_NAME_MINUTE, alarm.getMinute());
                    values.put(AlarmContract.AlarmEntry.COLUMN_NAME_NAME, alarm.getName());

                    String selection = AlarmContract.AlarmEntry._ID + " LIKE ?";
                    String[] selectionArgs = {Integer.toString(alarm.getId())};

                    int count = db.update(
                            AlarmContract.AlarmEntry.TABLE_NAME,
                            values,
                            selection,
                            selectionArgs);
                    alarmList.set(position, alarm);
                    alarmAdapter.notifyDataSetChanged();

                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    @Override
    public void onDestroy() {
        mDbHelper.close();
        super.onDestroy();
    }


    private void loadAlarmData() {
        mDbHelper = new AlarmReaderDbHelper(getContext());
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                AlarmContract.AlarmEntry._ID,
                AlarmContract.AlarmEntry.COLUMN_NAME_NAME,
                AlarmContract.AlarmEntry.COLUMN_NAME_MINUTE,
                AlarmContract.AlarmEntry.COLUMN_NAME_HOUR
        };

        String sortOrder =
                AlarmContract.AlarmEntry._ID + " DESC";
        Cursor cursor = db.query(
                AlarmContract.AlarmEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        int hour, minute, id;
        String name;
        android.text.format.DateFormat dateFormat = new android.text.format.DateFormat();
        boolean is24HourFormat= dateFormat.is24HourFormat(getContext());
        while (cursor.moveToNext()) {
            id = Integer.parseInt(cursor.getString(0));
            name = cursor.getString(1);
            minute = Integer.parseInt(cursor.getString(2));
            hour = Integer.parseInt(cursor.getString(3));
            Alarm alarm = new Alarm(hour, minute, name, id,is24HourFormat);
            alarmList.add(alarm);
        }


    }


}
