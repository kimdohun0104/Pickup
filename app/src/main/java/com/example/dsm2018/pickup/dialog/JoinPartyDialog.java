package com.example.dsm2018.pickup.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.example.dsm2018.pickup.R;

public class JoinPartyDialog {

    public JoinPartyDialog(Context context) {
        this.context = context;
    }

    Context context;

    Button cancelButton;
    Button joinPartyButton;

    void showDialog() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_join_party);

        cancelButton = (Button) dialog.findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(v->dialog.dismiss());

        dialog.show();
    }
}
