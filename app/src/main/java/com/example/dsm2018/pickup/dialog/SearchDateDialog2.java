package com.example.dsm2018.pickup.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.example.dsm2018.pickup.R;

public class SearchDateDialog2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_search_date_dialog2);
    }
}
