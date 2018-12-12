package com.onurizmitlioglu.storyup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigInteger;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mFirebaseAuth;

    private TextView textViewUserMail;
    private Button buttonLogOut;

    private DatabaseReference databaseReference;
    private EditText editName, editBio;
    private Button buttonSaveInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mFirebaseAuth = FirebaseAuth.getInstance();
        if(mFirebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser user = mFirebaseAuth.getCurrentUser();

        textViewUserMail = (TextView) findViewById(R.id.tv_user_mail);

        textViewUserMail.setText("Welcome " + user.getEmail());

        databaseReference = FirebaseDatabase.getInstance().getReference();
        editName = (EditText) findViewById(R.id.et_name);
        editBio = (EditText) findViewById(R.id.et_bio);

        buttonSaveInfo = (Button) findViewById(R.id.btn_save_info);
        buttonSaveInfo.setOnClickListener(this);

        buttonLogOut = (Button) findViewById(R.id.btn_logout);
        buttonLogOut.setOnClickListener(this);

    }

    private void saveUserInformation(){
        String name = editName.getText().toString().trim();
        String bio = editBio.getText().toString().trim();

        UserInformation userInformation = new UserInformation(name,bio);
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(userInformation);

        Toast.makeText(this, "Profile information updated.", Toast.LENGTH_SHORT).show();


    }
    @Override
    public void onClick(View view) {
        if(view == buttonLogOut){
            mFirebaseAuth.signOut();
            finish();
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
        }
        if(view == buttonSaveInfo){
            saveUserInformation();
        }
    }
}
