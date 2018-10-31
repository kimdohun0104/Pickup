package com.example.dsm2018.pickup.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class EmailDialog {

    public EmailDialog(Context context) {
        this.context = context;
    }

    Context context;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Button nextButton, cancelButton;
    EditText inputEmail;
    TextView errorText;

    RetrofitService retrofitService;
    SharedPreferences sharedPreferences;


    public void showDialog() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_email);

        sharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        retrofitService = new RetrofitHelp().retrofitService;

        nextButton = dialog.findViewById(R.id.nextButton);
        inputEmail = dialog.findViewById(R.id.inputEmail);
        errorText = dialog.findViewById(R.id.dialogError);
        cancelButton = dialog.findViewById(R.id.cancelButton);

        nextButton.setOnClickListener(v -> {
            if(inputEmail.getText().toString().matches(emailPattern)){
                Map<String, String> map = new HashMap();
                map.put("user_authorization", sharedPreferences.getString("user_authorization", ""));
                map.put("modify_value", inputEmail.getText().toString());
                map.put("modify_info_type", "user_email");

                Call<Void> call = retrofitService.modifyinfo(map);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.code() == 200) {
                            UserInformation.getInstance().user_email = inputEmail.getText().toString();
                            UserInformationActivity.userEmail.setText(inputEmail.getText().toString());
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            } else {
                errorText.setVisibility(View.VISIBLE);
                inputEmail.setBackgroundResource(R.drawable.round_layout_side_red);
            }
        });

        cancelButton.setOnClickListener(v->dialog.dismiss());

        dialog.show();
    }
}
