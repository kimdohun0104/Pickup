package com.example.dsm2018.pickup.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.activity.CreatePartyActivity;
import com.example.dsm2018.pickup.activity.SearchEndPointActivity;
import com.example.dsm2018.pickup.activity.SearchStartingPointActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CreatePartyFragment extends Fragment implements OnMapReadyCallback {

    TextView searchStartingPoint, searchEndPoint;
    ImageView startingPointIcon, endPointIcon;
    Button createPartyButton;

    Bitmap pin = null;
    String startingPointName, endPointName;
    Intent createPartyIntent;

    double latitude = 0, longitude = 0;
    boolean isStartingPointSet = false, isEndPointSet = false;

    private GoogleMap googleMap = null;
    private MapView startingPointMap, endPointMap;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 100) {
            startingPointName = data.getExtras().getString("startingPointName");
            searchStartingPoint.setText(startingPointName);
            searchStartingPoint.setBackgroundResource(R.drawable.round_layout_side_orange);
            startingPointIcon.setImageResource(R.drawable.ic_location_orange);
            searchStartingPoint.setTextColor(getResources().getColor(R.color.colorTextBlack));
            latitude = data.getExtras().getDouble("latitude");
            longitude = data.getExtras().getDouble("longitude");
            createPartyIntent.putExtra("startingPointLatitude", latitude);
            createPartyIntent.putExtra("startingPointLongitude", longitude);
            pin = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_start), 80, 80, false);
            startingPointMap.getMapAsync(this);
            isStartingPointSet = true;
        } else if(resultCode == 101) {
            endPointName = data.getStringExtra("endPointName");
            searchEndPoint.setText(endPointName);
            searchEndPoint.setBackgroundResource(R.drawable.round_layout_side_orange);
            endPointIcon.setImageResource(R.drawable.ic_location_orange);
            searchEndPoint.setTextColor(getResources().getColor(R.color.colorTextBlack));
            latitude = data.getExtras().getDouble("latitude");
            longitude = data.getExtras().getDouble("longitude");
            createPartyIntent.putExtra("endPointLatitude", latitude);
            createPartyIntent.putExtra("endPointLongitude", longitude);
            pin = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_arrive), 80, 80, false);
            endPointMap.getMapAsync(this);
            isEndPointSet = true;
        }

        if(isStartingPointSet && isEndPointSet) {
            createPartyButton.setEnabled(true);
            createPartyButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_party, container, false);

        startingPointMap = view.findViewById(R.id.startingPointMap);
        endPointMap = view.findViewById(R.id.endPointMap);
        searchStartingPoint = view.findViewById(R.id.searchStartingPoint);
        searchEndPoint = view.findViewById(R.id.searchEndPoint);
        startingPointIcon = view.findViewById(R.id.startingPointIcon);
        endPointIcon = view.findViewById(R.id.endPointIcon);
        createPartyButton = view.findViewById(R.id.createPartyButton);

        createPartyButton.setEnabled(false);

        createPartyIntent = new Intent(getActivity(), CreatePartyActivity.class);

        searchStartingPoint.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchStartingPointActivity.class);
            startActivityForResult(intent, 100);
        });

        searchEndPoint.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchEndPointActivity.class);
            startActivityForResult(intent, 101);
        });

        createPartyButton.setOnClickListener((v)-> {
            createPartyIntent.putExtra("startingPointName", startingPointName);
            createPartyIntent.putExtra("endPointName", endPointName);
            startActivity(createPartyIntent);
        });

        endPointMap.onCreate(savedInstanceState);
        startingPointMap.onCreate(savedInstanceState);
        endPointMap.onResume();
        startingPointMap.onResume();
        startingPointMap.getMapAsync(this);
        endPointMap.getMapAsync(this);
        MapsInitializer.initialize(getActivity().getApplicationContext());

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        LatLng position = new LatLng(latitude, longitude);

        this.googleMap.getUiSettings().setAllGesturesEnabled(false);
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16));

        if(pin != null) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(position);
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(pin));
            this.googleMap.addMarker(markerOptions);
        }
    }
}
