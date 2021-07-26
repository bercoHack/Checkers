package com.berco.damka_final_game;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;


public class SettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private Switch sound, vibration, music;
    private SaveSettings settings;
    private ImageView picSetting;
    private SoundControl sc;
    private MusicServise ms;
    private Vibrator vibrator;
    private Intent musicIntent;
    private boolean startMusic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settings = new SaveSettings(this);
        sc = new SoundControl(this);
        ms = new MusicServise();
        startMusic = false;
        picSetting = findViewById(R.id.picSetting);

        //those lines are for animation
        Animation animation= AnimationUtils.loadAnimation(this, R.anim.rotate);
        picSetting.startAnimation(animation);

        sound = (Switch)findViewById(R.id.sound);
        vibration = (Switch)findViewById(R.id.vibration);
        music = (Switch)findViewById(R.id.music);
        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);

        musicIntent = new Intent(this, MusicServise.class);

        sound.setChecked(settings.isSound());
        vibration.setChecked(settings.isVibrate());
        music.setChecked(settings.isMusic());

        sc.setDisabled(sound.isChecked());
        vibration.setOnCheckedChangeListener(this);
        sound.setOnCheckedChangeListener(this);
        music.setOnCheckedChangeListener(this);
        musicIntent = new Intent(this, MusicServise.class);
        stopService(musicIntent);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        //handling events of checked changed in switches
        if(compoundButton == sound){
           settings.setSound(b);
           sc.setDisabled(b);
       }
        if(compoundButton == music){
            settings.setMusic(b);
            startMusic = b;
        }
        if(compoundButton == vibration){
            settings.setVibration(b);
        }
    }

    public void ok(View view) {
        //submitting the values of the settings
        sc.playTextSaved();
        if(startMusic)
            startService(musicIntent);
        if(settings.isVibrate())
            vibrator.vibrate(100);
        settings.save();
        finish();
    }


}