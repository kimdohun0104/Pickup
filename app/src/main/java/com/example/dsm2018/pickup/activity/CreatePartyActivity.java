package com.example.dsm2018.pickup.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.RetrofitHelp;
import com.example.dsm2018.pickup.RetrofitService;
import com.example.dsm2018.pickup.dialog.SearchDateDialog;
import com.example.dsm2018.pickup.dialog.SearchTimeDialog;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePartyActivity extends AppCompatActivity implements OnMapReadyCallback {

    SharedPreferences sharedPreferences;
    RetrofitService retrofitService;

    TextView startingPointNameText, endPointNameText, setDateButton, setTimeButton, numberOfPeopleText;
    EditText titleEdit, contentEdit;
    Button addPersonnelButton, reductionPersonnelButton, backButton, createPartyButton;
    Bitmap startingPointPin, endPointPin;
    LatLng startingPointPosition, endPointPosition;

    boolean isTitle = false,  isContent = false, isDate = false, isTime = false, isPersonal = false;

    private GoogleMap mMap;

    int personnelCount = 0;
    String party_year, party_month, party_day, party_hour, party_minute, party_departure_name, party_destination_name;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 102) {
            party_year = data.getExtras().getString("party_year");
            party_month = data.getExtras().getString("party_month");
            party_day = data.getExtras().getString("party_day");

            setDateButton.setBackgroundResource(R.drawable.round_layout_side_orange);
            isDate = true;
            checkButton();
        } else if (requestCode == 103) {
            party_hour = data.getExtras().getString("party_hour");
            party_minute = data.getExtras().getString("party_minute");

            setDateButton.setBackgroundResource(R.drawable.round_layout_side_orange);
            isTime = true;
            checkButton();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_party);

        sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);
        retrofitService = new RetrofitHelp().retrofitService;

        startingPointNameText = findViewById(R.id.startingPointNameText);
        numberOfPeopleText = findViewById(R.id.numberOfPeopleText);
        addPersonnelButton = findViewById(R.id.addPersonnelButton);
        reductionPersonnelButton = findViewById(R.id.reductionPersonnelButton);
        titleEdit = findViewById(R.id.titleEdit);
        contentEdit = findViewById(R.id.contentEdit);
        setDateButton = findViewById(R.id.setDateButton);
        setTimeButton = findViewById(R.id.setTimeButton);
        reductionPersonnelButton = findViewById(R.id.reductionPersonnelButton);
        backButton = findViewById(R.id.backButton);
        endPointNameText = findViewById(R.id.endPointNameText);
        createPartyButton = findViewById(R.id.createPartyButton);

        Intent intent = getIntent();
        party_departure_name = intent.getExtras().getString("startingPointName");
        startingPointNameText.setText(party_departure_name);
        party_destination_name = intent.getExtras().getString("endPointName");
        endPointNameText.setText(party_departure_name);
        startingPointPosition = new LatLng(intent.getExtras().getDouble("startingPointLatitude"), intent.getExtras().getDouble("startingPointLongitude"));
        endPointPosition = new LatLng(intent.getExtras().getDouble("endPointLatitude"), intent.getExtras().getDouble("endPointLongitude"));

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        titleEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(titleEdit.getText().toString().equals("")) {
                    titleEdit.setBackgroundResource(R.drawable.round_layout_side_grey);
                    isTitle = false;
                    createPartyButton.setEnabled(false);
                    createPartyButton.setBackgroundColor(getResources().getColor(R.color.colorGrey_3));
                } else if(!(titleEdit.getText().toString().equals(""))){
                    titleEdit.setBackgroundResource(R.drawable.round_layout_side_orange);
                    isTitle = true;
                    checkButton();
                }
            }
        });

        contentEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(contentEdit.getText().toString().equals("")) {
                    contentEdit.setBackgroundResource(R.drawable.round_layout_side_grey);
                    isContent = false;
                    createPartyButton.setEnabled(false);
                    createPartyButton.setBackgroundColor(getResources().getColor(R.color.colorGrey_3));
                } else if(!(contentEdit.getText().toString().equals(""))){
                    contentEdit.setBackgroundResource(R.drawable.round_layout_side_orange);
                    isContent = true;
                    checkButton();
                }
            }
        });

        setDateButton.setOnClickListener(v -> startActivityForResult(new Intent(getApplicationContext(), SearchDateDialog.class), 100));

        setTimeButton.setOnClickListener(v-> startActivityForResult(new Intent(getApplicationContext(), SearchTimeDialog.class), 101));

        addPersonnelButton.setOnClickListener(v->{
            if(personnelCount >= 0 && personnelCount < 4) {
                personnelCount++;
                numberOfPeopleText.setText(personnelCount + "명");
                numberOfPeopleText.setTextColor(getResources().getColor(R.color.colorTextBlack));
                numberOfPeopleText.setBackgroundResource(R.drawable.round_layout_side_orange);

                isPersonal = true;
                checkButton();
            }
        });

        reductionPersonnelButton.setOnClickListener(v->{
            if(personnelCount > 1) {
                personnelCount--;
                numberOfPeopleText.setText(personnelCount + "명");
            }
        });

        createPartyButton.setOnClickListener(v-> {
            Map<String, String> map = new HashMap();
            map.put("user_authorization", sharedPreferences.getString("user_authorization", ""));
            map.put("party_title", titleEdit.getText().toString());
            map.put("party_content", contentEdit.getText().toString());
            map.put("party_year", party_year);
            map.put("party_month", party_month);
            map.put("party_day", party_day);
            map.put("party_hour", party_hour);
            map.put("party_minute", party_minute);
            map.put("party_peoplenum", String.valueOf(personnelCount));
            map.put("party_departure_name", party_departure_name);
            map.put("party_departure_lat", String.valueOf(startingPointPosition.latitude));
            map.put("party_departure_lng", String.valueOf(startingPointPosition.longitude));
            map.put("party_destination_name", party_destination_name);
            map.put("party_destination_lat", String.valueOf(endPointPosition.latitude));
            map.put("party_destination_lng", String.valueOf(endPointPosition.longitude));

            Call<Void> call = retrofitService.partyCreate(map);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        });

        backButton.setOnClickListener(v->finish());
    }

    private void checkButton() {
        if(isTime && isTitle && isContent && isPersonal && isDate) {
            createPartyButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            createPartyButton.setEnabled(true);
        } else {
            createPartyButton.setBackgroundColor(getResources().getColor(R.color.colorGrey_3));
            createPartyButton.setEnabled(false);
        }
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
