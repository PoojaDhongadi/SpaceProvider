package com.example.spaceprovider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private FirebaseAuth mAuth;
    EditText login_email,login_password;
    Button register_btn,login_btn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        register_btn = (Button) findViewById(R.id.register_btn);
        login_btn = (Button)findViewById(R.id.login_btn);

        login_email = (EditText)findViewById(R.id.login_email);
        login_password = (EditText)findViewById(R.id.login_password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        register_btn.setOnClickListener(this);
        login_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register_btn:
                startActivity(new Intent(this,RegisterActivity.class));
                finish();
                break;
            case R.id.login_btn:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email = login_email.getText().toString().trim();
        String password = login_password.getText().toString().trim();

        if(email.isEmpty()){
            login_email.setError("Email is required");
            login_email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            login_email.setError("Please enter a valid email!");
            login_email.requestFocus();
            return;
        }
        if(password.isEmpty()){
            login_password.setError("Password is required");
            login_password.requestFocus();
            return;
        }
        if(password.length() < 6){
            login_password.setError("Min password length should be 6 characters!");
            login_password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(MainActivity.this,DashboardActivity.class));
                            finish();
                            Toast.makeText(MainActivity.this,"Successfully Logged In...",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this,"Failed to login! Please check your credentials",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}