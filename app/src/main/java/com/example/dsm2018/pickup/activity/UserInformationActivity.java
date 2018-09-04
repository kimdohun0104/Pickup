package com.example.dsm2018.pickup.activity;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.dialog.EmailDialog;
import com.example.dsm2018.pickup.dialog.PhoneNumberDialog;
import com.example.dsm2018.pickup.dialog.ProfileImageDialog;
import com.example.dsm2018.pickup.dialog.UserNameDialog;

public class UserInformationActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    ImageView profileImage;
    Button backButton, modifyUserInformation;
    CheckBox profileImageCheckBox, userNameCheckBox, phoneNumberCheckBox, emailCheckBox;
    FragmentManager fragmentManager = getSupportFragmentManager();
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        profileImageCheckBox = (CheckBox)findViewById(R.id.profileImageCheckBox);
        userNameCheckBox = (CheckBox)findViewById(R.id.userNameCheckBox);
        phoneNumberCheckBox = (CheckBox)findViewById(R.id.phoneNumberCheckBox);
        emailCheckBox = (CheckBox)findViewById(R.id.emailCheckBox);
        modifyUserInformation = (Button)findViewById(R.id.modifyUserInformation);
        backButton = (Button)findViewById(R.id.backButton);
        profileImage = (ImageView)findViewById(R.id.profileImage);

        profileImageCheckBox.setOnCheckedChangeListener(this);
        userNameCheckBox.setOnCheckedChangeListener(this);
        phoneNumberCheckBox.setOnCheckedChangeListener(this);
        emailCheckBox.setOnCheckedChangeListener(this);

        profileImage.setBackground(new ShapeDrawable(new OvalShape()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            profileImage.setClipToOutline(true);
        }
        profileImage.setImageResource(R.drawable.test);

        backButton.setOnClickListener(v -> finish());

        modifyUserInformation.setOnClickListener(v -> checkCheckBox());
    }

    public void checkCheckBox(){
        if(emailCheckBox.isChecked()){
            new EmailDialog().show(fragmentManager, "");
            emailCheckBox.setChecked(false);
        }
        if(phoneNumberCheckBox.isChecked()){
            new PhoneNumberDialog().show(fragmentManager, "");
            phoneNumberCheckBox.setChecked(false);
        }
        if(userNameCheckBox.isChecked()){
            new UserNameDialog().show(fragmentManager, "");
            userNameCheckBox.setChecked(false);
        }
        if(profileImageCheckBox.isChecked()){
            new ProfileImageDialog().show(fragmentManager, "");
            profileImageCheckBox.setChecked(false);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked)
            count++;
        else
            count--;
        if(count == 0)
            modifyUserInformation.setBackgroundResource(R.color.colorGrey_3);
        else
            modifyUserInformation.setBackgroundResource(R.color.colorPrimary);
    }
}
