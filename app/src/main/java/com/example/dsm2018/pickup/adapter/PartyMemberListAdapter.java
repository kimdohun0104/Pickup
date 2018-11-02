package com.example.dsm2018.pickup.adapter;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.model.PartyDetailResponse;

import java.util.ArrayList;

public class PartyMemberListAdapter extends RecyclerView.Adapter<PartyMemberListAdapter.ViewHolder>{
    public PartyMemberListAdapter(ArrayList<PartyDetailResponse> data) {
        this.data = data;
    }

    ArrayList<PartyDetailResponse> data;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_party_member, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.profileImage.setImageResource(R.drawable.flower);
        holder.emailText.setText(data.get(position).user_email);
        holder.nameText.setText(data.get(position).user_name);
        holder.profileImage.setBackground(new ShapeDrawable(new OvalShape()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.profileImage.setClipToOutline(true);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView profileImage;
        public TextView nameText;
        public TextView emailText;
        public ViewHolder(View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profileImage);
            nameText = itemView.findViewById(R.id.nameText);
            emailText = itemView.findViewById(R.id.emailText);
        }
    }
}
