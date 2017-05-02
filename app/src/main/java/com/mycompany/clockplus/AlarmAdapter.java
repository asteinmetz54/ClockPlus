
/**
 * Copyright 2017 Andrew Steinmetz
 *
 * I provide the the instructor/TA the right to build and evaluate the following software package
 * for the purpose of determining my grade and program assessment
 */

package com.mycompany.clockplus;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import android.widget.TextView;
import android.widget.Toast;

import com.mycompany.clockplus.Alarm;
import com.mycompany.clockplus.R;

import java.util.ArrayList;

/**
 * @author Andrew Steinmetz   mailto: arsteinm@asu.edu
 * @version April 28
 */

public class AlarmAdapter extends ArrayAdapter<Alarm>  {

    public  AlarmAdapter(Context context, ArrayList<Alarm> alarms){
        super(context, 0, alarms);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent){
        final Alarm alarm = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_alarm, parent, false);
        }


        TextView time = (TextView) convertView.findViewById(R.id.listAlarmTime);
        TextView name = (TextView) convertView.findViewById(R.id.listAlarmName);

        time.setText(alarm.getTime());
        name.setText(alarm.getName());
        return convertView;
    }
}
