package com.example.dsm2018.pickup.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.dsm2018.pickup.R;

public class PartyDeleteDialogFragment extends DialogFragment{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_party_delete, container, false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return view;
    }
}
