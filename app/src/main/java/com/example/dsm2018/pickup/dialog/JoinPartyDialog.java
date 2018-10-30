package com.example.dsm2018.pickup.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.RetrofitHelp;
import com.example.dsm2018.pickup.RetrofitService;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class JoinPartyDialog {

    public JoinPartyDialog(Context context, String party_key) {
        this.context = context;
        this.party_key = party_key;
    }

    String party_key;

    Context context;

    Button cancelButton;
    Button joinPartyButton;

    SharedPreferences sharedPreferences;
    RetrofitService retrofitService;

    public void showDialog() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_join_party);

        sharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        retrofitService = new RetrofitHelp().retrofitService;

        cancelButton = dialog.findViewById(R.id.cancelButton);
        joinPartyButton = dialog.findViewById(R.id.joinPartyButton);

        cancelButton.setOnClickListener(v->dialog.dismiss());

        joinPartyButton.setOnClickListener(v->{
            Map<String, String> map = new HashMap();
            map.put("user_authorization", sharedPreferences.getString("user_authorization", ""));
            map.put("party_key", party_key);

            Call<Void> call = retrofitService.partyJoin(map);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    dialog.dismiss();
                    ((Activity)context).finish();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        });

        dialog.show();
    }
}
