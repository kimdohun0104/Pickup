package com.example.dsm2018.pickup.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class PhoneNumberDialog {

    public PhoneNumberDialog(Context context) {
        this.context = context;
    }

    Context context;

    EditText inputPhoneNumber;
    TextView errorText;
    Button nextButton, cancelButton;

    SharedPreferences sharedPreferences;
    RetrofitService retrofitService;

    public void showDialog() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_phone_number);

        retrofitService = new RetrofitHelp().retrofitService;
        sharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE);

        inputPhoneNumber = dialog.findViewById(R.id.inputPhoneNumber);
        errorText = dialog.findViewById(R.id.dialogError);
        nextButton = dialog.findViewById(R.id.nextButton);
        cancelButton = dialog.findViewById(R.id.cancelButton);

        inputPhoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        cancelButton.setOnClickListener(v -> dialog.dismiss());

        nextButton.setOnClickListener(v -> {
            String number = inputPhoneNumber.getText().toString();
            if(number.length() == 13) {
                Map<String, String> map = new HashMap();
                map.put("user_authorization", sharedPreferences.getString("user_authorization", ""));
                map.put("modify_value", inputPhoneNumber.getText().toString().trim());
                map.put("modify_info_type", "user_phone");

                Call<Void> call = retrofitService.modifyinfo(map);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.code() == 200) {
                            UserInformation.getInstance().user_phone = inputPhoneNumber.getText().toString().trim();
                            UserInformationActivity.userPhoneNumber.setText(inputPhoneNumber.getText().toString().trim());
                            dialog.dismiss();
                        } else if(response.code() == 500) {
                            Toast.makeText(context, "서버 오류가 발생하였습니다. 잠시후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                        }
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
