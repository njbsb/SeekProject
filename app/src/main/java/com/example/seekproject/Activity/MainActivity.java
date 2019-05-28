package com.example.seekproject.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.seekproject.Fragment.ActivityFragment;
import com.example.seekproject.Fragment.CalendarFragment;
import com.example.seekproject.Fragment.DoctorFragment;
import com.example.seekproject.Fragment.HomeFragment;
import com.example.seekproject.Fragment.ProfileFragment;
import com.example.seekproject.R;
import com.example.seekproject.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseUser user;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        mUser = User.getInstance();

        final DocumentReference userRef = db.collection("users").document(user.getUid());
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                mUser = documentSnapshot.toObject(User.class);
//                Toast.makeText(getApplicationContext(), mUser.getName(), Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Intent i = getIntent();
        String f = i.getStringExtra("fragment");
        if (f != null) {
            if (f.equals("profile"))
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
        }
        else
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;
            switch (menuItem.getItemId()) {
                case R.id.item_0:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.item_1:
                    selectedFragment = new DoctorFragment();
                    break;
                case R.id.item_2:
                    selectedFragment = new ActivityFragment();
                    break;
//                case R.id.item_3:
//                    selectedFragment = new CalendarFragment();
//                    break;
                case R.id.item_4:
                    selectedFragment = new ProfileFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };
}
