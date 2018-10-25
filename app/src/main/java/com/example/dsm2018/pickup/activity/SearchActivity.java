package com.example.dsm2018.pickup.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.RetrofitHelp;
import com.example.dsm2018.pickup.RetrofitService;
import com.example.dsm2018.pickup.adapter.PartySearchTextListAdapter;
import com.example.dsm2018.pickup.fragment.FilterSheetFragment;
import com.example.dsm2018.pickup.model.PartySearchTextResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    FloatingActionButton filterButton;
    Button backButton, searchButton;
    EditText searchEdit;
    RecyclerView recyclerView;

    ArrayList<PartySearchTextResponse> data;
    LinearLayoutManager layoutManager;
    PartySearchTextListAdapter listAdapter;

    RetrofitService retrofitService;
    SharedPreferences sharedPreferences;

    public Bundle filterBundle;
    public boolean isFilter = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        retrofitService = new RetrofitHelp().retrofitService;
        sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);
        data = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getApplicationContext());

        filterButton = findViewById(R.id.filterButton);
        backButton = findViewById(R.id.backButton);
        searchButton = findViewById(R.id.searchButton);
        searchEdit = findViewById(R.id.searchEdit);
        recyclerView = findViewById(R.id.recyclerView);

        filterButton.setOnClickListener(v -> {
            FilterSheetFragment filterSheetFragment = FilterSheetFragment.getInstance();
            filterSheetFragment.show(getSupportFragmentManager(), "fragment_bottom_sheet");
        });

        searchButton.setOnClickListener(v-> {
            if(isFilter) {
                Map<String, String> map = new HashMap();
                map.put("user_authorization", sharedPreferences.getString("user_authorization", ""));
                map.put("search_text", searchEdit.getText().toString());
                map.put("filter_departure_lat", filterBundle.getString("filter_departure_lat"));
                map.put("filter_departure_lng", filterBundle.getString("filter_departure_lng"));
                map.put("filter_destination_lat", filterBundle.getString("filter_destination_lat"));
                map.put("filter_destination_lng", filterBundle.getString("filter_destination_lng"));
                map.put("filter_date_year", filterBundle.getString("filter_date_year"));
                map.put("filter_date_month", filterBundle.getString("filter_date_month"));
                map.put("filter_date_day", filterBundle.getString("filter_date_day"));
                map.put("filter_date_hour", filterBundle.getString("filter_date_hour"));
                map.put("filter_date_minute", filterBundle.getString("filter_date_minute"));

                Log.d("user_authorization", sharedPreferences.getString("user_authorization", ""));
                Log.d("search_text", searchEdit.getText().toString());
                Log.d("filter_departure_lat", filterBundle.getString("filter_departure_lat"));
                Log.d("filter_departure_lng", filterBundle.getString("filter_departure_lng"));
                Log.d("filter_destination_lat", filterBundle.getString("filter_destination_lat"));
                Log.d("filter_destination_lng", filterBundle.getString("filter_destination_lng"));
                Log.d("filter_date_year", filterBundle.getString("filter_date_year"));
                Log.d("filter_date_month", filterBundle.getString("filter_date_month"));
                Log.d("filter_date_day", filterBundle.getString("filter_date_day"));
                Log.d("filter_date_hour", filterBundle.getString("filter_date_hour"));
                Log.d("filter_date_minute", filterBundle.getString("filter_date_minute"));

                Call<List<PartySearchTextResponse>> call = retrofitService.partySearchText(map);
                call.enqueue(new Callback<List<PartySearchTextResponse>>() {
                    @Override
                    public void onResponse(Call<List<PartySearchTextResponse>> call, Response<List<PartySearchTextResponse>> response) {
                        data.addAll(response.body());
                        recyclerView.setLayoutManager(layoutManager);
                        listAdapter = new PartySearchTextListAdapter(data);
                        recyclerView.setAdapter(listAdapter);
                    }

                    @Override
                    public void onFailure(Call<List<PartySearchTextResponse>> call, Throwable t) {

                    }
                });
            } else if(!isFilter) {
                Map<String, String> map = new HashMap();
                map.put("user_authorization", sharedPreferences.getString("user_authorization", ""));
                map.put("search_text", searchEdit.getText().toString());
                map.put("filter_departure_lat", "");
                map.put("filter_departure_lng", "");
                map.put("filter_destination_lat", "");
                map.put("filter_destination_lng", "");
                map.put("filter_date_year", "");
                map.put("filter_date_month", "");
                map.put("filter_date_day", "");
                map.put("filter_date_hour", "");
                map.put("filter_date_minute", "");

                Call<List<PartySearchTextResponse>> call = retrofitService.partySearchText(map);
                call.enqueue(new Callback<List<PartySearchTextResponse>>() {
                    @Override
                    public void onResponse(Call<List<PartySearchTextResponse>> call, Response<List<PartySearchTextResponse>> response) {
                        data.addAll(response.body());
                        recyclerView.setLayoutManager(layoutManager);
                        listAdapter = new PartySearchTextListAdapter(data);
                        recyclerView.setAdapter(listAdapter);
                    }

                    @Override
                    public void onFailure(Call<List<PartySearchTextResponse>> call, Throwable t) {

                    }
                });
            }
        });

        backButton.setOnClickListener(v -> finish());
    }
}
