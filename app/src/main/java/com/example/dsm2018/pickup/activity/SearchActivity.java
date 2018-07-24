package com.example.dsm2018.pickup.activity;

import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.fragment.BottomSheetFragment;

public class SearchActivity extends AppCompatActivity {

    FloatingActionButton filterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        filterButton = (FloatingActionButton)findViewById(R.id.filterButton);

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment bottomSheetFragment = BottomSheetFragment.getInstance();

                bottomSheetFragment.show(getSupportFragmentManager(), "fragment_bottom_sheet");
            }
        });
    }
}
