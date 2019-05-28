package com.example.seekproject.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seekproject.Doctor;
import com.example.seekproject.Fragment.ViewPagerFragment.DoctorDetailAboutFragment;
import com.example.seekproject.Fragment.ViewPagerFragment.DoctorDetailScheduleFragment;
import com.example.seekproject.R;
import com.example.seekproject.Adapter.ViewPagerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DoctorDetails extends AppCompatActivity {

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private ImageView doctorImage;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private TextView txt_drName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        tabLayout = findViewById(R.id.tabLayoutID);
        appBarLayout = findViewById(R.id.appBarLayoutID);
        viewPager = findViewById(R.id.viewPagerID);
        doctorImage = findViewById(R.id.img_doctorImage);
        txt_drName = findViewById(R.id.txt_doctorDetailName);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ViewPagerAdapter adapterDoctor = new ViewPagerAdapter(getSupportFragmentManager());
        adapterDoctor.addFragment(new DoctorDetailAboutFragment(), "About");
        adapterDoctor.addFragment(new DoctorDetailScheduleFragment(), "Schedule");

        viewPager.setAdapter(adapterDoctor);
        tabLayout.setupWithViewPager(viewPager);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Intent i = getIntent();
        String id = i.getStringExtra("id");
        loadDoctor(id);
    }

    public void loadDoctor(String doctorID) {
        DocumentReference doctorRef = db.collection("doctors").document(doctorID);
        doctorRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Doctor doctor = documentSnapshot.toObject(Doctor.class);
                String doctorName = doctor.getName();
                String doctorTitle = doctor.getTitle();
                String doctorProfession = doctor.getProfession();
                String doctorGender = doctor.getGender();
                String doctorAbout = doctor.getAbout();

                txt_drName.setText(doctorName);
                if (doctorGender.equals("male")) {
                    doctorImage.setImageResource(R.drawable.ic_doctor_male);
                }
                else {
                    doctorImage.setImageResource(R.drawable.ic_doctor_female);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
