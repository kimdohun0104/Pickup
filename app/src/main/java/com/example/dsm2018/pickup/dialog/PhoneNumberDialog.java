package com.example.dsm2018.pickup.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.dsm2018.pickup.R;

public class PhoneNumberDialog extends DialogFragment {

    EditText inputPhoneNumber;

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
        View view = inflater.inflate(R.layout.dialog_phone_number, container, false);

        inputPhoneNumber = (EditText)view.findViewById(R.id.inputPhoneNumber);
        inputPhoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        return view;
    }
}
