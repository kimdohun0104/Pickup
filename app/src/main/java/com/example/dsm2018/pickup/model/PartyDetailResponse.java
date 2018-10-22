package com.example.dsm2018.pickup.model;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class PartyDetailResponse implements Serializable {
    public String party_departure_lat;
    public String party_departure_lng;
    public String party_departure_name;
    public String party_destination_lat;
    public String party_destination_lng;
    public String party_destination_name;
    public String party_money;
    public String party_title;
    public String party_context;
    public String party_year;
    public String party_month;
    public String party_day;
    public String party_hour;
    public String party_minute;
    public String party_peoplenum;
    public String party_currnum;
    public String party_boss;
    public List<PartyMember> party_member_list;
}

class PartyMember {
    String user_name;
    String user_email;
    String user_profile;
    String user_phone;
}