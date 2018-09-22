package com.example.dsm2018.pickup.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.dialog.DeleteAccountDialog;
import com.example.dsm2018.pickup.dialog.EmailDialog;
import com.example.dsm2018.pickup.dialog.PhoneNumberDialog;

public class LoginActivity extends AppCompatActivity {

    RelativeLayout facebookLogin, googleLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        facebookLogin = (RelativeLayout) findViewById(R.id.facebookLogin);
        facebookLogin.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });

        googleLogin = (RelativeLayout)findViewById(R.id.googleLoginButton);
        googleLogin.setOnClickListener(v -> {

        });
    }
}
