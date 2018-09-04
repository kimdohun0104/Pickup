package com.example.dsm2018.pickup.activity;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.model.StartingPointItemModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchStartingPointActivity extends AppCompatActivity {

    Button backButton, searchButton;
    EditText inputStartingPoint;
    RelativeLayout beforeSearch;
    RecyclerView recyclerView;
    Geocoder geocoder;
    LinearLayoutManager layoutManager;
    ArrayList<StartingPointItemModel> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_starting_point);

        geocoder = new Geocoder(this);
        layoutManager = new LinearLayoutManager(this);
        data = new ArrayList<>();

        inputStartingPoint = (EditText)findViewById(R.id.inputStartingPoint);
        backButton = (Button)findViewById(R.id.backButton);
        beforeSearch = (RelativeLayout)findViewById(R.id.beforeSearch);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        searchButton = (Button)findViewById(R.id.searchButton);

        inputStartingPoint.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == EditorInfo.IME_ACTION_SEARCH ||
                    actionId == EditorInfo.IME_ACTION_DONE ||
                    event.getAction() == KeyEvent.ACTION_DOWN ||
                    event.getAction() == KeyEvent.KEYCODE_ENTER){

                String location = inputStartingPoint.getText().toString();
                List<Address> addressList = new ArrayList<>();

                try {
                    addressList = geocoder.getFromLocationName(location, 10);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println(addressList.size());
            }
            return false;
        });

        backButton.setOnClickListener(v -> finish());
    }
}
