package com.example.dsm2018.pickup.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dsm2018.pickup.GPSTracker;
import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.RecyclerItemClickListener;
import com.example.dsm2018.pickup.RetrofitHelp;
import com.example.dsm2018.pickup.RetrofitService;
import com.example.dsm2018.pickup.activity.PartyDetailActivity;
import com.example.dsm2018.pickup.adapter.PartySearchLocationListAdapter;
import com.example.dsm2018.pickup.model.PartyDetailResponse;
import com.example.dsm2018.pickup.model.PartySearchLocationResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PartyListFragment extends Fragment implements OnMapReadyCallback{

    GPSTracker gpsTracker;
    GoogleMap mMap;
    MapView mapView;
    Bitmap pin;
    TextView currentLocationText;
    ImageView currentLocationIcon;

    SharedPreferences sharedPreferences;
    RetrofitService retrofitService;

    RecyclerView recyclerView;
    ArrayList<PartySearchLocationResponse> data;
    LinearLayoutManager layoutManager;
    PartySearchLocationListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_party_list, container, false);

        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 101);

        pin = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_pin), 80, 80, false);
        gpsTracker = new GPSTracker(getActivity());
        layoutManager = new LinearLayoutManager(getActivity());
        data = new ArrayList<>();
        retrofitService = new RetrofitHelp().retrofitService;
        sharedPreferences = view.getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);

        currentLocationText = view.findViewById(R.id.currentLocationText);
        mapView = view.findViewById(R.id.currentLocation);
        currentLocationIcon = view.findViewById(R.id.currentLocationIcon);
        recyclerView = view.findViewById(R.id.recyclerView);

        mapView.getMapAsync(this);

        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        MapsInitializer.initialize(getActivity().getApplicationContext());

        currentLocationText.setOnClickListener(v->{
            mapView.getMapAsync(this);
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Map<String, String> map = new HashMap();
                map.put("user_authorization", sharedPreferences.getString("user_authorization", ""));
                map.put("party_key", data.get(position).party_key);

                Call<List<PartyDetailResponse>> call = retrofitService.partyDetail(map);
                call.enqueue(new Callback<List<PartyDetailResponse>>() {
                    @Override
                    public void onResponse(Call<List<PartyDetailResponse>> call, Response<List<PartyDetailResponse>> response) {
                        Intent intent = new Intent(getActivity(), PartyDetailActivity.class);

                        ArrayList<PartyDetailResponse> array = new ArrayList<>();
                        array.addAll(response.body());
                        intent.putExtra("response", array);

                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<List<PartyDetailResponse>> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();
        double latitude, longitude;

        mMap.getUiSettings().setAllGesturesEnabled(false);

        if(gpsTracker.isGPSEnabled) {
            gpsTracker = new GPSTracker(getActivity());
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();

            Log.d("DEBUGLOG", "party list: " + String.valueOf(latitude));
            Log.d("DEBUGLOG", "party list: " + String.valueOf(longitude));

            LatLng position = new LatLng(latitude, longitude);

            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16));

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(position);
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(pin));
            mMap.addMarker(markerOptions);

            currentLocationText.setText(getAddress(getActivity(), latitude, longitude));
            currentLocationText.setTextColor(getResources().getColor(R.color.colorTextBlack));
            currentLocationText.setBackgroundResource(R.drawable.round_layout_side_orange);
            currentLocationIcon.setImageResource(R.drawable.ic_location_orange);

            Map<String, String> map = new HashMap();
            map.put("user_authorization", sharedPreferences.getString("user_authorization", ""));
            map.put("departure_lat", String.valueOf(latitude));
            map.put("departure_lng", String.valueOf(longitude));

            Call<List<PartySearchLocationResponse>> call = retrofitService.partySearchLocation(map);
            call.enqueue(new Callback<List<PartySearchLocationResponse>>() {
                @Override
                public void onResponse(Call<List<PartySearchLocationResponse>> call, Response<List<PartySearchLocationResponse>> response) {
                    data.clear();
                    data.addAll(response.body());
                    adapter = new PartySearchLocationListAdapter(data);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<List<PartySearchLocationResponse>> call, Throwable t) {

                }
            });
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
                    String currentLocationAddress = address.get(0).getAddressLine(0);
                    nowAddress  = currentLocationAddress;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return nowAddress;
    }
}
