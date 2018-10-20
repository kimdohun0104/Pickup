package com.example.dsm2018.pickup.dialog;

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

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneNumberDialog {

    public PhoneNumberDialog(Context context) {
        this.context = context;
    }

    Context context;

    EditText inputPhoneNumber;
    TextView errorText;
    Button nextButton, cancelButton;

    String phonePattern = "[0-9]-[0-9]-[0-9]";

    SharedPreferences sharedPreferences;
    RetrofitService retrofitService;

    public void showDialog() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_phone_number);

        retrofitService = new RetrofitHelp().retrofitService;
        sharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE);

        inputPhoneNumber = (EditText) dialog.findViewById(R.id.inputPhoneNumber);
        errorText = (TextView) dialog.findViewById(R.id.dialogError);
        nextButton = (Button) dialog.findViewById(R.id.nextButton);
        cancelButton = (Button) dialog.findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(v -> dialog.dismiss());

        nextButton.setOnClickListener(v -> {
            String number = inputPhoneNumber.getText().toString();
            if(number.length() == 13) {
                Map<String, String> map = new HashMap();
                map.put("user_authorization", sharedPreferences.getString("user_authorization", ""));
                map.put("modify_value", inputPhoneNumber.getText().toString());
                map.put("modify_info_type", "user_phone");

                Call<Void> call = retrofitService.modifyinfo(map);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            } else {
                errorText.setVisibility(View.VISIBLE);
                inputPhoneNumber.setBackgroundResource(R.drawable.round_layout_side_red);
            }
        });

        dialog.show();
    }
}
