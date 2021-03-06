package com.example.dsm2018.pickup.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.RetrofitHelp;
import com.example.dsm2018.pickup.RetrofitService;
import com.example.dsm2018.pickup.adapter.PartyLogListAdapter;
import com.example.dsm2018.pickup.model.PartyLogResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PartyLogFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    ArrayList<PartyLogResponse> data;
    PartyLogListAdapter partyLogListAdapter;
    LinearLayoutManager layoutManager;
    RetrofitService retrofitService;
    SharedPreferences sharedPreferences;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_party_log, container, false);

        data = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());
        retrofitService = new RetrofitHelp().retrofitService;
        sharedPreferences = view.getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);

        recyclerView = view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = view.findViewById(R.id.swipeLayout);

        swipeRefreshLayout.setOnRefreshListener(this);

        Map<String, String> map = new HashMap();
        map.put("user_authorization", sharedPreferences.getString("user_authorization", ""));

        Call<List<PartyLogResponse>> call = retrofitService.partyLog(map);
        call.enqueue(new Callback<List<PartyLogResponse>>() {
            @Override
            public void onResponse(Call<List<PartyLogResponse>> call, Response<List<PartyLogResponse>> response) {
                if (response.code() == 200) {
                    data.clear();
                    data.addAll(response.body());
                    partyLogListAdapter = new PartyLogListAdapter(data);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(partyLogListAdapter);
                } else if(response.code() == 500) {
                    Toast.makeText(getActivity(), "서버 오류가 발생하였습니다. 잠시후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PartyLogResponse>> call, Throwable t) {

            }
        });

        return view;
    }

    @Override
    public void onRefresh() {

        Map<String, String> map = new HashMap();
        map.put("user_authorization", sharedPreferences.getString("user_authorization", ""));

        Call<List<PartyLogResponse>> call = retrofitService.partyLog(map);
        call.enqueue(new Callback<List<PartyLogResponse>>() {
            @Override
            public void onResponse(Call<List<PartyLogResponse>> call, Response<List<PartyLogResponse>> response) {
                if(response.code() == 200) {
                    data.clear();
                    data.addAll(response.body());
                    partyLogListAdapter = new PartyLogListAdapter(data);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(partyLogListAdapter);
                } else{
                    Toast.makeText(getActivity(), "서버 오류", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PartyLogResponse>> call, Throwable t) {

            }
        });

        swipeRefreshLayout.setRefreshing(false);
    }
}
