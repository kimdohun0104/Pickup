package com.example.dsm2018.pickup.dialog;

import android.app.Dialog;
import android.content.Context;
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

public class EmailDialog {

    public EmailDialog(Context context) {
        this.context = context;
    }

    Context context;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Button nextButton, cancelButton;
    EditText inputEmail;
    TextView errorText;

    public void showDialog() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_email);

        nextButton = (Button)dialog.findViewById(R.id.nextButton);
        inputEmail = (EditText)dialog.findViewById(R.id.inputEmail);
        errorText = (TextView)dialog.findViewById(R.id.dialogError);
        cancelButton = (Button)dialog.findViewById(R.id.cancelButton);

        nextButton.setOnClickListener(v -> {
            if(inputEmail.getText().toString().matches(emailPattern)){

            } else {
                errorText.setVisibility(View.VISIBLE);
                inputEmail.setBackgroundResource(R.drawable.round_layout_side_red);
            }
        });

        cancelButton.setOnClickListener(v->dialog.dismiss());

        dialog.show();
    }
}
