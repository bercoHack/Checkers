package com.berco.damka_final_game;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class SoundControl {
    private SoundPool soundpool;
    private final int bombid;
    private SaveSettings s;
    private static boolean disabled;
    private TextToSpeech tts;

    public SoundControl(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundpool = new SoundPool.Builder().setMaxStreams(20).build();
        }
        soundpool = new SoundPool(20,AudioManager.STREAM_MUSIC,1);
        disabled = true;
        s = new SaveSettings(context);
        disabled = s.isSound();
        bombid = soundpool.load(context,R.raw.sound,1);


        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.US);
                }
            }
        });
    }

    public void playBomb(){
        //option for sound, not used
        if(disabled)
            soundpool.play(bombid,1,1,1,0,1);
    }

    public void playTextSaved() {
        //sound "saved"
        if(disabled)
            tts.speak("saved!", TextToSpeech.QUEUE_FLUSH, null);
    }

    public void playTextSecondPlayer() {
        //sound "both players submitted, starting!"
        if(disabled)
            tts.speak("both players submitted, starting!", TextToSpeech.QUEUE_FLUSH, null);
    }

    public void setDisabled(boolean b) {
        disabled = b;
    }
}
