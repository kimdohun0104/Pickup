package com.example.dsm2018.pickup.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.fragment.JoinPartyDialogFragment;
import com.example.dsm2018.pickup.fragment.LogoutDialogFragment;
import com.example.dsm2018.pickup.fragment.SearchDateDialogFragment;
import com.example.dsm2018.pickup.fragment.SearchTimeDialogFragment;
import com.example.dsm2018.pickup.fragment.SettingLocationSearchDialogFragment;

public class LoginActivity extends AppCompatActivity {

    RelativeLayout facebookLogin, googleLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        facebookLogin = (RelativeLayout) findViewById(R.id.facebookLogin);
        facebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        googleLogin = (RelativeLayout)findViewById(R.id.googleLoginButton);
        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                LogoutDialogFragment dialogFragment = new LogoutDialogFragment();
                dialogFragment.show(fm, "");
            }
        });
    }
}
