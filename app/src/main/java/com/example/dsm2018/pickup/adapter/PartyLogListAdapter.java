package com.example.dsm2018.pickup.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.model.PartyLogResponse;

import java.util.ArrayList;

public class PartyLogListAdapter extends RecyclerView.Adapter<PartyLogListAdapter.ViewHolder>{

    ArrayList<PartyLogResponse> data;

    public PartyLogListAdapter(ArrayList<PartyLogResponse> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_party_log, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PartyLogResponse partyLogResponse = data.get(position);
        holder.partyTitle.setText(partyLogResponse.party_title);
        holder.partyDate.setText(partyLogResponse.party_year + "년 " + partyLogResponse.party_month + "월 " + partyLogResponse.party_day + "일");
        holder.partyStatus.setText(partyLogResponse.party_status);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView partyTitle;
        public TextView partyDate;
        public TextView partyStatus;
        public ViewHolder(View itemView) {
            super(itemView);
            partyTitle = itemView.findViewById(R.id.partyTitle);
            partyDate = itemView.findViewById(R.id.partyDate);
            partyStatus = itemView.findViewById(R.id.partyStatus);
        }
    }
}
