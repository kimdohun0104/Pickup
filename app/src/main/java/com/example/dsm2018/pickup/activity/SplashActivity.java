package com.example.dsm2018.pickup.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.RetrofitHelp;
import com.example.dsm2018.pickup.RetrofitService;
import com.example.dsm2018.pickup.UserInformation;
import com.example.dsm2018.pickup.model.SigninResponse;
import com.facebook.Profile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    RetrofitService retrofitService;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);

        new Handler().postDelayed(() -> {
            if(Profile.getCurrentProfile() != null) {
                retrofitService = new RetrofitHelp().retrofitService;

                Map<String, String> map = new HashMap();
                map.put("user_authorization", sharedPreferences.getString("user_authorization", ""));

                Call<List<SigninResponse>> call = retrofitService.signin(map);
                call.enqueue(new Callback<List<SigninResponse>>() {
                    @Override
                    public void onResponse(Call<List<SigninResponse>> call, Response<List<SigninResponse>> response) {
                        if(response.code() == 200) {
                            List<SigninResponse> signinResponse = response.body();
                            UserInformation userInformation = UserInformation.getInstance();
                            userInformation.user_email = signinResponse.get(0).user_email;
                            userInformation.user_name = signinResponse.get(0).user_name;
                            userInformation.user_phone = signinResponse.get(0).user_phone;
                            userInformation.user_profile = signinResponse.get(0).user_profile;

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SigninResponse>> call, Throwable t) {

                    }
                });
            } else {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        }, 1000);
    }
}
