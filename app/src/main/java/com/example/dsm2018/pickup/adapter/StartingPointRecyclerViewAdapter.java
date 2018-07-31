package com.example.dsm2018.pickup.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.model.StartingPointItemModel;

import java.util.ArrayList;

public class StartingPointRecyclerViewAdapter extends RecyclerView.Adapter<StartingPointRecyclerViewAdapter.ViewHolder>{

    private ArrayList<StartingPointItemModel> data;

    public StartingPointRecyclerViewAdapter(ArrayList<StartingPointItemModel> data) {
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
        holder.buildingName.setText(data.get(position).buildingName);
        holder.address.setText(data.get(position).address);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView buildingName;
        public TextView address;

        public ViewHolder(View itemView) {
            super(itemView);
            buildingName = (TextView)itemView.findViewById(R.id.buildingName);
            address = (TextView)itemView.findViewById(R.id.address);
        }
    }
}
