package com.mycompany.clockplus;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Timer.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Timer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Timer extends Fragment {

    public Timer(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.timer_fragment_layout, container, false);
    }


}
