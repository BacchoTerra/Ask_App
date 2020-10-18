package com.bacchoterra.askapp.authentication;

import android.app.Activity;
import android.content.Context;

import com.bacchoterra.askapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class GoogleAuth {

    private static GoogleSignInOptions gso;


    public static GoogleSignInOptions getGso(Context context) {


        if (gso == null) {

            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(context.getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

        }

        return gso;

    }

}
