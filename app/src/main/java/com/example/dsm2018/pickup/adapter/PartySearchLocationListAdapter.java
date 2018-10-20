package com.example.dsm2018.pickup.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.model.PartySearchLocationResponse;

import java.util.ArrayList;

public class PartySearchLocationListAdapter extends RecyclerView.Adapter<PartySearchLocationListAdapter.ViewHolder>{

    public PartySearchLocationListAdapter(ArrayList<PartySearchLocationResponse> data) {
        this.data = data;
    }

    ArrayList<PartySearchLocationResponse> data;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_party_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PartySearchLocationResponse response = data.get(position);
        holder.partyTitle.setText(response.party_title);
        holder.partyDate.setText(response.party_year + "년 " + response.party_month + "월 " +  response.party_day + "일");
        holder.partyPeopleNum.setText(response.party_curnum + "명/" + response.party_peoplenum + "명");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView partyTitle;
        public TextView partyDate;
        public TextView partyPeopleNum;
        public ViewHolder(View itemView) {
            super(itemView);
            partyTitle = itemView.findViewById(R.id.partyTitle);
            partyDate = itemView.findViewById(R.id.partyDate);
            partyPeopleNum = itemView.findViewById(R.id.partyPeopleNum);
        }
    }
}
