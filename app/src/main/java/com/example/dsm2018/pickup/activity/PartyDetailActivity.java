package com.example.dsm2018.pickup.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.RecyclerItemClickListener;
import com.example.dsm2018.pickup.RetrofitHelp;
import com.example.dsm2018.pickup.RetrofitService;
import com.example.dsm2018.pickup.adapter.PartyMemberListAdapter;
import com.example.dsm2018.pickup.dialog.JoinPartyDialog;
import com.example.dsm2018.pickup.dialog.PartyDeleteDialog;
import com.example.dsm2018.pickup.model.PartyDetailResponse;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class PartyDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    MapFragment mapFragment;
    TextView departureNameText, destinationNameText, partyTitle, partyContent, partyDate, partyTime, partyPeoplenum, partyMoneyText;
    RecyclerView recyclerView;
    Button joinPartyButton, backButton;

    GoogleMap mMap;

    LatLng startingPointPosition, endPointPosition;
    Bitmap startingPointPin, endPointPin;

    SharedPreferences sharedPreferences;
    RetrofitService retrofitService;

    LinearLayoutManager layoutManager;
    PartyMemberListAdapter listAdapter;
    ArrayList<PartyDetailResponse> response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_detail);

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
        layoutManager = new LinearLayoutManager(this);
        sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);
        retrofitService = new RetrofitHelp().retrofitService;

        departureNameText = findViewById(R.id.departureNameText);
        destinationNameText = findViewById(R.id.destinationNameText);
        partyTitle = findViewById(R.id.partyTitle);
        partyContent = findViewById(R.id.partyContent);
        partyDate = findViewById(R.id.partyDate);
        partyTime = findViewById(R.id.partyTime);
        partyPeoplenum = findViewById(R.id.peopleNum);
        recyclerView = findViewById(R.id.recyclerView);
        joinPartyButton = findViewById(R.id.joinPartyButton);
        partyMoneyText = findViewById(R.id.partyMoneyText);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v->finish());

        Intent intent = getIntent();
        response = (ArrayList<PartyDetailResponse>) intent.getSerializableExtra("response");

        startingPointPosition = new LatLng(Double.parseDouble(response.get(0).party_departure_lat), Double.parseDouble(response.get(0).party_departure_lng));
        endPointPosition = new LatLng(Double.parseDouble(response.get(0).party_destination_lat), Double.parseDouble(response.get(0).party_destination_lng));

        departureNameText.setText(response.get(0).party_departure_name);
        destinationNameText.setText(response.get(0).party_destination_name);
        partyTitle.setText(response.get(0).party_title);
        partyContent.setText(response.get(0).party_context);
        partyDate.setText(response.get(0).party_year + "년 " + response.get(0).party_month + "월 " + response.get(0).party_day + "일");
        int party_hour = Integer.parseInt(response.get(0).party_hour);
        if(party_hour >= 12) {
            party_hour -= 12;
            partyTime.setText("PM " + party_hour + "시 " + response.get(0).party_minute + "분");
        } else {
            partyTime.setText("AM " + party_hour + "시 " + response.get(0).party_minute + "분");
        }
        partyPeoplenum.setText(response.get(0).party_currnum + "명 / " + response.get(0).party_peoplenum + "명");
        partyMoneyText.setText(response.get(0).party_money);

        if(checkBoss() || checkMember()) {
            joinPartyButton.setText("파티해제");
            joinPartyButton.setBackgroundColor(getResources().getColor(R.color.colorRed));

            joinPartyButton.setOnClickListener(v-> new PartyDeleteDialog(this, response.get(0).party_key).showDialog());
        } else {
            joinPartyButton.setText("파티참가");
            joinPartyButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            joinPartyButton.setOnClickListener(v-> new JoinPartyDialog(this, response.get(0).party_key, response.get(0).party_currnum).showDialog());
        }

        recyclerView.setLayoutManager(layoutManager);
        listAdapter = new PartyMemberListAdapter(response);
        recyclerView.setAdapter(listAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String tel = "tel:" + response.get(position).user_phone.replaceAll("-", "");
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(tel)));
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        MarkerOptions markerOptions = new MarkerOptions();

        startingPointPin = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_start), 80, 80, false);
        markerOptions.position(startingPointPosition);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(startingPointPin));
        mMap.addMarker(markerOptions);

        endPointPin = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_arrive), 80, 80, false);
        markerOptions.position(endPointPosition);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(endPointPin));
        mMap.addMarker(markerOptions);

        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setOnMapLoadedCallback(() -> {
            zoom();
        });
    }

    private void zoom() {
        LatLngBounds.Builder zoomToFitBuilder = new LatLngBounds.Builder();
        zoomToFitBuilder.include(startingPointPosition);
        zoomToFitBuilder.include(endPointPosition);
        LatLngBounds zoomToFitBound = zoomToFitBuilder.build();
        int padding = 150;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(zoomToFitBound, padding);
        mMap.animateCamera(cameraUpdate);
    }

    private boolean checkBoss() {
        String user_authorization = sharedPreferences.getString("user_authorization", "");

        for(int i = 0; i < response.size(); i++) {
            if(user_authorization.equals(response.get(i).party_boss))
                return true;
        }

        return false;
    }

    private boolean checkMember() {
        String user_authorization = sharedPreferences.getString("user_authorization", "");

        for(int i = 0; i < response.size(); i++) {
            if(user_authorization.equals(response.get(i).party_member))
                return true;
        }

        return false;
    }
}
