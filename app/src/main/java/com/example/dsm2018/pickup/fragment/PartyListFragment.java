package com.example.dsm2018.pickup.fragment;

import android.Manifest;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.adapter.GpsInfo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class PartyListFragment extends Fragment implements OnMapReadyCallback {

    MapView currentLocation;
    GoogleMap mMap;
    private final int REQUEST_PERMISSION_CODE = 101;
    LinearLayout locationBox;
    ImageView locationImageView;
    TextView locationText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_party_list, container, false);

        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSION_CODE);

        currentLocation = (MapView) view.findViewById(R.id.currentLocation);
        locationBox = (LinearLayout)view.findViewById(R.id.locationBox);
        locationImageView = (ImageView)view.findViewById(R.id.locationImageView);
        locationText = (TextView)view.findViewById(R.id.locationText);

        currentLocation.getMapAsync(this);

        currentLocation.onCreate(savedInstanceState);
        currentLocation.onResume();

        MapsInitializer.initialize(getActivity().getApplicationContext());

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        GpsInfo gps = new GpsInfo(getActivity());

        if(gps.isGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            LatLng position = new LatLng(latitude, longitude);

            Log.d("my log", latitude + "," + longitude);

            mMap.getUiSettings().setAllGesturesEnabled(false);
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16));

            locationText.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
            locationBox.setBackgroundResource(R.drawable.round_layout_side_orange);
            locationImageView.setImageResource(R.drawable.ic_location_orange);

        } else {
            gps.showSettingsAlert();
            mMap.getUiSettings().setAllGesturesEnabled(false);
        }
    }
}
