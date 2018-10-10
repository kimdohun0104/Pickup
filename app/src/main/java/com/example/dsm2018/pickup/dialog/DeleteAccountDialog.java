package com.example.dsm2018.pickup.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.RetrofitHelp;
import com.example.dsm2018.pickup.RetrofitService;
import com.example.dsm2018.pickup.model.SignoutRequest;

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

        cancelButton = (Button) dialog.findViewById(R.id.cancel_button);
        deleteAccountButton = (Button) dialog.findViewById(R.id.deleteAccountButton);

        cancelButton.setOnClickListener(v-> dialog.dismiss());

        deleteAccountButton.setOnClickListener(v->{
            retrofitService = new RetrofitHelp().retrofitService;

            Call<SignoutRequest> call = retrofitService.signout(new SignoutRequest(sharedPreferences.getString("user_authorization", "")));
            call.enqueue(new Callback<SignoutRequest>() {
                @Override
                public void onResponse(Call<SignoutRequest> call, Response<SignoutRequest> response) {
                    
                }

                @Override
                public void onFailure(Call<SignoutRequest> call, Throwable t) {

                }
            });
        });

        dialog.show();
    }
}
