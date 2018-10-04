package com.example.dsm2018.pickup.model;

public class PartyLocationRequest {
    String user_authorization;
    double departure_lat;
    double departure_lng;
    double destination_lat;
    double destination_lng;

    public PartyLocationRequest(String user_authorization, double departure_lat, double departure_lng, double destination_lat, double destination_lng) {
        this.user_authorization = user_authorization;
        this.departure_lat = departure_lat;
        this.departure_lng = departure_lng;
        this.destination_lat = destination_lat;
        this.destination_lng = destination_lng;
    }
}
