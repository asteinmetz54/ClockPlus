package com.mycompany.clockplus;


import android.app.TimePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TimePicker;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlarmFragment extends Fragment {
    private ArrayList<Alarm> alarmList;
    SparseArray<AlarmGroup> groups = new SparseArray<AlarmGroup>();

    public AlarmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alarm_fragment_layout, container, false);
        // Inflate the layout for this fragment
        loadAlarmData();
        ExpandableListView expandableListView;
        expandableListView = (ExpandableListView) view.findViewById(R.id.listview_alarm);
        ExpandableListAdapter listAdapter = new ExpandableListAdapter(getActivity(), groups);
        expandableListView.setAdapter(listAdapter);
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
        int index = 0;
        while (cursor.moveToNext()) {
            id = Integer.parseInt(cursor.getString(0));
            name = cursor.getString(1);
            minute = Integer.parseInt(cursor.getString(2));
            hour = Integer.parseInt(cursor.getString(3));
            Alarm alarm = new Alarm(getContext(), hour, minute, name, id);
            AlarmGroup group = new AlarmGroup(alarm.getTime());
            group.children.add(alarm);
            groups.append(index, group);
            index++;
        }


    }


}
