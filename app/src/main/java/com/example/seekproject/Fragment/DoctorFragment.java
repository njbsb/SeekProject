package com.example.seekproject.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.seekproject.Activity.DoctorDetails;
import com.example.seekproject.R;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorFragment extends Fragment implements View.OnClickListener {

    public DoctorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_doctor, container, false);

        ImageButton img_1 = v.findViewById(R.id.img_1);
        ImageButton img_2 = v.findViewById(R.id.img_2);
        ImageButton img_3 = v.findViewById(R.id.img_3);
        ImageButton img_4 = v.findViewById(R.id.img_4);
        ImageButton img_5 = v.findViewById(R.id.img_5);
        ImageButton img_6 = v.findViewById(R.id.img_6);
        ImageButton img_7 = v.findViewById(R.id.img_7);
        ImageButton img_8 = v.findViewById(R.id.img_8);
        ImageButton img_9 = v.findViewById(R.id.img_9);

        img_1.setOnClickListener(this);
        img_2.setOnClickListener(this);
        img_3.setOnClickListener(this);
        img_4.setOnClickListener(this);
        img_5.setOnClickListener(this);
        img_6.setOnClickListener(this);
        img_7.setOnClickListener(this);
        img_8.setOnClickListener(this);
        img_9.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        Intent doctorDetails = new Intent(getActivity(), DoctorDetails.class);
        switch (v.getId()) {
            case R.id.img_1:
                //
                doctorDetails.putExtra("id", "dr1");
                break;
            case R.id.img_2:
                doctorDetails.putExtra("id", "dr2");
                //
                break;
            case R.id.img_3:
                doctorDetails.putExtra("id", "dr3");
                //
                break;
            case R.id.img_4:
                doctorDetails.putExtra("id", "dr4");
                //
                break;
            case R.id.img_5:
                doctorDetails.putExtra("id", "dr5");
                //
                break;
            case R.id.img_6:
                doctorDetails.putExtra("id", "dr6");
                //
                break;
            case R.id.img_7:
                doctorDetails.putExtra("id", "dr7");
                //
                break;
            case R.id.img_8:
                doctorDetails.putExtra("id", "dr8");
                //
                break;
            case R.id.img_9:
                doctorDetails.putExtra("id", "dr9");
                //
                break;
        }
        startActivity(doctorDetails);
    }
}
