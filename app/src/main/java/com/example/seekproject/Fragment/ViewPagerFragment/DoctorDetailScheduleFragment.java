package com.example.seekproject.Fragment.ViewPagerFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.seekproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorDetailScheduleFragment extends Fragment implements View.OnClickListener {

    private CalendarView calendarView;
    private Button btn_8am, btn_9am, btn_10am, btn_12pm, btn_2pm, btn_3pm, btn_9pm, btn_seek;
    private String date;
    private String time;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    public DoctorDetailScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_doctor_detail_schedule, container, false);
        calendarView = v.findViewById(R.id.calendarView);
        btn_8am = v.findViewById(R.id.btn_8am);
        btn_9am = v.findViewById(R.id.btn_9am);
        btn_10am = v.findViewById(R.id.btn_10am);
        btn_12pm = v.findViewById(R.id.btn_12pm);
        btn_2pm = v.findViewById(R.id.btn_2pm);
        btn_3pm = v.findViewById(R.id.btn_3pm);
        btn_9pm = v.findViewById(R.id.btn_9pm);
        btn_seek = v.findViewById(R.id.btn_seek);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                date = dayOfMonth + "/" + month + "/" + year;
                Toast.makeText(getActivity(), date, Toast.LENGTH_SHORT).show();
            }
        });

        btn_8am.setOnClickListener(this);
        btn_9am.setOnClickListener(this);
        btn_10am.setOnClickListener(this);
        btn_12pm.setOnClickListener(this);
        btn_2pm.setOnClickListener(this);
        btn_3pm.setOnClickListener(this);
        btn_9pm.setOnClickListener(this);

        btn_seek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (date != null && time != null) {
                    String message = "Appointment set on " + date + " at " + time;
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "You have not selected date and time yet", Toast.LENGTH_SHORT).show();
                }
//                CollectionReference appCollection = db.collection("users").document(mAuth.getCurrentUser().getUid()).collection("appointment");
//                DocumentReference appointmentRef = db.collection("users").document(mAuth.getCurrentUser().getUid()).collection("appointment").document();
//                HashMap<String, Object> appointment = new HashMap<>();
//                appointment.put("date", date);
//                appointment.put("time", time);
////                appointmentRef.set(appointment);
//                appCollection.add(appointment);
            }
        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_8am:
                time = "8 AM";
                break;
            case R.id.btn_9am:
                time = "9 AM";
                break;
            case R.id.btn_10am:
                time = "10 AM";
                break;
            case R.id.btn_12pm:
                time = "12 PM";
                break;
            case R.id.btn_2pm:
                time = "2 PM";
                break;
            case R.id.btn_3pm:
                time = "3 AM";
                break;
            case R.id.btn_9pm:
                time = "9 PM";
                break;
        }
        Toast.makeText(getActivity(), time, Toast.LENGTH_SHORT).show();
    }
}
