package com.example.dsm2018.pickup.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.RecyclerItemClickListener;
import com.example.dsm2018.pickup.RetrofitHelp;
import com.example.dsm2018.pickup.RetrofitService;
import com.example.dsm2018.pickup.adapter.PartySearchTextListAdapter;
import com.example.dsm2018.pickup.fragment.FilterSheetFragment;
import com.example.dsm2018.pickup.model.PartyDetailResponse;
import com.example.dsm2018.pickup.model.PartySearchTextResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    public FloatingActionButton filterButton;
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

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Map<String, String> map = new HashMap();
                map.put("user_authorization", sharedPreferences.getString("user_authorization", ""));
                map.put("party_key", data.get(position).party_key);

                Call<List<PartyDetailResponse>> call = retrofitService.partyDetail(map);
                call.enqueue(new Callback<List<PartyDetailResponse>>() {
                    @Override
                    public void onResponse(Call<List<PartyDetailResponse>> call, Response<List<PartyDetailResponse>> response) {
                        Intent intent = new Intent(getApplicationContext(), PartyDetailActivity.class);

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

        searchButton.setOnClickListener(v-> {

            if(isFilter) {
                Map<String, String> map = new HashMap() {{
                    put("user_authorization", sharedPreferences.getString("user_authorization", ""));
                    put("search_text", searchEdit.getText().toString().trim());
                    put("filter_departure_lat", filterBundle.getString("filter_departure_lat"));
                    put("filter_departure_lng", filterBundle.getString("filter_departure_lng"));
                    put("filter_destination_lat", filterBundle.getString("filter_destination_lat"));
                    put("filter_destination_lng", filterBundle.getString("filter_destination_lng"));
                    put("filter_date_year", filterBundle.getString("filter_date_year"));
                    put("filter_date_month", filterBundle.getString("filter_date_month"));
                    put("filter_date_day", filterBundle.getString("filter_date_day"));
                    put("filter_date_hour", filterBundle.getString("filter_date_hour"));
                    put("filter_date_minute", filterBundle.getString("filter_date_minute"));
                }};

                Log.d("DEBUGLOG", filterBundle.getString("filter_departure_lat"));

                Call<List<PartySearchTextResponse>> call = retrofitService.partySearchText(map);
                call.enqueue(new Callback<List<PartySearchTextResponse>>() {
                    @Override
                    public void onResponse(Call<List<PartySearchTextResponse>> call, Response<List<PartySearchTextResponse>> response) {
                        if(response.code() == 200) {
                            if(response.body() != null) {
                                data.clear();
                                data.addAll(response.body());
                                recyclerView.setLayoutManager(layoutManager);
                                listAdapter = new PartySearchTextListAdapter(data);
                                recyclerView.setAdapter(listAdapter);
                            }
                        } else if(response.code() == 500) {
                            Toast.makeText(getApplicationContext(), "서버 오류가 발생하였습니다. 잠시후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PartySearchTextResponse>> call, Throwable t) {

                    }
                });
            } else if(!isFilter) {
                Map<String, String> map = new HashMap() {{
                    put("user_authorization", sharedPreferences.getString("user_authorization", ""));
                    put("search_text", searchEdit.getText().toString().trim());
                    put("filter_departure_lat", "");
                    put("filter_departure_lng", "");
                    put("filter_destination_lat", "");
                    put("filter_destination_lng", "");
                    put("filter_date_year", "");
                    put("filter_date_month", "");
                    put("filter_date_day", "");
                    put("filter_date_hour", "");
                    put("filter_date_minute", "");
                }};

                Call<List<PartySearchTextResponse>> call = retrofitService.partySearchText(map);
                call.enqueue(new Callback<List<PartySearchTextResponse>>() {
                    @Override
                    public void onResponse(Call<List<PartySearchTextResponse>> call, Response<List<PartySearchTextResponse>> response) {
                        if(response.code() == 200) {
                            if(response.body() != null) {
                                Log.d("DEBUGLOG", response.body().get(0).party_title);
                                data.clear();
                                data.addAll(response.body());
                                recyclerView.setLayoutManager(layoutManager);
                                listAdapter = new PartySearchTextListAdapter(data);
                                recyclerView.setAdapter(listAdapter);
                            }
                        } else if(response.code() == 500) {
                            Toast.makeText(getApplicationContext(), "서버 오류가 발생하였습니다. 잠시후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                        }
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
