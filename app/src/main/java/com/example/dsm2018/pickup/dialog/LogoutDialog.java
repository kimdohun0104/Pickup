package com.example.dsm2018.pickup.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.activity.LoginActivity;
import com.example.dsm2018.pickup.activity.MainActivity;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;

public class LogoutDialog {

    public LogoutDialog(Context context) {
        this.context = context;
    }

    Context context;

    Button cancelButton;
    Button logoutButton;

    public void showDialog() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_logout);

        cancelButton = (Button) dialog.findViewById(R.id.cancelButton);
        logoutButton = (Button) dialog.findViewById(R.id.logoutButton);

        cancelButton.setOnClickListener(v->dialog.dismiss());

        logoutButton.setOnClickListener(v->{
            disconnectFromFacebook();
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        });

        dialog.show();
    }

    private void disconnectFromFacebook() {
        if(AccessToken.getCurrentAccessToken() == null) {
            return;
        }
        LoginManager.getInstance().logOut();
    }
}
