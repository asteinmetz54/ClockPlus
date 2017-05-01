package com.mycompany.clockplus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 4/30/2017.
 */

public class AlarmGroup {
    public String alarmTime;
    public final List<Alarm> children = new ArrayList<Alarm>();

    public AlarmGroup(String alarmTime) {
        this.alarmTime = alarmTime;
    }
}
