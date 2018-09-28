package com.example.dsm2018.pickup.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.example.dsm2018.pickup.R;

public class LoginActivity extends AppCompatActivity {

    RelativeLayout facebookLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        facebookLoginButton = (RelativeLayout) findViewById(R.id.facebookLoginButton);
        facebookLoginButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
