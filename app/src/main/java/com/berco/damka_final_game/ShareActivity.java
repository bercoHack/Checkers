package com.berco.damka_final_game;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShareActivity extends AppCompatActivity implements View.OnClickListener, BackAble {

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private EditText et1;


    private String[] permissions = new String[] {
            Manifest.permission.SEND_SMS,
            Manifest.permission.CALL_PHONE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        et1 = findViewById(R.id.et1);
        btn3 = findViewById(R.id.btn3);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);

        checkAndRequestPermissions();

    }


    public void onClick(View view) {
        //contacting the creator witch several ways like email, sms and cellPhone
        if(btn1==view) {
            String message = et1.getText().toString();
            String phoneNumber="0523474106";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
            intent.putExtra("sms_body", message);
            startActivity(intent);
        }
        if(btn2==view) {
            String []emails = {"guy.berco2003@gmail.com"};
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_EMAIL, emails);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Message to the creator of Damka_final_game");
            intent.putExtra(Intent.EXTRA_TEXT, et1.getText());
            startActivity(Intent.createChooser(intent, "Send Email"));
        }
        if(btn3==view){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            Uri data = Uri.parse("tel:" + "52-3474106");
            intent.setData(data);
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED)
                startActivity(intent);
        }

    }


    private void checkAndRequestPermissions() {
        List<String> listPermissionsNeeded = new ArrayList<>();
        //check the permissions needed
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) !=
                    PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        //request the ones needed and don't have yet
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        }
    }

    @Override
    public void onClickBack(View view) {
        finish();
    }
}
