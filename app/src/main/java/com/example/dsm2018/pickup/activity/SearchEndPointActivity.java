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
import com.example.dsm2018.pickup.adapter.SearchDestinationAdapter;
import com.example.dsm2018.pickup.model.SearchPointModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchEndPointActivity extends AppCompatActivity {

    Button backButton, searchButton;
    EditText inputEndPoint;
    RelativeLayout beforeSearch;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<SearchPointModel> data;
    List<Address> addressList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_end_point);

        backButton = findViewById(R.id.backButton);
        inputEndPoint = findViewById(R.id.inputEndPoint);
        searchButton = findViewById(R.id.searchButton);
        beforeSearch = findViewById(R.id.beforeSearch);
        recyclerView = findViewById(R.id.recyclerView);

        data = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        backButton.setOnClickListener(v -> finish());

        inputEndPoint.setOnKeyListener((view, i, keyEvent) -> {
            if(((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && i == KeyEvent.KEYCODE_ENTER)) {
                searchLocation();
                return true;
            }
            return false;
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Dialog dialog = new Dialog(SearchEndPointActivity.this);
                dialog.setContentView(R.layout.dialog_setting_end_point);

                String title = data.get(position).title;
                String address = data.get(position).address;

                TextView titleText = dialog.findViewById(R.id.titleText);
                TextView addressText = dialog.findViewById(R.id.addressText);
                Button cancelButton = dialog.findViewById(R.id.cancelButton);
                Button selectButton = dialog.findViewById(R.id.selectButton);

                titleText.setText(title);
                addressText.setText(address);

                cancelButton.setOnClickListener(v-> dialog.dismiss());

                selectButton.setOnClickListener(v-> {
                    Intent intent = new Intent();
                    intent.putExtra("endPointName", title);
                    intent.putExtra("latitude", addressList.get(position).getLatitude());
                    intent.putExtra("longitude", addressList.get(position).getLongitude());
                    setResult(101, intent);
                    dialog.dismiss();
                    finish();
                });

                dialog.show();
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        searchButton.setOnClickListener((v)-> searchLocation());
    }

    private void searchLocation() {
        data.clear();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addressList = geocoder.getFromLocationName(inputEndPoint.getText().toString().trim(), 5);
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
            recyclerView.setAdapter(new SearchDestinationAdapter(data));
        }

        if(addressList.size() != 0) {
            beforeSearch.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}

