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
import android.widget.Toast;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.adapter.RecyclerItemClickListener;
import com.example.dsm2018.pickup.adapter.SearchDestinationAdapter;
import com.example.dsm2018.pickup.model.SearchDestinationModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchDestinationActivity extends AppCompatActivity {

    Button backButton, searchButton;
    EditText inputDestination;
    RelativeLayout beforeSearch;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<SearchDestinationModel> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_destination);

        data = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);


        backButton = (Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        inputDestination = (EditText)findViewById(R.id.inputDestination);
        searchButton = (Button)findViewById(R.id.searchButton);
        beforeSearch = (RelativeLayout) findViewById(R.id.beforeSearch);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String title = data.get(position).title;
                Intent intent = new Intent();
                intent.putExtra("destination", title);
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
            beforeSearch.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);

            recyclerView.setLayoutManager(layoutManager);

            List<Address> addressList = new ArrayList<>();
            try {
                addressList = geocoder.getFromLocationName(inputDestination.getText().toString().trim(), 5);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(addressList != null) {
                for (int i = 0; i < addressList.size(); i++) {
                    data.add(new SearchDestinationModel(addressList.get(i).getFeatureName(), addressList.get(i).getAddressLine(0)));
                }

                recyclerView.setAdapter(new SearchDestinationAdapter(data, getApplicationContext()));
            }
            if(addressList.size() == 0) {
                beforeSearch.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
            }
        });
    }
}

