package com.example.dsm2018.pickup.fragment;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.adapter.GpsInfo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class PartyListFragment extends Fragment implements OnMapReadyCallback {

    MapView currentLocation;
    GoogleMap mMap;
    private final int REQUEST_PERMISSION_CODE = 101;
    LinearLayout locationBox;
    ImageView locationImageView;
    TextView locationText;
    double latitude = 0, longitude = 0;
    GpsInfo gps;
    LatLng position;
    Bitmap pin, smallPin;

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
        pin = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_pin);
        smallPin = Bitmap.createScaledBitmap(pin, 70, 70, false);
        gps = new GpsInfo(getActivity());
        position = new LatLng(0, 0);

        init();

        currentLocation.getMapAsync(this);

        currentLocation.onCreate(savedInstanceState);
        currentLocation.onResume();

        MapsInitializer.initialize(getActivity().getApplicationContext());

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16));
        mMap.addMarker(new MarkerOptions().position(position).icon(BitmapDescriptorFactory.fromBitmap(smallPin)));
    }

    public void init(){
        if(gps.isGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            position = new LatLng(latitude, longitude);

            locationText.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
            locationText.setText(getAddress(latitude, longitude).getThoroughfare());
            locationImageView.setImageResource(R.drawable.ic_location_orange);
            locationBox.setBackgroundResource(R.drawable.round_layout_side_orange);
        } else {
            gps.showSettingsAlert();
        }
    }

    public Address getAddress(double latitude, double longitude){
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            return addresses.get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
