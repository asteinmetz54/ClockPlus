package com.mycompany.clockplus;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mycompany.clockplus.database.AlarmContract;
import com.mycompany.clockplus.database.AlarmReaderDbHelper;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlarmFragment extends Fragment {
    public static final String EXTRA_HOUR = "com.mycompany.clockplus.HOUR";
    public static final String EXTRA_MIN = "com.mycompany.clockplus.MIN";
    public static final String EXTRA_NAME = "com.mycompany.clockplus.NAME";
    public static final String EXTRA_ID = "com.mycompany.clockplus.ID";
    private ArrayList<Alarm> alarmList;

    private ListView listView;

    private AlarmAdapter alarmAdapter;

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
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Alarm alarm = (Alarm) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(getContext(), CreateAlarm.class);
                intent.putExtra(EXTRA_HOUR, alarm.getHour());
                intent.putExtra(EXTRA_MIN, alarm.getMinute());
                intent.putExtra(EXTRA_NAME, alarm.getName());
                intent.putExtra(EXTRA_ID, alarm.getId());
                startActivity(intent);
            }
        });
        alarmAdapter = new AlarmAdapter(getContext(), alarmList);
        listView.setAdapter(alarmAdapter);
        return view;
    }

    private void loadAlarmData() {
        AlarmReaderDbHelper mDbHelper = new AlarmReaderDbHelper(getContext());
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
        while (cursor.moveToNext()) {
            id = Integer.parseInt(cursor.getString(0));
            name = cursor.getString(1);
            minute = Integer.parseInt(cursor.getString(2));
            hour = Integer.parseInt(cursor.getString(3));
            Alarm alarm = new Alarm(getContext(), hour, minute, name, id);
            alarmList.add(alarm);
        }


    }


}
