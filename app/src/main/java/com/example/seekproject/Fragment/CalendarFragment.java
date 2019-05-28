package com.example.seekproject.Fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.seekproject.Adapter.ViewPagerAdapter;
import com.example.seekproject.Fragment.ViewPagerFragment.AppointmentPast;
import com.example.seekproject.Fragment.ViewPagerFragment.AppointmentUpcoming;
import com.example.seekproject.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageButton btnHome;

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);
        btnHome = v.findViewById(R.id.btn_home);
        tabLayout = v.findViewById(R.id.tabLayoutCalendarID);
        viewPager = v.findViewById(R.id.viewpagerCalendarID);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        ViewPagerAdapter adapterCalendar = new ViewPagerAdapter(getChildFragmentManager());
        adapterCalendar.addFragment(new AppointmentUpcoming(), "Upcoming ");
        adapterCalendar.addFragment(new AppointmentPast(), "Past");

        viewPager.setAdapter(adapterCalendar);
        tabLayout.setupWithViewPager(viewPager);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment home = new HomeFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, home).commit();
            }
        });
    }


}
