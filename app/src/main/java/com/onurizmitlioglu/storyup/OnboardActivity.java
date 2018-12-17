package com.onurizmitlioglu.storyup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

public class OnboardActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private EditText editTextMail;
    private EditText editTextPassword;
    private TextView textViewSignIn;
    private ProgressDialog mProgressDialog;
    private LoginButton fbLoginButton;

    private FirebaseAuth mFirebaseAuth;
    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();
        mProgressDialog = new ProgressDialog(this);

        buttonRegister = (Button) findViewById(R.id.btn_register);
        editTextMail = (EditText) findViewById(R.id.et_email);
        editTextPassword = (EditText) findViewById(R.id.et_password);
        textViewSignIn = (TextView) findViewById(R.id.tv_sign_in);
        fbLoginButton = (LoginButton) findViewById(R.id.fb_login_button);

        buttonRegister.setOnClickListener(this);
        textViewSignIn.setOnClickListener(this);

        fbLoginButton.setOnClickListener(this);
    }

    private void registerUser(){
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
        mFirebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            finish();
                            Intent exploreActivity = new Intent(OnboardActivity.this, ExploreActivity.class);
                            startActivity(exploreActivity);
                        }
                        else{
                            Toast.makeText(OnboardActivity.this, "Authentication failed, try again later.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void fbLogin(){
        fbLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFBAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void handleFBAccessToken(AccessToken accessToken){
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mFirebaseAuth.signInWithCredential(credential).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(OnboardActivity.this, "" + e.getMessage(),Toast.LENGTH_SHORT).show();
                Log.e("ERROR_EDMT", e.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                String email = authResult.getUser().getEmail();
                Toast.makeText(OnboardActivity.this, "Signed on Facebook via e-mail: "+email, Toast.LENGTH_SHORT).show();
                Intent loginFBIntent = new Intent(OnboardActivity.this, ExploreActivity.class);
                startActivity(loginFBIntent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        mCallbackManager.onActivityResult(requestCode,resultCode,data);
    }


    @Override
    public void onClick(View view) {
        if(view == buttonRegister){
            registerUser();
        }
        else if(view == textViewSignIn){
            //Login Activity
            finish();
            Intent loginActivity = new Intent(OnboardActivity.this, LoginActivity.class);
            startActivity(loginActivity);
        }
        else if(view == fbLoginButton){
            fbLogin();
        }

    }
}
