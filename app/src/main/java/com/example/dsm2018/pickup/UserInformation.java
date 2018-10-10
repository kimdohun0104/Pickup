package com.example.dsm2018.pickup;

public class UserInformation {
    private UserInformation() {}

    private static UserInformation userInformation = new UserInformation();

    public static UserInformation getInstance() {
        return userInformation;
    }

    public String user_name;
    public String user_profile;
    public String user_phone;
    public String user_email;
}
