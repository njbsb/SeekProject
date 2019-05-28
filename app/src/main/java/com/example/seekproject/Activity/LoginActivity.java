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
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private Button btn_login;
    private EditText et_email, et_password;
    private TextView txt_toRegister;
    private ProgressBar progressLogin;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = findViewById(R.id.btn_login_login);
        et_email = findViewById(R.id.et_login_email);
        et_password = findViewById(R.id.et_login_password);
        progressLogin = findViewById(R.id.progress_login);
        txt_toRegister = findViewById(R.id.txt_login_toRegister);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        if (mAuth.getCurrentUser() != null) {
            Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainActivity);
            finish();
        }

        progressLogin.setVisibility(View.INVISIBLE);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_login.setVisibility(View.INVISIBLE);
                progressLogin.setVisibility(View.VISIBLE);
                txt_toRegister.setVisibility(View.INVISIBLE);
                final String email = et_email.getText().toString();
                final String password = et_password.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "some fields are empty", Toast.LENGTH_SHORT).show();
                    btn_login.setVisibility(View.VISIBLE);
                    txt_toRegister.setVisibility(View.VISIBLE);
                    progressLogin.setVisibility(View.INVISIBLE);
                }
                else {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressLogin.setVisibility(View.INVISIBLE);
                                btn_login.setVisibility(View.VISIBLE);
                                txt_toRegister.setVisibility(View.VISIBLE);
                                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(mainActivity);
                                finish();
                                Toast.makeText(getApplicationContext(), "Welcome to Seek", Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressLogin.setVisibility(View.INVISIBLE);
                                btn_login.setVisibility(View.VISIBLE);
                                txt_toRegister.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
            }
        });

        txt_toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerActivity = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(registerActivity);
                finish();
            }
        });
    }
}
