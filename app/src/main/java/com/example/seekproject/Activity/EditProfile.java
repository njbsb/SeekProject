package com.example.seekproject.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seekproject.R;
import com.example.seekproject.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CCPCountry;
import com.hbb20.CountryCodePicker;

public class EditProfile extends AppCompatActivity {

    private EditText et_name, et_phone, et_email, et_address, et_newPassword, et_confirmPassword, et_oldPassword;
    private CountryCodePicker ccp;
    private Button btn_editSave;
    private TextView txt_changePassword;
    private boolean changePassword;
    private String initial;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        et_name = findViewById(R.id.et_profile_name);
        et_phone = findViewById(R.id.et_profile_phone);
        et_email = findViewById(R.id.et_profile_email);
        et_address = findViewById(R.id.et_profile_address);
        et_oldPassword = findViewById(R.id.et_profile_oldPassword);
        et_newPassword = findViewById(R.id.et_profile_newPassword);
        et_confirmPassword = findViewById(R.id.et_profile_confirmPassword);
        ccp = findViewById(R.id.ccpicker);
        btn_editSave = findViewById(R.id.btn_profile_save);
        txt_changePassword = findViewById(R.id.txt_profile_changePassword);
    }

    @Override
    protected void onStart() {
        super.onStart();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        changePassword = false;
        et_oldPassword.setVisibility(View.INVISIBLE);
        et_newPassword.setVisibility(View.INVISIBLE);
        et_confirmPassword.setVisibility(View.INVISIBLE);
//        et_newPassword.setEnabled(false);
//        et_confirmPassword.setEnabled(false);
//        et_newPassword.setKeyListener(null);
//        et_confirmPassword.setKeyListener(null);

        txt_changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!changePassword) {
                    et_oldPassword.setVisibility(View.VISIBLE);
                    et_newPassword.setVisibility(View.VISIBLE);
                    et_confirmPassword.setVisibility(View.VISIBLE);
                    changePassword = true;
                    final String oldPassword = et_oldPassword.getText().toString();

                }
                else {
                    et_oldPassword.setVisibility(View.INVISIBLE);
                    et_newPassword.setVisibility(View.INVISIBLE);
                    et_confirmPassword.setVisibility(View.INVISIBLE);
//                    et_newPassword.setEnabled(false);
//                    et_confirmPassword.setEnabled(false);
                    changePassword = false;
                }

            }
        });

        final DocumentReference userRef = db.collection("users").document(mAuth.getCurrentUser().getUid());
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                mUser = documentSnapshot.toObject(User.class);
                et_name.setText(mUser.getName());
                et_email.setText(mUser.getEmail());
                et_phone.setText(mUser.getPhoneNum());
                et_address.setText(mUser.getAddress());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        ccp.setDefaultCountryUsingNameCode("(MY)");
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                initial = ccp.getSelectedCountryCode();
            }
        });


        btn_editSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = et_name.getText().toString();
                final String phone = et_phone.getText().toString();
                final String email = et_email.getText().toString();
                final String address = et_address.getText().toString();
                final String oldPassword = et_oldPassword.getText().toString();
                final String newPassword = et_newPassword.getText().toString();
                final String confirmPassword = et_confirmPassword.getText().toString();

                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty() || !newPassword.equals(confirmPassword)) {
                        Toast.makeText(getApplicationContext(), "some fields are empty or mismatch", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                        FirebaseAuth.getInstance().getCurrentUser().updateProfile(changeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    FirebaseAuth.getInstance().getCurrentUser().updateEmail(email);
                                    userRef.update("name", name,"phonenum", phone, "email", email, "address", address).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Profile successfully updated", Toast.LENGTH_SHORT).show();
                                                finish();
                                                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                                                mainActivity.putExtra("fragment", "profile");
                                                startActivity(mainActivity);
                                                finish();
                                            }
                                            else {
                                                Toast.makeText(getApplicationContext(), "Failed to update db", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Failed to update FirebaseUser name", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        if (!oldPassword.isEmpty()) {
                            AuthCredential credential = EmailAuthProvider.getCredential(email, oldPassword);
                            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        if (!newPassword.isEmpty() && !confirmPassword.isEmpty()){
                                            if (newPassword.equals(confirmPassword)) {
                                                FirebaseAuth.getInstance().getCurrentUser().updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(getApplicationContext(), "Password successfully updated", Toast.LENGTH_SHORT).show();
                                                        }
                                                        else {
                                                            Toast.makeText(getApplicationContext(), "Password was not updated", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    }
                                }
                            });
                        }
                    }
                }


            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mainActivity = new Intent(this, MainActivity.class);
        mainActivity.putExtra("fragment", "profile");
        startActivity(mainActivity);
        finish();
    }
}
