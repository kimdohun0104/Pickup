package com.example.dsm2018.pickup.model;

public class SigninResponse {
    String user_name;
    String user_profile;
    String user_phone;
    String user_email;

    public SigninResponse(String user_name, String user_profile, String user_phone, String user_email) {
        this.user_name = user_name;
        this.user_profile = user_profile;
        this.user_phone = user_phone;
        this.user_email = user_email;
    }
}
