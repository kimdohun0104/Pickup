package com.example.dsm2018.pickup.model;

public class ModifyNameRequest {
    String user_authorization;
    String user_name;

    public ModifyNameRequest(String user_authorization, String user_name) {
        this.user_authorization = user_authorization;
        this.user_name = user_name;
    }
}
