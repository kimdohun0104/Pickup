package com.example.dsm2018.pickup.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.model.SearchPointModel;

import java.util.ArrayList;

public class SearchStartingPointAdapter extends RecyclerView.Adapter<SearchStartingPointAdapter.ViewHolder>{

    ArrayList<SearchPointModel> data;

    public SearchStartingPointAdapter(ArrayList<SearchPointModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_starting_point, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleText.setText(data.get(position).title);
        holder.addressText.setText(data.get(position).address);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView titleText;
        public TextView addressText;
        public ViewHolder(View itemView) {
            super(itemView);
            titleText = (TextView) itemView.findViewById(R.id.titleText);
            addressText = (TextView) itemView.findViewById(R.id.addressText);
        }
    }
}
