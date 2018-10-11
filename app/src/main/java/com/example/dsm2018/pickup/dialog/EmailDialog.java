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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.RetrofitHelp;
import com.example.dsm2018.pickup.RetrofitService;
import com.example.dsm2018.pickup.UserInformation;
import com.example.dsm2018.pickup.model.ModifyinfoRequest;

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

        nextButton = (Button)dialog.findViewById(R.id.nextButton);
        inputEmail = (EditText)dialog.findViewById(R.id.inputEmail);
        errorText = (TextView)dialog.findViewById(R.id.dialogError);
        cancelButton = (Button)dialog.findViewById(R.id.cancelButton);

        nextButton.setOnClickListener(v -> {
            if(inputEmail.getText().toString().matches(emailPattern)){
                Call<ModifyinfoRequest> call = retrofitService.modifyinfo(
                        new ModifyinfoRequest(sharedPreferences.getString("user_authorization", ""), inputEmail.getText().toString(), "user_email"));
                call.enqueue(new Callback<ModifyinfoRequest>() {
                    @Override
                    public void onResponse(Call<ModifyinfoRequest> call, Response<ModifyinfoRequest> response) {
                        if(response.code() == 200) {
                            UserInformation.getInstance().user_email = inputEmail.getText().toString();
                        }
                    }

                    @Override
                    public void onFailure(Call<ModifyinfoRequest> call, Throwable t) {

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
