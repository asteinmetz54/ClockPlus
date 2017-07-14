package com.mycompany.clockplus.database;

import android.provider.BaseColumns;

/**
 * Created by Andrew on 4/29/2017.
 */

public class AlarmContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private AlarmContract() {}

    /* Inner class that defines the table contents */
    public static class AlarmEntry implements BaseColumns {
        public static final String TABLE_NAME = "alarm";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_MINUTE = "minute";
        public static final String COLUMN_NAME_HOUR = "hour";
        public static final String COLUMN_NAME_ENABLED = "enabled";
    }


}
