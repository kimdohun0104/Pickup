package com.example.dsm2018.pickup.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView startingPointText, destinationText;
    ImageView setStartingPointIcon, setDestinationIcon;

    private GoogleMap mMap = null;
    private MapView startingPoint, endPoint;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 100) {
            startingPointText.setText(data.getStringExtra("startingPoint"));
            searchStartingPoint.setBackgroundResource(R.drawable.round_layout_side_orange);
            setStartingPointIcon.setImageResource(R.drawable.ic_location_orange);
        } else if(resultCode == 101) {
            destinationText.setText(data.getStringExtra("destination"));
            searchDestination.setBackgroundResource(R.drawable.round_layout_side_orange);
            setDestinationIcon.setImageResource(R.drawable.ic_location_orange);
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_party, container, false);

        startingPoint = (MapView)view.findViewById(R.id.startingPoint);
        endPoint = (MapView)view.findViewById(R.id.endPoint);
        searchStartingPoint = (LinearLayout)view.findViewById(R.id.searchStartingPoint);
        searchDestination = (LinearLayout)view.findViewById(R.id.searchDestination);
        startingPointText = (TextView)view.findViewById(R.id.startingPointText);
        destinationText = (TextView)view.findViewById(R.id.destinationText);
        setStartingPointIcon = (ImageView)view.findViewById(R.id.setStartingPointIcon);
        setDestinationIcon = (ImageView)view.findViewById(R.id.setDestinationIcon);

        searchStartingPoint.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchStartingPointActivity.class);
            startActivityForResult(intent, 100);
        });

        searchDestination.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchDestinationActivity.class);
            startActivityForResult(intent, 101);
        });

        endPoint.getMapAsync(this);
        startingPoint.getMapAsync(this);
        endPoint.onCreate(savedInstanceState);
        startingPoint.onCreate(savedInstanceState);
        endPoint.onResume();
        startingPoint.onResume();

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
