package com.example.dsm2018.pickup.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.dialog.SearchDateDialog;
import com.example.dsm2018.pickup.dialog.SearchTimeDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CreatePartyActivity extends AppCompatActivity implements OnMapReadyCallback {

    TextView startingPointText, endPointText;
    EditText titleEdit, contentEdit;
    TextView setDateButton, setTimeButton, numberOfPeopleText;
    Button addPersonnel, reductionPersonnel, backButton;
    Bitmap startingPointPin, endPointPin;
    LatLng startingPointPosition, endPointPosition, centerPointPosition;

    int personnelCount = 0;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_party);

        startingPointText = (TextView)findViewById(R.id.startingPointText);
        endPointText = (TextView)findViewById(R.id.endPointText);
        numberOfPeopleText = (TextView)findViewById(R.id.numberOfPeopleText);
        addPersonnel = (Button)findViewById(R.id.addPersonnel);
        reductionPersonnel = (Button)findViewById(R.id.reductionPersonnel);
        titleEdit = (EditText)findViewById(R.id.titleEdit);
        contentEdit = (EditText)findViewById(R.id.contentEdit);
        setDateButton = (TextView)findViewById(R.id.setDateButton);
        setTimeButton = (TextView)findViewById(R.id.setTimeButton);
        reductionPersonnel = (Button)findViewById(R.id.reductionPersonnel);
        backButton = (Button)findViewById(R.id.backButton);

        Intent intent = getIntent();
        startingPointText.setText(intent.getExtras().getString("startingPointName"));
        endPointText.setText(intent.getExtras().getString("endPointName"));
        startingPointPosition = new LatLng(intent.getExtras().getDouble("startingPointLatitude"), intent.getExtras().getDouble("startingPointLongitude"));
        endPointPosition = new LatLng(intent.getExtras().getDouble("endPointLatitude"), intent.getExtras().getDouble("endPointLongitude"));

        centerPointPosition = new LatLng((startingPointPosition.latitude + endPointPosition.latitude) / 2.0, (startingPointPosition.longitude + endPointPosition.longitude) / 2.0);

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
                } else if(!(titleEdit.getText().toString().equals(""))){
                    titleEdit.setBackgroundResource(R.drawable.round_layout_side_orange);
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
                } else if(!(contentEdit.getText().toString().equals(""))){
                    contentEdit.setBackgroundResource(R.drawable.round_layout_side_orange);
                }
            }
        });

        SearchDateDialog searchDateDialog = new SearchDateDialog(CreatePartyActivity.this);
        setDateButton.setOnClickListener(v-> searchDateDialog.showDialog());



        addPersonnel.setOnClickListener(v->{
            if(personnelCount >= 0 && personnelCount < 4) {
                personnelCount++;
                numberOfPeopleText.setText(personnelCount + "명");
                numberOfPeopleText.setTextColor(getResources().getColor(R.color.colorTextBlack));
                numberOfPeopleText.setBackgroundResource(R.drawable.round_layout_side_orange);
            }
        });

        reductionPersonnel.setOnClickListener(v->{
            if(personnelCount > 1) {
                personnelCount--;
                numberOfPeopleText.setText(personnelCount + "명");
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
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerPointPosition, 16));
    }
}
