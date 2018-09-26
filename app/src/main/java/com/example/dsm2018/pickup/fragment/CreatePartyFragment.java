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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.activity.CreatePartyActivity;
import com.example.dsm2018.pickup.activity.SearchDestinationActivity;
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

    LinearLayout searchStartingPoint, searchDestination;
    TextView startingPointText, destinationText;
    ImageView setStartingPointIcon, setDestinationIcon;
    Button createPartyButton;

    Bitmap pin = null;
    String startingPointName, endPointName;
    Intent createPartyIntent;

    double latitude = 0, longitude = 0;
    boolean isStartingPointSet = false, isDestinationSet = false;

    private GoogleMap mMap = null;
    private MapView startingPoint, endPoint;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 100) {
            startingPointName = data.getExtras().getString("startingPoint");
            startingPointText.setText(startingPointName);
            searchStartingPoint.setBackgroundResource(R.drawable.round_layout_side_orange);
            setStartingPointIcon.setImageResource(R.drawable.ic_location_orange);
            startingPointText.setTextColor(getResources().getColor(R.color.colorTextBlack));
            latitude = data.getExtras().getDouble("latitude");
            longitude = data.getExtras().getDouble("longitude");
            createPartyIntent.putExtra("startingPointLatitude", latitude);
            createPartyIntent.putExtra("startingPointLongitude", longitude);
            pin = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_start), 80, 80, false);
            startingPoint.getMapAsync(this);
            isStartingPointSet = true;
        } else if(resultCode == 101) {
            endPointName = data.getStringExtra("destination");
            destinationText.setText(endPointName);
            searchDestination.setBackgroundResource(R.drawable.round_layout_side_orange);
            setDestinationIcon.setImageResource(R.drawable.ic_location_orange);
            destinationText.setTextColor(getResources().getColor(R.color.colorTextBlack));
            latitude = data.getExtras().getDouble("latitude");
            longitude = data.getExtras().getDouble("longitude");
            createPartyIntent.putExtra("endPointLatitude", latitude);
            createPartyIntent.putExtra("endPointLongitude", longitude);
            pin = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_arrive), 80, 80, false);
            endPoint.getMapAsync(this);
            isDestinationSet = true;
        }

        if(isStartingPointSet && isDestinationSet) {
            createPartyButton.setEnabled(true);
            createPartyButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
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
        createPartyButton = (Button)view.findViewById(R.id.createPartyButton);
        createPartyButton.setEnabled(false);

        createPartyIntent = new Intent(getActivity(), CreatePartyActivity.class);

        searchStartingPoint.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchStartingPointActivity.class);
            startActivityForResult(intent, 100);
        });

        searchDestination.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchDestinationActivity.class);
            startActivityForResult(intent, 101);
        });

        createPartyButton.setOnClickListener((v)-> {
            createPartyIntent.putExtra("startingPointName", startingPointName);
            createPartyIntent.putExtra("endPointName", endPointName);
            startActivity(createPartyIntent);
        });

        endPoint.onCreate(savedInstanceState);
        startingPoint.onCreate(savedInstanceState);
        endPoint.onResume();
        startingPoint.onResume();
        startingPoint.getMapAsync(this);
        endPoint.getMapAsync(this);
        MapsInitializer.initialize(getActivity().getApplicationContext());

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng position = new LatLng(latitude, longitude);

        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16));

        if(pin != null) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(position);
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(pin));
            mMap.addMarker(markerOptions);
        }
    }
}
