/**
 * Copyright 2017 Andrew Steinmetz
 *
 * I provide the the instructor/TA the right to build and evaluate the following software package
 * for the purpose of determining my grade and program assessment
 */

package com.mycompany.clockplus;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.io.Serializable;

/**
 * @author Andrew Steinmetz   mailto: arsteinm@asu.edu
 * @version April 28
 */
public class Alarm extends BaseObservable implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private  int hour, minute;
    private boolean isOn;
    private int id;
    private int mdata;
    boolean is24HourFormat;

    public Alarm(){}

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        this.isOn = on;
    }

    public Alarm(int hour, int minute, String name, int id, boolean is24HourFormat){

        this.hour = hour;
        this.minute = minute;
        this.name = name;
        this.id = id;
        this.is24HourFormat = is24HourFormat;
        isOn = true;
    }

    @Bindable
    public String getTime(){
        String time;
        if(is24HourFormat){
            if (hour < 10){
                time = "0" + hour;
            }else{
                time = Integer.toString(hour);
            }
            time += ":";
            if(minute < 10){
                time += "0" + minute;
            }else{
                time += Integer.toString(minute);
            }

        }else {
            if(hour > 12){
                int hour12 = hour - 12;
                time = Integer.toString(hour12);
            }else {
                if (hour == 0){
                    time = "12";
                }else time = Integer.toString(hour);
            }
            time +=":";
            if(minute < 10){
                time += "0" + minute;
            }else{
                time += minute;
            }
            if (hour <= 12){
                time += "am";
            } else {
                time += "pm";
            }
        }
        return time;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setHour(int hour) {
        this.hour = hour;
        notifyPropertyChanged(BR.time);
    }

    public void setMinute(int minute) {
        this.minute = minute;
        notifyPropertyChanged(BR.time);
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public void setId(int id) {
        this.id = id;
    }


}
