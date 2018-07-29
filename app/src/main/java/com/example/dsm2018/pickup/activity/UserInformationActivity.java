package com.example.dsm2018.pickup.activity;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.dsm2018.pickup.R;

public class UserInformationActivity extends AppCompatActivity {

    ImageView profileImage;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        backButton = (Button)findViewById(R.id.backButton);
        profileImage = (ImageView)findViewById(R.id.profileImage);
        profileImage.setBackground(new ShapeDrawable(new OvalShape()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            profileImage.setClipToOutline(true);
        }
        profileImage.setImageResource(R.drawable.test);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
