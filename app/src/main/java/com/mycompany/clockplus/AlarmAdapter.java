
/**
 * Copyright 2017 Andrew Steinmetz
 *
 * I provide the the instructor/TA the right to build and evaluate the following software package
 * for the purpose of determining my grade and program assessment
 */

package com.mycompany.clockplus;

import android.support.v7.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author Andrew Steinmetz   mailto: arsteinm@asu.edu
 * @version April 28
 */

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder>  {
    private static final String TAG = "CustomAdapter";
    private final ArrayList<Alarm> alarmArrayList;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView time;
        private final TextView name;

        public ViewHolder(View view){
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            time = (TextView) view  .findViewById(R.id.listAlarmTime);
            name = (TextView) view.findViewById(R.id.listAlarmName);
        }

        public TextView getTime(){
            return time;
        }

        public TextView getName(){
            return name;
        }

    }

    public  AlarmAdapter(ArrayList<Alarm> alarms){
        alarmArrayList = alarms;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_alarm, viewGroup, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG, "Element " + position + " set.");
        Alarm alarm = alarmArrayList.get(position);
        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        holder.getTime().setText(alarm.getTime());
        holder.getName().setText(alarm.getName());
    }

    @Override
    public int getItemCount() {
        return alarmArrayList.size();
    }
}
