
/**
 * Copyright 2017 Andrew Steinmetz
 *
 * I provide the the instructor/TA the right to build and evaluate the following software package
 * for the purpose of determining my grade and program assessment
 */

package com.mycompany.clockplus;

import android.app.LauncherActivity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.Switch;
import android.widget.TextView;

import com.mycompany.clockplus.databinding.ListItemAlarmBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrew Steinmetz   mailto: arsteinm@asu.edu
 * @version April 28
 */

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder>  {
    private static final String TAG = "CustomAdapter";
    private final ArrayList<Alarm> alarmArrayList;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ListItemAlarmBinding binding;
        public ViewHolder(ListItemAlarmBinding itemView){
            super(itemView.getRoot());
            binding = itemView;
            binding.listAlarmSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            binding.listItemAlarm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    
                }
            });

        }
    }


    public  AlarmAdapter(ArrayList<Alarm> alarms){
        alarmArrayList = alarms;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int viewType) {
        ListItemAlarmBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                R.layout.list_item_alarm,
                viewGroup,
                false);


        return new ViewHolder(binding);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG, "Element " + position + " set.");
        Alarm alarm = alarmArrayList.get(position);
        holder.binding.setAlarm(alarm);
    }

    @Override
    public int getItemCount() {
        return alarmArrayList.size();
    }
}
