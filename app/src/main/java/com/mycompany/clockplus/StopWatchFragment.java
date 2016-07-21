package com.mycompany.clockplus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;

import java.util.concurrent.ConcurrentHashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StopWatchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StopWatchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StopWatchFragment extends Fragment {

    Chronometer mchronometer;
    private int count = 0;
    private long base = 0;

    public StopWatchFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.stopwatch_fragment_layout, container, false);

        mchronometer = (Chronometer) rootView.findViewById(R.id.chronometer);
        mchronometer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startStopChronometer(view);
            }
        });
        // Inflate the layout for this fragment
        return rootView;

    }

    public void startStopChronometer(View view){

        if ((count % 2) == 0) {
            mchronometer.start();
            count++;
        } else {
            mchronometer.stop();
            count++;
        }

    }

}
