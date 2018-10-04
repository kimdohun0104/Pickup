package com.example.dsm2018.pickup.model;

public class ModifyPhoneRequest {
    String user_authorization;
    String email;

    public ModifyPhoneRequest(String user_authorization, String email) {
        this.user_authorization = user_authorization;
        this.email = email;
    }
}
