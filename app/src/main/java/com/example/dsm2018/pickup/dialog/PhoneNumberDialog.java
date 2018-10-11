package com.example.dsm2018.pickup.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.RetrofitHelp;
import com.example.dsm2018.pickup.RetrofitService;
import com.example.dsm2018.pickup.UserInformation;
import com.example.dsm2018.pickup.model.ModifyinfoRequest;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
                Call<ModifyinfoRequest> call = retrofitService.modifyinfo(
                        new ModifyinfoRequest(sharedPreferences.getString("user_authorization", ""), inputPhoneNumber.getText().toString(), "user_phone"));
                call.enqueue(new Callback<ModifyinfoRequest>() {
                    @Override
                    public void onResponse(Call<ModifyinfoRequest> call, Response<ModifyinfoRequest> response) {
                        if(response.code() == 200) {
                            UserInformation.getInstance().user_phone = inputPhoneNumber.getText().toString();
                        }
                    }

                    @Override
                    public void onFailure(Call<ModifyinfoRequest> call, Throwable t) {

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
