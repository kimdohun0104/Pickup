package com.example.dsm2018.pickup.model;

public class ModifyEmailRequest {
    String user_authorization;
    String user_email;

    public ModifyEmailRequest(String user_authorization, String user_email) {
        this.user_authorization = user_authorization;
        this.user_email = user_email;
    }
}
