package com.example.dsm2018.pickup.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.RelativeLayout;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.RetrofitHelp;
import com.example.dsm2018.pickup.RetrofitService;
import com.example.dsm2018.pickup.model.SignupRequest;
import com.example.dsm2018.pickup.model.SignupResponse;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    RetrofitService retrofitService;

    SharedPreferences preferences = getSharedPreferences("pref", MODE_PRIVATE);
    SharedPreferences.Editor editor = preferences.edit();

    CallbackManager callbackManager;
    RelativeLayout loginButton;

    String user_name, user_phone, user_key, user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        ActivityCompat.requestPermissions(LoginActivity.this,
                new String[]{Manifest.permission.READ_PHONE_STATE}, 101);

        callbackManager = CallbackManager.Factory.create();

        loginButton = (RelativeLayout) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> {
            LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "email"));
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    user_key = loginResult.getAccessToken().getToken();
                    user_phone = getPhone();

                    GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), (object, response) -> {
                        try { user_email = object.getString("email"); } catch (JSONException e) { user_email = ""; }
                        try{ user_name = object.getString("name"); } catch (JSONException e) { }
                    });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "name,email");
                    graphRequest.setParameters(parameters);
                    graphRequest.executeAsync();

                    retrofitService = new RetrofitHelp().retrofitService;
                    Call<SignupResponse> call = retrofitService.signup(new SignupRequest(user_key, user_name, user_phone, user_email));
                    call.enqueue(new Callback<SignupResponse>() {
                        @Override
                        public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                            if(response.code() == 200) {
                                SignupResponse signupResponse = response.body();
                                editor.putString("user_authorization", signupResponse.user_authorization);
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<SignupResponse> call, Throwable t) {

                        }
                    });

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }

                @Override
                public void onCancel() {
                    Log.d("facebookLogin", "onCancel");
                }

                @Override
                public void onError(FacebookException error) {
                    Log.d("facebookLogin", "onError");
                }
            });
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private String getPhone() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            Log.d("getPhone", "return \"\"");
            return "";
        }
        return telephonyManager.getLine1Number().replace("+82", "0");
    }
}
