package com.example.dsm2018.pickup.activity;

import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.RecyclerItemClickListener;
import com.example.dsm2018.pickup.adapter.SearchStartingPointAdapter;
import com.example.dsm2018.pickup.model.SearchPointModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchStartingPointActivity extends AppCompatActivity {

    Button backButton, searchButton;
    EditText inputStartingPoint;
    RelativeLayout beforeSearch;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    ArrayList<SearchPointModel> data;
    List<Address> addressList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_starting_point);

        inputStartingPoint = findViewById(R.id.inputStartingPoint);
        backButton = findViewById(R.id.backButton);
        beforeSearch = findViewById(R.id.beforeSearch);
        recyclerView = findViewById(R.id.recyclerView);
        searchButton = findViewById(R.id.searchButton);

        data = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);

        inputStartingPoint.setOnKeyListener((view, i, keyEvent) -> {
            if(((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && i == KeyEvent.KEYCODE_ENTER)) {
                searchLocation();
                return true;
            }
            return false;
        });

        backButton.setOnClickListener(v -> finish());

        searchButton.setOnClickListener((v)->{
            searchLocation();
        });

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String title = data.get(position).title;
                String address = data.get(position).address;

                Dialog dialog = new Dialog(SearchStartingPointActivity.this);
                dialog.setContentView(R.layout.dialog_setting_starting_point);

                TextView titleText = dialog.findViewById(R.id.titleText);
                TextView addressText = dialog.findViewById(R.id.addressText);
                Button selectButton = dialog.findViewById(R.id.selectButton);
                Button cancelButton = dialog.findViewById(R.id.cancelButton);

                titleText.setText(title);
                addressText.setText(address);

                selectButton.setOnClickListener(v->{
                    Intent intent = new Intent();
                    intent.putExtra("startingPointName", title);
                    intent.putExtra("latitude", addressList.get(position).getLatitude());
                    intent.putExtra("longitude", addressList.get(position).getLongitude());
                    setResult(100, intent);
                    dialog.dismiss();
                    finish();
                });

                cancelButton.setOnClickListener(v-> dialog.dismiss());

                dialog.show();
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    private void searchLocation() {
        data.clear();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        beforeSearch.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);

        recyclerView.setLayoutManager(layoutManager);

        addressList = new ArrayList<>();
        try {
            addressList = geocoder.getFromLocationName(inputStartingPoint.getText().toString().trim(), 5);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(addressList != null) {
            for (int i = 0; i < addressList.size(); i++) {
                StringBuilder address = new StringBuilder();
                Address iAddress = addressList.get(i);
                if(iAddress.getLocality() != null)
                    address.append(iAddress.getLocality() + " ");
                if(iAddress.getThoroughfare() != null)
                    address.append(iAddress.getThoroughfare() + " ");
                if(iAddress.getFeatureName() != null)
                    address.append(iAddress.getFeatureName() + " ");

                data.add(new SearchPointModel(address.toString(), iAddress.getAddressLine(0)));
            }

            recyclerView.setAdapter(new SearchStartingPointAdapter(data));
        }
        if(addressList.size() == 0) {
            beforeSearch.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }
    }
}
