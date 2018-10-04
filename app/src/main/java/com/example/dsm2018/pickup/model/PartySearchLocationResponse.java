package com.example.dsm2018.pickup.model;

public class PartySearchLocationResponse {
    String party_key;
    String party_title;
    String party_year;
    String party_month;
    String party_day;
    String party_peoplenum;
    String party_curnum;

    public PartySearchLocationResponse(String party_key, String party_title, String party_year, String party_month, String party_day, String party_peoplenum, String party_curnum) {
        this.party_key = party_key;
        this.party_title = party_title;
        this.party_year = party_year;
        this.party_month = party_month;
        this.party_day = party_day;
        this.party_peoplenum = party_peoplenum;
        this.party_curnum = party_curnum;
    }
}
