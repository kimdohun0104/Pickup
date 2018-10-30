package com.example.dsm2018.pickup.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Button;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.RetrofitHelp;
import com.example.dsm2018.pickup.RetrofitService;
import com.facebook.share.Share;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PartyDeleteDialog {

    public PartyDeleteDialog(Context context, String party_key) {
        this.context = context;
        this.party_key = party_key;
    }

    String party_key;

    Context context;

    Button cancelButton;
    Button partyDeleteButton;

    SharedPreferences sharedPreferences;
    RetrofitService retrofitService;

    public void showDialog() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_party_delete);

        sharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        retrofitService = new RetrofitHelp().retrofitService;

        cancelButton = dialog.findViewById(R.id.cancelButton);
        partyDeleteButton = dialog.findViewById(R.id.partyDeleteButton);

        partyDeleteButton.setOnClickListener(v->{
            Map<String, String> map = new HashMap();
            map.put("user_authorization", sharedPreferences.getString("user_authorization", ""));
            map.put("party_key", party_key);

            Call<Void> call = retrofitService.partyDelete(map);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.code() == 200) {
                        dialog.dismiss();
                        ((Activity)context).finish();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        });

        cancelButton.setOnClickListener(v-> {
            dialog.dismiss();
        });

        dialog.show();
    }
}
