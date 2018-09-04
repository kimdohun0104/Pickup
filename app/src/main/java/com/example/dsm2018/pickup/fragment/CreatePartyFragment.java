package com.example.dsm2018.pickup.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.activity.SearchDestinationActivity;
import com.example.dsm2018.pickup.activity.SearchStartingPointActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class CreatePartyFragment extends Fragment implements OnMapReadyCallback {

    LinearLayout searchStartingPoint, searchDestination;
    private GoogleMap mMap = null;
    private MapView startingPoint, endPoint;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_party, container, false);

        startingPoint = (MapView)view.findViewById(R.id.startingPoint);
        endPoint = (MapView)view.findViewById(R.id.endPoint);
        searchStartingPoint = (LinearLayout)view.findViewById(R.id.searchStartingPoint);
        searchDestination = (LinearLayout)view.findViewById(R.id.searchDestination);

        searchStartingPoint.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchStartingPointActivity.class);
            startActivity(intent);
        });

        searchDestination.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchDestinationActivity.class);
            startActivity(intent);
        });

        startingPoint.getMapAsync(this);
        endPoint.getMapAsync(this); 
        startingPoint.onCreate(savedInstanceState);
        startingPoint.onResume();
        endPoint.onCreate(savedInstanceState);
        endPoint.onResume();

        MapsInitializer.initialize(getActivity().getApplicationContext());

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng position = new LatLng(37.52487, 126.92723);

        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16));
    }
}
