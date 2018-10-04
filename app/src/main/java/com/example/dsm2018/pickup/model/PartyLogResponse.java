package com.example.dsm2018.pickup.model;

public class PartyLogResponse {
    String party_key;
    String party_title;
    String party_status;
    String party_year;
    String party_month;
    String party_day;

    public PartyLogResponse(String party_key, String party_title, String party_status, String party_year, String party_month, String party_day) {
        this.party_key = party_key;
        this.party_title = party_title;
        this.party_status = party_status;
        this.party_year = party_year;
        this.party_month = party_month;
        this.party_day = party_day;
    }
}
