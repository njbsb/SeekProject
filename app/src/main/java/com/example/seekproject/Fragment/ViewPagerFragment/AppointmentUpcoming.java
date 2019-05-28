package com.example.seekproject.Fragment.ViewPagerFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.seekproject.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentUpcoming extends Fragment {


    public AppointmentUpcoming() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_appointment_upcoming, container, false);
        return v;
    }

}
