package com.example.dsm2018.pickup.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dsm2018.pickup.R;

public class SearchStartingPointActivity extends AppCompatActivity {

    Button backButton;
    EditText inputStartingPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_starting_point);

        inputStartingPoint = (EditText)findViewById(R.id.inputStartingPoint);
        backButton = (Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
}
