package com.example.dsm2018.pickup.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.dialog.SearchDateDialog;
import com.example.dsm2018.pickup.dialog.SearchTimeDialog;

public class CreatePartyActivity extends AppCompatActivity {

    TextView startingPointText, endPointText;
    EditText titleEdit, contentEdit;
    TextView setDateButton, setTimeButton, numberOfPeopleText;
    Button addPersonnel, reductionPersonnel;

    int personnelCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_party);

        startingPointText = (TextView)findViewById(R.id.startingPointText);
        endPointText = (TextView)findViewById(R.id.endPointText);
        numberOfPeopleText = (TextView)findViewById(R.id.numberOfPeopleText);
        addPersonnel = (Button)findViewById(R.id.addPersonnel);
        reductionPersonnel = (Button)findViewById(R.id.reductionPersonnel);
        titleEdit = (EditText)findViewById(R.id.titleEdit);
        contentEdit = (EditText)findViewById(R.id.contentEdit);
        setDateButton = (TextView)findViewById(R.id.setDateButton);
        setTimeButton = (TextView)findViewById(R.id.setTimeButton);

        FragmentManager fragmentManager = getSupportFragmentManager();

        Intent intent = getIntent();
        startingPointText.setText(intent.getExtras().getString("startingPointName"));
        endPointText.setText(intent.getExtras().getString("endPointName"));

        setDateButton.setOnClickListener(v->{
            new SearchDateDialog().show(fragmentManager, "searchDateDialog");
        });

        setTimeButton.setOnClickListener(v->{
            new SearchTimeDialog().show(fragmentManager, "searchTimeDialog");
        });

        addPersonnel.setOnClickListener(v->{
            if(personnelCount > 0 && personnelCount < 5) {
                personnelCount++;
                numberOfPeopleText.setText(personnelCount + "명");
                numberOfPeopleText.setTextColor(getResources().getColor(R.color.colorTextBlack));
                numberOfPeopleText.setBackgroundResource(R.drawable.round_layout_side_orange);
            }
        });
    }
}
