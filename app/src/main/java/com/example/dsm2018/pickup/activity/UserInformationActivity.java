package com.example.dsm2018.pickup.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.dialog.EmailDialog;
import com.example.dsm2018.pickup.dialog.PhoneNumberDialog;
import com.example.dsm2018.pickup.dialog.ProfileImageDialog;
import com.example.dsm2018.pickup.dialog.UserNameDialog;
import com.facebook.AccessToken;
import com.facebook.Profile;

import java.io.IOException;

public class UserInformationActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    ImageView profileImage;
    Button backButton, modifyUserInformation;
    CheckBox profileImageCheckBox, userNameCheckBox, phoneNumberCheckBox, emailCheckBox;
    TextView userName, userPhoneNumber, userEmail;

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
        userName = (TextView) findViewById(R.id.userName);
        userPhoneNumber = (TextView) findViewById(R.id.userPhoneNumber);
        userEmail = (TextView) findViewById(R.id.userEmail);

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
            new EmailDialog(UserInformationActivity.this).showDialog();
        }
        if(phoneNumberCheckBox.isChecked()){
            new PhoneNumberDialog(UserInformationActivity.this).showDialog();
        }
        if(userNameCheckBox.isChecked()){
            new UserNameDialog(UserInformationActivity.this).showDialog();
        }
        if(profileImageCheckBox.isChecked()){
            showImageDialog();
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

    Button cancelButton, nextButton;
    LinearLayout changeImage;
    ImageView dialogProfileImage;
    Bitmap bitmap;

    int exifDegree;

    private final int REQUEST_PERMISSION_CODE = 101;
    private final int GALLERY_CODE=1112;

    public void showImageDialog() {
        Dialog dialog = new Dialog(UserInformationActivity.this);
        dialog.setContentView(R.layout.dialog_profile_image);

        ActivityCompat.requestPermissions(UserInformationActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_PERMISSION_CODE);

        nextButton = (Button)dialog.findViewById(R.id.nextButton);
        dialogProfileImage = (ImageView)dialog.findViewById(R.id.profileImage);
        changeImage = (LinearLayout)dialog.findViewById(R.id.changeImage);
        cancelButton = (Button)dialog.findViewById(R.id.cancelButton);

        changeImage.setOnClickListener(v-> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY_CODE);
        });

        dialogProfileImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY_CODE);
        });

        cancelButton.setOnClickListener(v-> dialog.dismiss());

        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try{
            sendPicture(data.getData());
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void sendPicture(Uri imgUri){
        String imagePath = getRealPathFromURI(imgUri); // path 경로
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        exifDegree = exifOrientationToDegrees(exifOrientation);

        bitmap = BitmapFactory.decodeFile(imagePath);//경로를 통해 비트맵으로 전환
        changeImage.setVisibility(View.INVISIBLE);
        dialogProfileImage.setImageBitmap(rotate(bitmap, exifDegree));
    }

    private String getRealPathFromURI(Uri contentUri) {
        int column_index=0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getApplicationContext().getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        return cursor.getString(column_index);
    }

    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    private Bitmap rotate(Bitmap src, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }
}
