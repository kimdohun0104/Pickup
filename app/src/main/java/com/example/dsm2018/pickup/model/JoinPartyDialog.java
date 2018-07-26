package com.example.dsm2018.pickup.model;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.Button;

import com.example.dsm2018.pickup.R;

public class JoinPartyDialog extends Dialog{

    Button cancelButton, joinButton;

    public JoinPartyDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.join_party_dialog);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


    }
}
