package com.example.spaceprovider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.Random;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    ImageView profilePic;
    Button browse,registerBtn,cancelBtn;
    EditText cowork_name,mailid,register_mobile,address,area,city,state,register_pass;
    Uri filepath;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        profilePic = (ImageView)findViewById(R.id.profilePic);

        registerBtn = (Button) findViewById(R.id.registerBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        browse = (Button) findViewById(R.id.browse);

        cowork_name = (EditText)findViewById(R.id.cowork_name);
        mailid = (EditText)findViewById(R.id.mailid);
        register_mobile = (EditText)findViewById(R.id.register_mobile);
        address = (EditText)findViewById(R.id.address);
        area = (EditText)findViewById(R.id.area);
        city = (EditText)findViewById(R.id.city);
        state = (EditText)findViewById(R.id.state);
        register_pass = (EditText)findViewById(R.id.register_pass);

        browse.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancelBtn:
                startActivity(new Intent(this,MainActivity.class));
                finish();
                break;
            case R.id.browse:
                uploadPic();
                break;
            case R.id.registerBtn:
                registerSpaceProviderUser();
                break;
        }
    }

    private void uploadPic() {
        Dexter.withActivity(RegisterActivity.this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Intent intent=new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent,"Select Image File"),1);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if(requestCode==1  && resultCode==RESULT_OK)
        {
            filepath=data.getData();
            try{
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                profilePic.setImageBitmap(bitmap);
            }catch (Exception ex)
            {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void registerSpaceProviderUser() {

        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setTitle("File Uploader ");
        dialog.show();

        String name = cowork_name.getText().toString().trim();
        String mail = mailid.getText().toString().trim();
        String phoneno = register_mobile.getText().toString().trim();
        String addres = address.getText().toString().trim();
        String a = area.getText().toString().trim();
        String cityname = city.getText().toString().trim();
        String st = state.getText().toString().trim();
        String pass = register_pass.getText().toString().trim();

        if(name.isEmpty()){
            cowork_name.setError("Cowork name is required");
            cowork_name.requestFocus();
            return;
        }
        if(mail.isEmpty()){
            mailid.setError("Mailid is required");
            mailid.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            mailid.setError("Please provide valid email!");
            mailid.requestFocus();
            return;
        }
        if(phoneno.isEmpty()){
            register_mobile.setError("Phone number is required");
            register_mobile.requestFocus();
            return;
        }
        if(phoneno.length() != 10){
            register_mobile.setError("Invalid Phone number");
            register_mobile.requestFocus();
            return;
        }
        if(addres.isEmpty()){
            address.setError("Address is required");
            address.requestFocus();
            return;
        }
        if(a.isEmpty()){
            area.setError("Area is required");
            area.requestFocus();
            return;
        }
        if(cityname.isEmpty()){
            city.setError("City is required");
            city.requestFocus();
            return;
        }
        if(st.isEmpty()){
            state.setError("State is required");
            state.requestFocus();
            return;
        }
        if(pass.isEmpty()){
            register_pass.setError("Password is required");
            register_pass.requestFocus();
            return;
        }
        if(pass.length() < 6){
            register_pass.setError("Min password length should be 6 characters!");
            register_pass.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(mail,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this,"Mail registerd",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(RegisterActivity.this,"Failed to register!",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });

        FirebaseStorage storage=FirebaseStorage.getInstance();
        final StorageReference uploader=storage.getReference("Image1"+new Random().nextInt(60));

        uploader.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Image Uploaded", Toast.LENGTH_SHORT).show();

                                SpaceProviderUser user = new SpaceProviderUser(name,mail,phoneno,addres,a,cityname,st,uri.toString());

                                FirebaseDatabase.getInstance().getReference("sproviders")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(RegisterActivity.this,"User registered successfully",Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(RegisterActivity.this,DashboardActivity.class));
                                            finish();
                                        }else{
                                            Toast.makeText(RegisterActivity.this,"Failed to register!",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                            }
                        });
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float percent=(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        dialog.setMessage("Uploaded :"+(int)percent+" %");
                    }
                });


    }
}