package com.example.dsm2018.pickup.activity;

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
import com.example.dsm2018.pickup.adapter.SearchDestinationAdapter;
import com.example.dsm2018.pickup.model.SearchDestinationModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchStartingPointActivity extends AppCompatActivity {

    Button backButton, searchButton;
    EditText inputStartingPoint;
    RelativeLayout beforeSearch;
    RecyclerView recyclerView;
    ArrayList<SearchDestinationModel> data;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_starting_point);

        data = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);

        inputStartingPoint = (EditText)findViewById(R.id.inputStartingPoint);
        backButton = (Button)findViewById(R.id.backButton);
        beforeSearch = (RelativeLayout)findViewById(R.id.beforeSearch);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        searchButton = (Button)findViewById(R.id.searchButton);

        backButton.setOnClickListener(v -> finish());

        recyclerView.setLayoutManager(layoutManager);


    }
}
