package com.example.dsm2018.pickup.model;

public class ModifyProfileRequest {
    String user_authorization;
    String user_profile;

    public ModifyProfileRequest(String user_authorization, String user_profile) {
        this.user_authorization = user_authorization;
        this.user_profile = user_profile;
    }
}