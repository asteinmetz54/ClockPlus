package com.mycompany.clockplus;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends FragmentActivity, AppCompatActivity {
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager)findViewById(R.id.pager);
        SwipeAdapter sadapter = new SwipeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(sadapter);
    }


    public void addAlarm(View view){
        Intent intent = new Intent(this, CreateAlarm.class);
        startActivity(intent);
    }

    public void jumpToAlarm(View view){
        viewPager.setCurrentItem(0, true);
    }

    public void jumpToTimer(View view){
        viewPager.setCurrentItem(1, true);
    }

    public void jumpToStopwatch(View view){
        viewPager.setCurrentItem(2, true);
    }

    public void jumpToWorldClock(View view){
        viewPager.setCurrentItem(3,true);
    }

}
