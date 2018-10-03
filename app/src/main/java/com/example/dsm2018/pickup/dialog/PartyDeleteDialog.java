package com.example.dsm2018.pickup.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;

import com.example.dsm2018.pickup.R;

public class PartyDeleteDialog {

    public PartyDeleteDialog(Context context) {
        this.context = context;
    }

    Context context;

    Button cancelButton;
    Button partyDeleteButton;

    void showDialog() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_party_delete);

        cancelButton = (Button) dialog.findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(v->dialog.dismiss());

        dialog.show();
    }
}
