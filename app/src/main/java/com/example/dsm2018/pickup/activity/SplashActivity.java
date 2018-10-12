package com.example.dsm2018.pickup.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.RetrofitHelp;
import com.example.dsm2018.pickup.RetrofitService;
import com.example.dsm2018.pickup.UserInformation;
import com.example.dsm2018.pickup.model.SigninRequest;
import com.example.dsm2018.pickup.model.SigninResponse;
import com.facebook.Profile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

                Call<SigninResponse> call = retrofitService.signin(new SigninRequest(sharedPreferences.getString("user_authorization", "")));
                call.enqueue(new Callback<SigninResponse>() {
                    @Override
                    public void onResponse(Call<SigninResponse> call, Response<SigninResponse> response) {
                        if(response.code() == 200) {
                            SigninResponse signinResponse = response.body();
                            UserInformation userInformation = UserInformation.getInstance();
                            userInformation.user_email = signinResponse.user_email;
                            userInformation.user_name = signinResponse.user_name;
                            userInformation.user_phone = signinResponse.user_phone;
                            userInformation.user_profile = signinResponse.user_profile;

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<SigninResponse> call, Throwable t) {

                    }
                });
            } else {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}
