package com.example.seekproject.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.seekproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeFragment extends Fragment {
    private TextView txtName;
    private FirebaseUser user;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        txtName = v.findViewById(R.id.txt_home_name);
        user = FirebaseAuth.getInstance().getCurrentUser();
        txtName.setText(user.getDisplayName());
        return v;
    }

}
