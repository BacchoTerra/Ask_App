package com.bacchoterra.askapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bacchoterra.askapp.R;
import com.bacchoterra.askapp.authentication.GoogleAuth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    //Layout components
    private SignInButton btnGoogleSignIn;
    private Button btnEmailSignUp;
    private TextView txtSignIn;
    private TextView txtEnterAsAGuest;

    //Google SignIn components
    private GoogleSignInClient googleSignInClient;
    private static final int GOOGLE_SIGN_IN_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();

    }

    private void init() {
        initViews();
        initGoogleSignInClient();
    }

    private void initViews() {

        btnGoogleSignIn = findViewById(R.id.activity_welcome_btnGoogleSignIn);
        btnEmailSignUp = findViewById(R.id.activity_welcome_btnContinueWithEmail);
        txtSignIn = findViewById(R.id.activity_welcome_txtSignIn);
        txtEnterAsAGuest = findViewById(R.id.activity_welcome_txtEnterAsAGuest);

        btnGoogleSignIn.setOnClickListener(this);

    }

    private void initGoogleSignInClient() {

        googleSignInClient = GoogleSignIn.getClient(this, GoogleAuth.getGso(this));

        googleSignInClient.signOut();

    }

    private void signInWithGoogleDialog() {

        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQUEST_CODE);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.activity_welcome_btnGoogleSignIn:
                signInWithGoogleDialog();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN_REQUEST_CODE){


            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data).addOnCompleteListener(new OnCompleteListener<GoogleSignInAccount>() {
                @Override
                public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                    if (task.isSuccessful()){
                        handleSignInResult(task);
                    }
                }
            });


        }

    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {

        try {
            GoogleSignInAccount acc = task.getResult(ApiException.class);
            Toast.makeText(this, acc.getDisplayName(), Toast .LENGTH_SHORT).show();
        }catch (ApiException e) {
            e.printStackTrace();
            Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
        }

    }
}