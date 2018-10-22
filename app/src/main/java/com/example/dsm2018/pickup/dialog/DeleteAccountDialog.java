package com.example.dsm2018.pickup.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Button;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.RetrofitHelp;
import com.example.dsm2018.pickup.RetrofitService;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteAccountDialog {

    public DeleteAccountDialog(Context context) {
        this.context = context;
    }

    Context context;

    Button cancelButton;
    Button deleteAccountButton;

    RetrofitService retrofitService;

    SharedPreferences sharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE);

    public void showDialog() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_delete_account);

        cancelButton = dialog.findViewById(R.id.cancel_button);
        deleteAccountButton = dialog.findViewById(R.id.deleteAccountButton);

        cancelButton.setOnClickListener(v-> dialog.dismiss());

        deleteAccountButton.setOnClickListener(v->{
            retrofitService = new RetrofitHelp().retrofitService;

            Map<String, String> map = new HashMap();
            map.put("user_authorization", sharedPreferences.getString("user_authorization", ""));

            Call<Void> call = retrofitService.signout(map);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        });

        dialog.show();
    }
}
