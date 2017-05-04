package com.mycompany.clockplus;


import android.app.Activity;
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

import java.io.Serializable;
import java.util.ArrayList;

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
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == EDIT_ALARM) {
            if(resultCode == Activity.RESULT_OK){
                String result = data.getStringExtra("result");
                if(result.equals("delete")){
                    int position = data.getIntExtra("position", 0);
                    mDbHelper = new AlarmReaderDbHelper(getContext());
                    // Gets the data repository in write mode
                    SQLiteDatabase db = mDbHelper.getWritableDatabase();
                    String selection = AlarmContract.AlarmEntry._ID + " LIKE ?";
                    String[] selectionArgs = {Integer.toString(alarmList.get(position).getId())};
                    db.delete(AlarmContract.AlarmEntry.TABLE_NAME, selection, selectionArgs);
                    alarmList.remove(position);
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
