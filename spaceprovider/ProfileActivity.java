package com.example.spaceprovider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;
    ImageView pimg;
    TextView profile_cname,profile_mail,profile_num,profile_address,profile_area,profile_city,profile_state;
    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        pimg = (ImageView)findViewById(R.id.pimg);
        profile_cname = (TextView)findViewById(R.id.profile_cname);
        profile_mail = (TextView)findViewById(R.id.profile_mail);
        profile_num = (TextView)findViewById(R.id.profile_num);
        profile_address = (TextView)findViewById(R.id.profile_address);
        profile_area = (TextView)findViewById(R.id.profile_area);
        profile_city = (TextView)findViewById(R.id.profile_city);
        profile_state = (TextView)findViewById(R.id.profile_state);
        backBtn = (Button) findViewById(R.id.backBtn);

        backBtn.setOnClickListener(this);

        //this points to the authentication section in firebase console
        user = mAuth.getInstance().getCurrentUser();
        userId = user.getUid(); //this points to long userid
        reference = FirebaseDatabase.getInstance().getReference("sproviders");

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SpaceProviderUser userProfile = snapshot.getValue(SpaceProviderUser.class);

                if(userProfile != null){
                    String name = userProfile.name;
                    String mailid = userProfile.mailid;
                    String mobile = userProfile.mobile;
                    String address = userProfile.address;
                    String area = userProfile.area;
                    String city = userProfile.city;
                    String state = userProfile.state;
                    String profileImg = userProfile.profileImg;

                    profile_cname.setText(name);
                    profile_mail.setText(mailid);
                    profile_num.setText(mobile);
                    profile_address.setText(address);
                    profile_area.setText(area);
                    profile_city.setText(city);
                    profile_state.setText(state);

                    Picasso.get().load(profileImg).into(pimg);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backBtn:
                startActivity(new Intent(ProfileActivity.this,DashboardActivity.class));
                finish();
                break;
        }
    }
}