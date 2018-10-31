package com.example.dsm2018.pickup.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.TextView;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.RetrofitHelp;
import com.example.dsm2018.pickup.RetrofitService;
import com.example.dsm2018.pickup.UserInformation;
import com.example.dsm2018.pickup.activity.UserInformationActivity;

import java.util.HashMap;
import java.util.Map;

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

        cancelButton = dialog.findViewById(R.id.cancelButton);
        nextButton = dialog.findViewById(R.id.nextButton);
        inputName = dialog.findViewById(R.id.inputName);

        cancelButton.setOnClickListener(v-> dialog.dismiss());

        nextButton.setOnClickListener(v-> {
            if(!inputName.getText().toString().equals("")) {
                Map<String, String> map = new HashMap();
                map.put("user_authorization", sharedPreferences.getString("user_authorization", ""));
                map.put("modify_value", inputName.getText().toString());
                map.put("modify_info_type", "user_name");


                Call<Void> call = retrofitService.modifyinfo(map);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.code() == 200) {
                            UserInformation.getInstance().user_name = inputName.getText().toString();
                            UserInformationActivity.userName.setText(inputName.getText().toString());
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });

        dialog.show();
    }
}
