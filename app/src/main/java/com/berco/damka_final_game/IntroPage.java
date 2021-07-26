package com.berco.damka_final_game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class IntroPage extends AppCompatActivity implements View.OnClickListener {
    private Button button1,  button2;
    private TextView tv1, tv2;
    private Intent musicIntent;
    private ImageView board;
    private SaveSettings s;


    private String[] permissions = new String[] {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.BATTERY_STATS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_page);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);

        tv1 = findViewById(R.id.text1);
        tv2 = findViewById(R.id.text2);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        board = findViewById(R.id.board);
        //this part creates animation for the picture
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.zoomin);
        board.startAnimation(animation);

        checkAndRequestPermissions();
        s = new SaveSettings(this);

        //this part is for background music
        musicIntent = new Intent(this, MusicServise.class);
        if(s.isMusic())
            startService(musicIntent);
    }
    private void checkAndRequestPermissions() {
        List<String> listPermissionsNeeded = new ArrayList<>();
        //check the permissions needed
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
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
    public void onClick(View view) {
        //chose where to move from this activity
        if (view == button1){
            Intent intent = new Intent(this, CurrentPlayers.class);
            startActivity(intent);
        }
        if(view == button2){
           Intent intent = new Intent(this, GamesList.class);
           startActivity(intent);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        //connecting the menu to the view
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
}