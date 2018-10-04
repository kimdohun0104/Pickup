package com.example.dsm2018.pickup.model;

public class PartySearchLocationRequest {
    String user_authorization;
    String departure_lat;
    String departure_lng;
    String departure_name;

    public PartySearchLocationRequest(String user_authorization, String departure_lat, String departure_lng, String departure_name) {
        this.user_authorization = user_authorization;
        this.departure_lat = departure_lat;
        this.departure_lng = departure_lng;
        this.departure_name = departure_name;
    }
}
