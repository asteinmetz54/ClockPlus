/**
 * Copyright 2017 Andrew Steinmetz
 *
 * I provide the the instructor/TA the right to build and evaluate the following software package
 * for the purpose of determining my grade and program assessment
 */

package com.mycompany.clockplus;

import android.content.Context;

/**
 * @author Andrew Steinmetz   mailto: arsteinm@asu.edu
 * @version April 28
 */
public class Alarm {
    private String name;
    private  int hour, minute;
    private int id;
    private Context context;

    public Alarm(){}

    public Alarm(Context context, int hour, int minute, String name, int id){
        this.context = context;
        this.hour = hour;
        this.minute = minute;
        this.name = name;
        this.id = id;
    }

    public String getTime(){
        return Integer.toString(hour) + ":" + Integer.toString(minute);
//        android.text.format.DateFormat dateFormat = new android.text.format.DateFormat();
//        boolean is24HourFormat= dateFormat.is24HourFormat(context);
//        if(is24HourFormat){
//            return Integer.toString(hour) + ":" + Integer.toString(minute);
//        } else{
//            String amOrPm;
//            String hourAmPm;
//            if (hour > 12){
//                hourAmPm = Integer.toString(hour -12);
//                amOrPm = "p.m.";
//            } else{
//                hourAmPm = Integer.toString(hour);
//                amOrPm = "a.m.";
//            }
//            return hourAmPm + ":" + minute + amOrPm;
//        }
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }
}
