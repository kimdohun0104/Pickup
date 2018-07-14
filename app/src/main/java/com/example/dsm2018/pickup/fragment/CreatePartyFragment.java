package com.example.dsm2018.pickup.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dsm2018.pickup.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CreatePartyFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap = null;
    private MapView startingPoint, endPoint;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_party, container, false);

        startingPoint = (MapView)view.findViewById(R.id.startingPoint);
        endPoint = (MapView)view.findViewById(R.id.endPoint);

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

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(0,0),0));
    }

    @Override
    public void onResume() {
        super.onResume();
        startingPoint.onResume();
        endPoint.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        startingPoint.onPause();
        endPoint.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        startingPoint.onDestroy();
        endPoint.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        startingPoint.onLowMemory();
    }
}
