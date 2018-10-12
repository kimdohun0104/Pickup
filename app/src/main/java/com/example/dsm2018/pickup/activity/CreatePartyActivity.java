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
import com.example.dsm2018.pickup.dialog.SearchDateDialog2;
import com.example.dsm2018.pickup.dialog.SearchTimeDialog;
import com.example.dsm2018.pickup.model.PartyCreateRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePartyActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static PartyCreateRequest partyCreateRequest;
    SharedPreferences sharedPreferences;
    RetrofitService retrofitService;

    TextView startingPointNameText, endPointNameText, setDateButton, setTimeButton, numberOfPeopleText;
    EditText titleEdit, contentEdit;
    Button addPersonnelButton, reductionPersonnelButton, backButton, createPartyButton;
    Bitmap startingPointPin, endPointPin;
    LatLng startingPointPosition, endPointPosition;

    int personnelCount = 0;

    boolean isTitle = false,  isContent = false, isPersonal = false;
    public static boolean isDate = false, isTime = false;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_party);

        partyCreateRequest = new PartyCreateRequest();

        sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);
        partyCreateRequest.setUser_authorization(sharedPreferences.getString("user_authorization", ""));
        retrofitService = new RetrofitHelp().retrofitService;

        startingPointNameText = (TextView)findViewById(R.id.startingPointNameText);
        numberOfPeopleText = (TextView)findViewById(R.id.numberOfPeopleText);
        addPersonnelButton = (Button)findViewById(R.id.addPersonnelButton);
        reductionPersonnelButton = (Button)findViewById(R.id.reductionPersonnelButton);
        titleEdit = (EditText)findViewById(R.id.titleEdit);
        contentEdit = (EditText)findViewById(R.id.contentEdit);
        setDateButton = (TextView)findViewById(R.id.setDateButton);
        setTimeButton = (TextView)findViewById(R.id.setTimeButton);
        reductionPersonnelButton = (Button)findViewById(R.id.reductionPersonnelButton);
        backButton = (Button)findViewById(R.id.backButton);
        endPointNameText = (TextView) findViewById(R.id.endPointNameText);
        createPartyButton = (Button) findViewById(R.id.createPartyButton);

        Intent intent = getIntent();
        startingPointNameText.setText(intent.getExtras().getString("startingPointName"));
        partyCreateRequest.setParty_departure_name(intent.getExtras().getString("startingPointName"));
        endPointNameText.setText(intent.getExtras().getString("endPointName"));
        partyCreateRequest.setParty_destination_name(intent.getExtras().getString("endPointName"));
        startingPointPosition = new LatLng(intent.getExtras().getDouble("startingPointLatitude"), intent.getExtras().getDouble("startingPointLongitude"));
        partyCreateRequest.setParty_departure_lat(String.valueOf(intent.getExtras().getDouble("startingPointLatitude")));
        partyCreateRequest.setParty_departure_lng(String.valueOf(intent.getExtras().getDouble("startingPointLongitude")));
        endPointPosition = new LatLng(intent.getExtras().getDouble("endPointLatitude"), intent.getExtras().getDouble("endPointLongitude"));
        partyCreateRequest.setParty_destination_lat(String.valueOf(intent.getExtras().getDouble("endPointLatitude")));
        partyCreateRequest.setParty_destination_lng(String.valueOf(intent.getExtras().getDouble("endPointLongitude")));


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
                } else if(!(titleEdit.getText().toString().equals(""))){
                    titleEdit.setBackgroundResource(R.drawable.round_layout_side_orange);
                    isTitle = true;
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
                } else if(!(contentEdit.getText().toString().equals(""))){
                    contentEdit.setBackgroundResource(R.drawable.round_layout_side_orange);
                    isContent = true;
                }
            }
        });

        SearchDateDialog searchDateDialog = new SearchDateDialog(CreatePartyActivity.this, CreatePartyActivity.this);
        setDateButton.setOnClickListener(v-> searchDateDialog.showDialog());
//        setDateButton.setOnClickListener(v-> startActivity(new Intent(getApplicationContext(), SearchDateDialog2.class)));

        SearchTimeDialog searchTimeDialog = new SearchTimeDialog(CreatePartyActivity.this, CreatePartyActivity.this);
        setTimeButton.setOnClickListener(v->searchTimeDialog.showDialog());

        addPersonnelButton.setOnClickListener(v->{
            if(personnelCount >= 0 && personnelCount < 4) {
                personnelCount++;
                numberOfPeopleText.setText(personnelCount + "명");
                numberOfPeopleText.setTextColor(getResources().getColor(R.color.colorTextBlack));
                numberOfPeopleText.setBackgroundResource(R.drawable.round_layout_side_orange);

                isPersonal = true;
            }
        });

        reductionPersonnelButton.setOnClickListener(v->{
            if(personnelCount > 1) {
                personnelCount--;
                numberOfPeopleText.setText(personnelCount + "명");
            }
        });

        createPartyButton.setOnClickListener(v-> {
            if(isDate && isPersonal && isContent && isTitle && isTime) {
                partyCreateRequest.setParty_title(titleEdit.getText().toString());
                partyCreateRequest.setParty_context(contentEdit.getText().toString());
                partyCreateRequest.setParty_peoplenum(String.valueOf(personnelCount));

                Call<PartyCreateRequest> call = retrofitService.partyCreate(partyCreateRequest);
                call.enqueue(new Callback<PartyCreateRequest>() {
                    @Override
                    public void onResponse(Call<PartyCreateRequest> call, Response<PartyCreateRequest> response) {

                    }

                    @Override
                    public void onFailure(Call<PartyCreateRequest> call, Throwable t) {

                    }
                });
            }


        });

        backButton.setOnClickListener(v->finish());
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
