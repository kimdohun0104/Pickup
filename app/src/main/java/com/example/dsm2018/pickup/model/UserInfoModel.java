package com.example.dsm2018.pickup.model;

public class UserInfoModel {
    private final String email;
    private final String id;
    private final String name;
    private final String phone;
    private final String photo;

    public UserInfoModel(String email, String id, String name, String phone, String photo) {
        this.email = email;
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.photo = photo;
    }
}
