package com.example.dsm2018.pickup.activity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

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

        backButton = (Button)findViewById(R.id.backButton);
        inputEndPoint = (EditText)findViewById(R.id.inputEndPoint);
        searchButton = (Button)findViewById(R.id.searchButton);
        beforeSearch = (RelativeLayout) findViewById(R.id.beforeSearch);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        data = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        backButton.setOnClickListener(v -> finish());

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String title = data.get(position).title;
                Intent intent = new Intent();
                intent.putExtra("endPointName", title);
                intent.putExtra("latitude", addressList.get(position).getLatitude());
                intent.putExtra("longitude", addressList.get(position).getLongitude());
                setResult(101, intent);
                finish();
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        searchButton.setOnClickListener((v)->{
            data.clear();
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());

            try {
                addressList = geocoder.getFromLocationName(inputEndPoint.getText().toString().trim(), 5);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(addressList != null) {
                for (int i = 0; i < addressList.size(); i++) {
                    data.add(new SearchPointModel(addressList.get(i).getFeatureName(), addressList.get(i).getAddressLine(0)));
                }
                recyclerView.setAdapter(new SearchDestinationAdapter(data));
            }

            if(addressList.size() != 0) {
                beforeSearch.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
    }
}
