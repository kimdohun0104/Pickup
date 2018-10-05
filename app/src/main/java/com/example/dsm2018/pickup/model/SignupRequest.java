package com.example.dsm2018.pickup.model;

public class SignupRequest {
    String user_key;
    String user_name;
    String user_phone;
    String user_email;

    public SignupRequest(String user_key, String user_name, String user_phone, String user_email) {
        this.user_key = user_key;
        this.user_name = user_name;
        this.user_phone = user_phone;
        this.user_email = user_email;
    }
}
