package com.example.dsm2018.pickup.model;

public class PartySearchTextRequest {
    String user_authorization;
    String search_text;
    String filter_departure_lat;
    String filter_departure_lng;
    String filter_destination_lat;
    String filter_destination_lng;
    String filter_date_year;
    String filter_date_month;
    String filter_date_day;
    String filter_date_hour;
    String filter_date_minute;

    public PartySearchTextRequest(String user_authorization, String search_text, String filter_departure_lat, String filter_departure_lng, String filter_destination_lat, String filter_destination_lng, String filter_date_year, String filter_date_month, String filter_date_day, String filter_date_hour, String filter_date_minute) {
        this.user_authorization = user_authorization;
        this.search_text = search_text;
        this.filter_departure_lat = filter_departure_lat;
        this.filter_departure_lng = filter_departure_lng;
        this.filter_destination_lat = filter_destination_lat;
        this.filter_destination_lng = filter_destination_lng;
        this.filter_date_year = filter_date_year;
        this.filter_date_month = filter_date_month;
        this.filter_date_day = filter_date_day;
        this.filter_date_hour = filter_date_hour;
        this.filter_date_minute = filter_date_minute;
    }
}
