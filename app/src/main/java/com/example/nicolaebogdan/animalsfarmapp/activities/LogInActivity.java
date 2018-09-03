package com.example.nicolaebogdan.animalsfarmapp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nicolaebogdan.animalsfarmapp.R;

import java.io.ByteArrayOutputStream;

public class LogInActivity extends AppCompatActivity {

    Button nextBtn;
    Button takePhoto;
    EditText username;
    SharedPreferences.Editor prefEditor;
    SharedPreferences preferences;
    Bitmap saveImage;

    public static final int REQUEST_CODE = 1;
    private static final int MY_CAMERA_REQUEST_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);



        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(preferences.contains("userName") ){
            Intent intent = new Intent(LogInActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        username = (EditText)findViewById(R.id.username_text_view);

        prefEditor = preferences.edit();


        nextBtn = findViewById(R.id.next_button_view);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogInActivity.this,MainActivity.class);
                Toast.makeText(LogInActivity.this,username.getText().toString(),Toast.LENGTH_LONG).show();

                prefEditor.putString("userName", username.getText().toString());
                prefEditor.apply();
                startActivity(intent);
                finish();
            }
        });

        takePhoto = findViewById(R.id.take_photo_button_view);

        takePhoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {



                // Here, thisActivity is the current activity
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    // Permission is not granted
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(LogInActivity.this,
                            Manifest.permission.CAMERA)) {
                        Toast.makeText(LogInActivity.this,"You don't have permissions",Toast.LENGTH_SHORT).show();
                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(LogInActivity.this,new String[]{Manifest.permission.CAMERA},MY_CAMERA_REQUEST_CODE);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                } else {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, REQUEST_CODE);
                }



            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE &&  resultCode == RESULT_OK && data.hasExtra("data")){

            saveImage = (Bitmap) data.getExtras().get("data");

            if (saveImage != null) {

                prefEditor.putString("Photo",encodeTobase64(saveImage));
                prefEditor.apply();
            }

        } else { // Result was a failure
            Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
        }

    }

    // method for bitmap to base64
    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }
}