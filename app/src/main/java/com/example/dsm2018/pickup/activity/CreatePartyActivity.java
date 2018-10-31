package com.example.dsm2018.pickup.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePartyActivity extends AppCompatActivity implements OnMapReadyCallback {

    SharedPreferences sharedPreferences;
    RetrofitService retrofitService;

    TextView startingPointNameText, endPointNameText, setDateButton, setTimeButton, numberOfPeopleText, partyMoneyText;
    EditText titleEdit, contentEdit;
    Button addPersonnelButton, reductionPersonnelButton, backButton, createPartyButton;
    Bitmap startingPointPin, endPointPin;
    LatLng startingPointPosition, endPointPosition, centerPointPosition;

    String partyMoney;

    boolean isTitle = false,  isContent = false, isDate = false, isTime = false, isPersonal = false;

    private GoogleMap mMap;

    int personnelCount = 0;
    String party_year, party_month, party_day, party_hour, party_minute, party_departure_name, party_destination_name;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 102) {
            party_year = data.getExtras().getString("party_year");
            party_month = data.getExtras().getString("party_month");
            party_day = data.getExtras().getString("party_day");

            setDateButton.setBackgroundResource(R.drawable.round_layout_side_orange_grey);
            setDateButton.setTextColor(getResources().getColor(R.color.colorPrimary));
            isDate = true;
            checkButton();
        } else if (resultCode == 103) {
            party_hour = data.getExtras().getString("party_hour");
            party_minute = data.getExtras().getString("party_minute");

            setTimeButton.setBackgroundResource(R.drawable.round_layout_side_orange_grey);
            setTimeButton.setTextColor(getResources().getColor(R.color.colorPrimary));
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
        partyMoneyText = findViewById(R.id.partyMoneyText);

        Intent intent = getIntent();
        party_departure_name = intent.getExtras().getString("startingPointName");
        startingPointNameText.setText(party_departure_name);
        party_destination_name = intent.getExtras().getString("endPointName");
        endPointNameText.setText(party_destination_name);
        startingPointPosition = new LatLng(intent.getExtras().getDouble("startingPointLatitude"), intent.getExtras().getDouble("startingPointLongitude"));
        endPointPosition = new LatLng(intent.getExtras().getDouble("endPointLatitude"), intent.getExtras().getDouble("endPointLongitude"));
        partyMoneyText.setText(intent.getExtras().getString("partyMoney") + "원");
        partyMoney = intent.getExtras().getString("partyMoney");

        centerPointPosition = new LatLng((startingPointPosition.latitude + endPointPosition.latitude) / 2.0, (startingPointPosition.longitude + endPointPosition.longitude) / 2.0);

        Log.d("DEBUGLOG", "startingPoint: " + startingPointPosition.latitude + ", " + startingPointPosition.longitude);
        Log.d("DEBUGLOG", "endPoint: " + endPointPosition.latitude + ", " + endPointPosition.longitude);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        createPartyButton.setEnabled(false);

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
                    createPartyButton.setBackgroundColor(getResources().getColor(R.color.colorGrey_3));
                } else if(!(contentEdit.getText().toString().equals(""))){
                    contentEdit.setBackgroundResource(R.drawable.round_layout_side_orange);
                    isContent = true;
                    checkButton();
                }
            }
        });

        setDateButton.setOnClickListener(v -> startActivityForResult(new Intent(getApplicationContext(), SearchDateDialog.class), 102));

        setTimeButton.setOnClickListener(v-> startActivityForResult(new Intent(getApplicationContext(), SearchTimeDialog.class), 103));

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
            Log.d("DEBUGLOG", "createPartyButton 클릭");
            HashMap<String, String> map = new HashMap() {{
                put("user_authorization", sharedPreferences.getString("user_authorization", ""));
                put("party_title", titleEdit.getText().toString());
                put("party_context", contentEdit.getText().toString());
                put("party_year", party_year);
                put("party_month", party_month);
                put("party_day", party_day);
                put("party_hour", party_hour);
                put("party_minute", party_minute);
                put("party_peoplenum", String.valueOf(personnelCount));
                put("party_departure_name", party_departure_name);
                put("party_departure_lat", String.valueOf(startingPointPosition.latitude));
                put("party_departure_lng", String.valueOf(startingPointPosition.longitude));
                put("party_destination_name", party_destination_name);
                put("party_destination_lat", String.valueOf(endPointPosition.latitude));
                put("party_destination_lng", String.valueOf(endPointPosition.longitude));
                put("party_money", partyMoney);
            }};

            Call<Void> call = retrofitService.partyCreate(map);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.code() == 200) {
                        finish();
                    }
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
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerPointPosition, 16));
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
}
