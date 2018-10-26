package com.example.dsm2018.pickup.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.adapter.PartyMemberListAdapter;
import com.example.dsm2018.pickup.model.PartyDetailResponse;
import com.example.dsm2018.pickup.model.PartyMemberModel;
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
    TextView departureNameText, destinationNameText, partyTitle, partyContent, partyDate, partyTime, partyPeoplenum;
    RecyclerView recyclerView;
    Button joinPartyButton;

    PartyDetailResponse partyDetailResponse;

    GoogleMap mMap;

    LatLng startingPointPosition, endPointPosition;
    Bitmap startingPointPin, endPointPin;

    LinearLayoutManager layoutManager;
    PartyMemberListAdapter listAdapter;
    ArrayList<PartyMemberModel> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_detail);

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
        data = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);

        departureNameText = findViewById(R.id.departureNameText);
        destinationNameText = findViewById(R.id.destinationNameText);
        partyTitle = findViewById(R.id.partyTitle);
        partyContent = findViewById(R.id.partyContent);
        partyDate = findViewById(R.id.partyDate);
        partyTime = findViewById(R.id.partyTime);
        partyPeoplenum = findViewById(R.id.partyPeopleNum);
        recyclerView = findViewById(R.id.recyclerView);
        joinPartyButton = findViewById(R.id.joinPartyButton);

        Intent intent = getIntent();
        partyDetailResponse = (PartyDetailResponse) intent.getSerializableExtra("partyDetailResponse");

        startingPointPosition = new LatLng(Double.parseDouble(partyDetailResponse.party_departure_lat), Double.parseDouble(partyDetailResponse.party_departure_lng));
        endPointPosition = new LatLng(Double.parseDouble(partyDetailResponse.party_destination_lat), Double.parseDouble(partyDetailResponse.party_destination_lng));

        departureNameText.setText(partyDetailResponse.party_departure_name);
        destinationNameText.setText(partyDetailResponse.party_destination_name);
        partyTitle.setText(partyDetailResponse.party_title);
        partyContent.setText(partyDetailResponse.party_context);
        partyDate.setText(partyDetailResponse.party_year + "년 " + partyDetailResponse.party_month + "월 " + partyDetailResponse.party_day + "일");
        partyTime.setText("PM " + partyDetailResponse.party_hour + "시 " + partyDetailResponse.party_minute + "분");
        partyPeoplenum.setText(partyDetailResponse.party_currnum + "명 / " + partyDetailResponse.party_peoplenum + "명");
        
        data.addAll(partyDetailResponse.party_member_list);
        recyclerView.setLayoutManager(layoutManager);
        listAdapter = new PartyMemberListAdapter(data);
        recyclerView.setAdapter(listAdapter);
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
        zoom();
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
}
