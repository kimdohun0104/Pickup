package com.example.dsm2018.pickup.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.activity.MainActivity;
import com.example.dsm2018.pickup.activity.SearchDestinationActivity;
import com.example.dsm2018.pickup.fragment.CreatePartyFragment;
import com.example.dsm2018.pickup.model.SearchDestinationModel;

import java.util.ArrayList;

public class SearchDestinationAdapter extends RecyclerView.Adapter<SearchDestinationAdapter.ViewHolder> {

    ArrayList<SearchDestinationModel> data;
    Context context;

    public SearchDestinationAdapter(ArrayList<SearchDestinationModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_destination, parent, false);

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
            titleText = (TextView)itemView.findViewById(R.id.titleText);
            addressText = (TextView)itemView.findViewById(R.id.addressText);
        }
    }


}
