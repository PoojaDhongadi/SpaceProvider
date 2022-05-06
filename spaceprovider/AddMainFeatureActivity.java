package com.example.spaceprovider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddMainFeatureActivity extends AppCompatActivity implements View.OnClickListener {

    EditText name,seats,day_pass,monthly_pass;
    Button addBtn,btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_main_feature);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = (EditText) findViewById(R.id.name);
        seats = (EditText) findViewById(R.id.seats);
        day_pass = (EditText) findViewById(R.id.day_pass);
        monthly_pass = (EditText) findViewById(R.id.monthly_pass);

        addBtn = (Button) findViewById(R.id.addBtn);
        btnBack = (Button) findViewById(R.id.btnBack);

        addBtn.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addBtn:
                insertData();
                break;
            case R.id.btnBack:
                finish();
                break;
        }
    }

    private void insertData() {
        Map<String,Object> map = new HashMap<>();
        map.put("name",name.getText().toString());
        map.put("seats",seats.getText().toString());
        map.put("day_pass",day_pass.getText().toString());
        map.put("monthly_pass",monthly_pass.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("CafeSpace").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddMainFeatureActivity.this,"Data Inserted Successfully",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddMainFeatureActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });

       name.setText("");
       seats.setText("");
       day_pass.setText("");
       monthly_pass.setText("");
    }
}