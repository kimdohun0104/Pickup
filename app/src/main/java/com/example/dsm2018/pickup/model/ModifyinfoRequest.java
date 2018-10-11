package com.example.dsm2018.pickup.model;

public class ModifyinfoRequest {
    String user_authorization;
    String modify_value;
    String modify_info_type;

    public ModifyinfoRequest(String user_authorization, String modify_value, String modify_info_type) {
        this.user_authorization = user_authorization;
        this.modify_value = modify_value;
        this.modify_info_type = modify_info_type;
    }
}
