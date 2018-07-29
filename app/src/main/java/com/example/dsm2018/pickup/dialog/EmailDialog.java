package com.example.dsm2018.pickup.dialog;

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

public class EmailDialog extends DialogFragment {

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Button nextButton;
    EditText inputEmail;
    TextView errorText;

    @Override
    public void onResume() {
        super.onResume();
        int width = getResources().getDimensionPixelSize(R.dimen.dialog_width);
        int height = getResources().getDimensionPixelSize(R.dimen.dialog_height);
        getDialog().getWindow().setLayout(width, height);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_email, container, false);

        nextButton = (Button)view.findViewById(R.id.nextButton);
        inputEmail = (EditText)view.findViewById(R.id.inputEmail);
        errorText = (TextView)view.findViewById(R.id.dialogError);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputEmail.getText().toString().matches(emailPattern)){

                } else {
                    errorText.setVisibility(View.VISIBLE);
                    inputEmail.setBackgroundResource(R.drawable.round_layout_side_red);
                }
            }
        });

        return view;
    }
}
