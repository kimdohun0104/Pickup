package com.example.dsm2018.pickup.fragment;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.GPSTracker;
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

public class PartyListFragment extends Fragment implements OnMapReadyCallback{

    GPSTracker gpsTracker;
    GoogleMap mMap;
    MapView mapView;
    Bitmap pin;
    TextView currentLocationText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_party_list, container, false);

        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 101);

        pin = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_pin), 80, 80, false);
        gpsTracker = new GPSTracker(getActivity());

        mapView = (MapView)view.findViewById(R.id.currentLocation);
        currentLocationText = (TextView)view.findViewById(R.id.locationText);

        mapView.getMapAsync(this);

        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        MapsInitializer.initialize(getActivity().getApplicationContext());

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        double latitude, longitude;

        mMap.getUiSettings().setAllGesturesEnabled(false);

        Log.d("debugLog", "isGpsEnabled : " + gpsTracker.isGPSEnabled);
        Log.d("debugLog", "isNetworkEnabled : " + gpsTracker.isNetworkEnabled);

        if(gpsTracker.isGPSEnabled) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();

            LatLng position = new LatLng(latitude, longitude);

            Log.d("debugLog", latitude + ", " + longitude);

            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16));

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(position);
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(pin));
            mMap.addMarker(markerOptions);

            currentLocationText.setText(getAddress(getActivity(), latitude, longitude));
        }
    }

    public static String getAddress(Context mContext, double lat, double lng) {
        String nowAddress ="현재 위치를 확인 할 수 없습니다.";
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        List <Address> address;
        try {
            if (geocoder != null) {
                address = geocoder.getFromLocation(lat, lng, 1);

                if (address != null && address.size() > 0) {
                    String currentLocationAddress = address.get(0).getAddressLine(0).toString();
                    nowAddress  = currentLocationAddress;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return nowAddress;
    }
}
