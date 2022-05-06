package com.example.spaceprovider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainFeaturesActivity extends AppCompatActivity {

    /*private FirebaseAuth mAuth;
    private FirebaseUser user;
    String userId;*/

    RecyclerView recyclerView;
    MainFeatureAdapter mainFeatureAdapter;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_features);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.mainRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //user = mAuth.getInstance().getCurrentUser();
        //userId = user.getUid(); //this points to long userid

        FirebaseRecyclerOptions<MainFeatureModel> options =
                new FirebaseRecyclerOptions.Builder<MainFeatureModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("CafeSpace"), MainFeatureModel.class)
                        .build();

        mainFeatureAdapter = new MainFeatureAdapter(options);
        recyclerView.setAdapter(mainFeatureAdapter);

        floatingActionButton = (FloatingActionButton)findViewById(R.id.addBtn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddMainFeatureActivity.class));
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        mainFeatureAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        mainFeatureAdapter.stopListening();
    }
}