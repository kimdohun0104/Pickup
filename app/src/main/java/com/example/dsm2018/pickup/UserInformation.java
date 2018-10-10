package com.example.dsm2018.pickup;

public class UserInformation {
    private UserInformation() { }

    private static UserInformation userInformation = new UserInformation();

    public static UserInformation getInstance() {
        return userInformation;
    }

    public String user_authorization;
}
