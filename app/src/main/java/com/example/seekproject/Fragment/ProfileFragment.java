package com.example.seekproject.Fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seekproject.Activity.EditProfile;
import com.example.seekproject.Activity.LoginActivity;
import com.example.seekproject.R;
import com.example.seekproject.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {
    private TextView txtName, txtEdit;
    private ImageButton btnLogout;
    private LinearLayout subscription, helpCentre, emergency, settings;
    private FirebaseUser firebaseUser;
    private User mUser;
    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        txtName = v.findViewById(R.id.txt_profile_name);
        txtEdit = v.findViewById(R.id.txt_profile_edit);
        subscription = v.findViewById(R.id.settings_subscription);
        helpCentre = v.findViewById(R.id.settings_help);
        emergency = v.findViewById(R.id.settings_emergency);
        settings = v.findViewById(R.id.settings_settings);
        btnLogout = v.findViewById(R.id.btn_logout);

        subscription.setOnClickListener(this);
        helpCentre.setOnClickListener(this);
        emergency.setOnClickListener(this);
        settings.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        txtEdit.setOnClickListener(this);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mUser = User.getInstance();
        String name = firebaseUser.getDisplayName();
//        String name = mUser.getName();
//        Toast.makeText(getActivity(), mUser.getUserID(), Toast.LENGTH_SHORT).show();
        txtName.setText(name);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_profile_edit:
                // go to new activity to edit
                Intent edit = new Intent(getActivity(), EditProfile.class);
                startActivity(edit);
                getActivity().finish();
                break;
            case R.id.settings_subscription:
                //
                shortToast("subscription");
                break;
            case R.id.settings_help:
                //
                shortToast("help");
                break;
            case R.id.settings_emergency:
                //
                shortToast("emergency");
                break;
            case R.id.settings_settings:
                //
                shortToast("settings");
                break;
            case R.id.btn_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Sign out of the app?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                getActivity().finish();
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
        }
    }

    private void shortToast(String m) {
        Toast.makeText(getContext(), m, Toast.LENGTH_SHORT).show();
    }


}
