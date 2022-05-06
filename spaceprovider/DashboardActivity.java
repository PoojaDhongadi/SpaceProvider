package com.example.spaceprovider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    CardView cardProfile,cardLogout,cardFeatures;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mAuth = FirebaseAuth.getInstance();

        cardProfile = (CardView) findViewById(R.id.cardProfile);
        cardFeatures = (CardView) findViewById(R.id.cardFeatures);
        cardLogout = (CardView) findViewById(R.id.cardLogout);

        cardProfile.setOnClickListener(this);
        cardFeatures.setOnClickListener(this);
        cardLogout.setOnClickListener(this);


    }

    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cardProfile:
                startActivity(new Intent(DashboardActivity.this,ProfileActivity.class));
                finish();
                //showToast("Profile Clicked");
                break;
            case R.id.cardLogout:
                mAuth.signOut();
                startActivity(new Intent(DashboardActivity.this,MainActivity.class));
                finish();
                showToast("Logged Out...");
                break;
            case R.id.cardFeatures:
                startActivity(new Intent(DashboardActivity.this,MainFeaturesActivity.class));
                //finish();
                break;
        }
    }
}