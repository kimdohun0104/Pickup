package com.example.dsm2018.pickup.model;

public class PartyCreateRequest {
    String user_authorization;
    String party_title;
    String party_context;
    String party_year;
    String party_month;
    String party_day;
    String party_hour;
    String party_minute;
    String party_peoplenum;

    public PartyCreateRequest(String user_authorization, String party_title, String party_context, String party_year, String party_month, String party_day, String party_hour, String party_minute, String party_peoplenum) {
        this.user_authorization = user_authorization;
        this.party_title = party_title;
        this.party_context = party_context;
        this.party_year = party_year;
        this.party_month = party_month;
        this.party_day = party_day;
        this.party_hour = party_hour;
        this.party_minute = party_minute;
        this.party_peoplenum = party_peoplenum;
    }
}
