package com.mycompany.clockplus;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TimePicker;

import com.mycompany.clockplus.database.AlarmContract;
import com.mycompany.clockplus.database.AlarmReaderDbHelper;

import java.util.Calendar;


public class MainActivity extends FragmentActivity {

    //request codes
    static final private int MY_PERMISSIONS_REQUEST_READ_CALENDAR = 1;

    private String accountName = null;
    private AlarmReaderDbHelper mDbHelper;



    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.pager);
        SwipeAdapter sadapter = new SwipeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(sadapter);


    }




    @Override
    protected void onDestroy() {
        if(mDbHelper!=null)
        mDbHelper.close();
        super.onDestroy();
    }





    /**
     * Launch AddCalendarAlarm while checking if proper permissions to read calendar are granted
     *
     * @param view
     */
    @TargetApi(Build.VERSION_CODES.M)
    public void addCalendarAlarm(View view) {
        //check if the app has permission to read calendar
        int hasReadCalandarPermission = checkSelfPermission(Manifest.permission.READ_CALENDAR);
        if (hasReadCalandarPermission != PackageManager.PERMISSION_GRANTED) {
            if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_CALENDAR)) {
                showMessageOKCancel("You need to allow access to Read Calendar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(new String[]{Manifest.permission.READ_CALENDAR},
                                        MY_PERMISSIONS_REQUEST_READ_CALENDAR);
                            }
                        });
                return;
            }
            requestPermissions(new String[]{Manifest.permission.READ_CALENDAR},
                    MY_PERMISSIONS_REQUEST_READ_CALENDAR);
            return;
        } else if (hasReadCalandarPermission == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(this, AddCalendarAlarm.class);
            startActivity(intent);

        }
    }



    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CALENDAR: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Intent intent = new Intent(this, AddCalendarAlarm.class);
                    startActivity(intent);

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    public void jumpToAlarm(View view) {
        viewPager.setCurrentItem(0, true);
    }

    public void jumpToTimer(View view) {
        viewPager.setCurrentItem(1, true);
    }

    public void jumpToStopwatch(View view) {
        viewPager.setCurrentItem(2, true);
    }

    public void jumpToWorldClock(View view) {
        viewPager.setCurrentItem(3, true);
    }


}
