package com.example.seekproject.Fragment.ViewPagerFragment;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seekproject.Doctor;
import com.example.seekproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorDetailAboutFragment extends Fragment implements View.OnClickListener {

    private TextView title, profession, about;
    private ImageButton btnEmail, btnCall;
    private String email, phone;

    public DoctorDetailAboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_doctor_detail_about, container, false);

        title = v.findViewById(R.id.txt_about_title);
        profession = v.findViewById(R.id.txt_about_profession);
        about = v.findViewById(R.id.txt_about_aboutme);
        btnCall = v.findViewById(R.id.imgBtn_call);
        btnEmail = v.findViewById(R.id.imgBtn_email);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Intent i = getActivity().getIntent();
        String id = i.getStringExtra("id");

        DocumentReference doctorRef = db.collection("doctors").document(id);
        doctorRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Doctor doctor = documentSnapshot.toObject(Doctor.class);
                title.setText(doctor.getTitle());
                profession.setText(doctor.getProfession());
                about.setText(doctor.getAbout());
                phone = doctor.getPhone();
                email = doctor.getEmail();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btnEmail.setOnClickListener(this);
        btnCall.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
//        PackageManager pm = getActivity().getPackageManager();
        switch (v.getId()) {
            case R.id.imgBtn_call:
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel: " + phone));

                if (callIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    getActivity().startActivity(callIntent);
                }
                else {
                    Toast.makeText(getActivity(), "Sorry, no action can handle this action and data", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.imgBtn_email:
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Seek for help");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Hi, i am seeking for help");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                if (emailIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    getActivity().startActivity(emailIntent);
                }
                else {
                    Toast.makeText(getActivity(), "Sorry, no action can handle this action and data", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
