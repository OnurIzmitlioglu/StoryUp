package com.onurizmitlioglu.storyup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonSignIn;
    private EditText editTextMail;
    private EditText editTextPassword;
    private TextView textViewSignUp;
    private ProgressDialog mProgressDialog;

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();

        if(mFirebaseAuth.getCurrentUser() != null){
            //Start explore activity immediately.
            Intent exploreActivity = new Intent(LoginActivity.this, ExploreActivity.class);
            startActivity(exploreActivity);
        }

        mProgressDialog = new ProgressDialog(this);

        buttonSignIn = (Button) findViewById(R.id.btn_sign_in);
        editTextMail = (EditText) findViewById(R.id.et_email);
        editTextPassword = (EditText) findViewById(R.id.et_password);
        textViewSignUp = (TextView) findViewById(R.id.tv_sign_up);

        buttonSignIn.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);
    }

    private void signInUser(){
        String email = editTextMail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter your mail", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show a progressbar first.
        mProgressDialog.setMessage("Registering the user...");
        mProgressDialog.show();

        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mProgressDialog.dismiss();
                if(task.isSuccessful()){
                    finish();
                    Intent exploreActivity = new Intent(LoginActivity.this, ExploreActivity.class);
                    startActivity(exploreActivity);
                }
            }
        });

    }


    @Override
    public void onClick(View view) {
        if(view == buttonSignIn){
            signInUser();
        }
        else if(view == textViewSignUp){
            //Onboard Activity
            finish();
            Intent signUpIntent = new Intent(LoginActivity.this, OnboardActivity.class);
            startActivity(signUpIntent);
        }

    }
}
