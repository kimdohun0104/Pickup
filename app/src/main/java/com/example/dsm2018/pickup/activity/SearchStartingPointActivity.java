package com.example.dsm2018.pickup.activity;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.dsm2018.pickup.R;

import java.io.IOException;
import java.util.List;

public class SearchStartingPointActivity extends AppCompatActivity {

    Button backButton;
    EditText inputStartingPoint;
    RelativeLayout beforeSearch;
    RecyclerView recyclerView;
    Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_starting_point);

        geocoder = new Geocoder(this);

        inputStartingPoint = (EditText)findViewById(R.id.inputStartingPoint);
        backButton = (Button)findViewById(R.id.backButton);
        beforeSearch = (RelativeLayout)findViewById(R.id.beforeSearch);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
