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
import android.widget.TextView;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.RetrofitHelp;
import com.example.dsm2018.pickup.RetrofitService;
import com.example.dsm2018.pickup.UserInformation;
import com.example.dsm2018.pickup.model.ModifyinfoRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserNameDialog {

    public UserNameDialog(Context context) {
        this.context = context;
    }

    Context context;

    Button cancelButton, nextButton;
    TextView inputName;

    RetrofitService retrofitService;
    SharedPreferences sharedPreferences;

    public void showDialog() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_user_name);

        sharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        retrofitService = new RetrofitHelp().retrofitService;

        cancelButton = (Button) dialog.findViewById(R.id.cancelButton);
        nextButton = (Button) dialog.findViewById(R.id.nextButton);
        inputName = (TextView) dialog.findViewById(R.id.inputName);

        cancelButton.setOnClickListener(v-> dialog.dismiss());

        nextButton.setOnClickListener(v-> {
            if(!inputName.getText().toString().equals("")) {
                Call<ModifyinfoRequest>  call = retrofitService.modifyinfo(
                        new ModifyinfoRequest(sharedPreferences.getString("user_authorization", ""), inputName.getText().toString(), "user_name"));

                call.enqueue(new Callback<ModifyinfoRequest>() {
                    @Override
                    public void onResponse(Call<ModifyinfoRequest> call, Response<ModifyinfoRequest> response) {
                        if(response.code() == 200) {
                            UserInformation.getInstance().user_name = inputName.getText().toString();
                        }
                    }

                    @Override
                    public void onFailure(Call<ModifyinfoRequest> call, Throwable t) {

                    }
                });
            }


        });

        dialog.show();
    }
}
