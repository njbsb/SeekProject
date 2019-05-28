package com.example.seekproject.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seekproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_register;
    private EditText et_name, et_icNum, et_email, et_password, et_confirmPassword;
    private TextView txt_toLogin;
    private ProgressBar progressLogin;
    private FirebaseFirestore db;
    private FirebaseUser firebaseUser;
    private FirebaseAuth mAuth;

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btn_register = findViewById(R.id.btn_register_register);
        et_name = findViewById(R.id.et_register_name);
        et_icNum = findViewById(R.id.et_register_icNumber);
        et_email = findViewById(R.id.et_register_email);
        et_password = findViewById(R.id.et_register_password);
        et_confirmPassword = findViewById(R.id.et_register_confirm_password);
        txt_toLogin = findViewById(R.id.txt_register_toLogin);
        progressLogin = findViewById(R.id.progress_register);
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressLogin.setVisibility(View.INVISIBLE);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_register.setVisibility(View.INVISIBLE);
                txt_toLogin.setVisibility(View.INVISIBLE);
                progressLogin.setVisibility(View.VISIBLE);

                final String name = et_name.getText().toString();
                final String icnum = et_icNum.getText().toString();
                final String email = et_email.getText().toString();
                final String password = et_password.getText().toString();
                final String confirmPassword = et_confirmPassword.getText().toString();

                HashMap<String, Object> userMap = new HashMap<>();

                if (name.isEmpty() || icnum.isEmpty() || email.isEmpty() || password.isEmpty() || !password.equals(confirmPassword)) {

                    Toast.makeText(getApplicationContext(), "some fields are empty or passwords mismatch", Toast.LENGTH_SHORT).show();

                }
                else {
                    try {
                        userMap.put("name", name);
                        userMap.put("icnum", icnum);
                        userMap.put("email", email);
                        userMap.put("phonenum", null);
                        userMap.put("address", null);

                        createUserAccount(name, email, password, userMap);
                    }
                    catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void createUserAccount(final String name, String email, String password, final HashMap<String, Object> userMap) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "account registered and created", Toast.LENGTH_SHORT).show();

                    UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name).build();

                    mAuth.getInstance().getCurrentUser().updateProfile(userProfileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(mainActivity);
                                finish();
                                Toast.makeText(getApplicationContext(), "Welcome to Seek", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    db.collection("users").document(mAuth.getCurrentUser().getUid()).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "user is saved in db", Toast.LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(getApplicationContext(), "fail to write to db", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "registration failed", Toast.LENGTH_SHORT).show();
                    btn_register.setVisibility(View.VISIBLE);
                    txt_toLogin.setVisibility(View.VISIBLE);
                    progressLogin.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
