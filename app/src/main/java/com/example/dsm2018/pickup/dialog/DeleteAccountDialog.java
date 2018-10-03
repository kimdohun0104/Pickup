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

import com.example.dsm2018.pickup.R;

public class DeleteAccountDialog {

    public DeleteAccountDialog(Context context) {
        this.context = context;
    }

    Context context;

    Button cancelButton;
    Button deleteAccountButton;

    public void showDialog() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_delete_account);

        cancelButton = (Button) dialog.findViewById(R.id.cancel_button);

        cancelButton.setOnClickListener(v-> dialog.dismiss());

        dialog.show();
    }
}
