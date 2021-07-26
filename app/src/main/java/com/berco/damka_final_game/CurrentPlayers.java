package com.berco.damka_final_game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.Locale;
import java.util.Timer;

public class CurrentPlayers extends AppCompatActivity implements View.OnClickListener, BackAble {

    private EditText name1, email1, name2, email2;
    private Button submit, chPic1, chPic2;
    private RadioGroup gender1, gender2;
    private ImageView pic1, pic2;
    private static final int REQUEST_IMAGE_CAPTURE1 = 1 ;
    private static final int REQUEST_IMAGE_CAPTURE2 = 2 ;
    private Bitmap mImageBitmap1, mImageBitmap2;
    private SoundControl sc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_players);

        name1 = findViewById(R.id.name1);
        email1 = findViewById(R.id.EmailAddress1);
        submit = findViewById(R.id.submit);
        gender1 = findViewById(R.id.gender1);
        pic1 = findViewById(R.id.pic1);
        chPic1 = findViewById(R.id.chPic1);
        name2 = findViewById(R.id.name2);
        email2 = findViewById(R.id.EmailAddress2);
        gender2 = findViewById(R.id.gender2);
        pic2 = findViewById(R.id.pic2);
        chPic2 = findViewById(R.id.chPic2);

        chPic2.setOnClickListener(this);
        submit.setOnClickListener(this);
        chPic1.setOnClickListener(this);
        //object that controls sound in the application
        sc = new SoundControl(this);


    }

    @Override
    public void onClick(View view) {
        if(view == submit) {
            //move values of both players through the intent
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("name1", name1.getText().toString());
            intent.putExtra("gender1","toaster");
            if (((RadioButton) gender1.getChildAt(0)).isChecked())
                intent.putExtra("gender1", "male");
            if (((RadioButton) gender1.getChildAt(1)).isChecked())
                intent.putExtra("gender1", "female");
            if (((RadioButton) gender1.getChildAt(2)).isChecked())
                intent.putExtra("gender1", "toaster");
            intent.putExtra("email1", email1.getText().toString());
            intent.putExtra("picMap1", mImageBitmap1);

            intent.putExtra("name2", name2.getText().toString());
            intent.putExtra("gender2","toaster");
            if (((RadioButton) gender2.getChildAt(0)).isChecked())
                intent.putExtra("gender2", "male");
            if (((RadioButton) gender2.getChildAt(1)).isChecked())
                intent.putExtra("gender2", "female");
            if (((RadioButton) gender2.getChildAt(2)).isChecked())
                intent.putExtra("gender2", "toaster");
            intent.putExtra("email2", email2.getText().toString());
            intent.putExtra("picMap2", mImageBitmap2);

            //playing sound for both players submmited
            sc.playTextSecondPlayer();
            intent.putExtra("bitmap1", mImageBitmap1);
            intent.putExtra("bitmap2", mImageBitmap2);

            startActivity(intent);

        }
        if(view == chPic1){
            //start camera for first player's picture
            Intent CamIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(CamIntent,REQUEST_IMAGE_CAPTURE1);
        }
        if(view == chPic2){
            //start camera for second player's picture
            Intent CamIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(CamIntent,REQUEST_IMAGE_CAPTURE2);
        }

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==  REQUEST_IMAGE_CAPTURE1 && resultCode == RESULT_OK){
            //result for first player of camera
            mImageBitmap1 = (Bitmap) data.getExtras().get("data");
            pic1.setImageBitmap(mImageBitmap1);
        }

        if(requestCode ==  REQUEST_IMAGE_CAPTURE2 && resultCode == RESULT_OK){
            //result for second player of camera
            mImageBitmap2 = (Bitmap) data.getExtras().get("data");
            pic2.setImageBitmap(mImageBitmap2);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        //connect menu to this specific page
        getMenuInflater().inflate(R.menu.menuitem, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //choosing the activity to move to by the option from the menu
        if(item.getItemId() == R.id.menu_settings){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        if(item.getItemId() == R.id.menu_share){
            Intent intent = new Intent(this,ShareActivity.class);
            startActivity(intent);
            return true;
        }

        if(item.getItemId() == R.id.game_rules){
            Intent intent = new Intent(this,GameRules.class);
            startActivity(intent);
            return true;
        }

        return false;
    }


    public void onClickBack(View view) {
        finish();
    }
}