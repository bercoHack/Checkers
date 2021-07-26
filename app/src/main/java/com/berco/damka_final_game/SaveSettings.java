package com.berco.damka_final_game;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveSettings {
     private final String FN = "Settings2";
     private final String VIB = "kVibrate";
     private final String SOUND = "kSound";
     private final String MUSIC = "kMusic";


     private boolean bSound, bVibrate, bMusic;
     private SharedPreferences sp;

     public SaveSettings(Context context){
         //getting past setting from file
         sp = context.getSharedPreferences(FN, Context.MODE_PRIVATE);
         bVibrate = sp.getBoolean(VIB, true);
         bSound = sp.getBoolean(SOUND, true);
         bMusic = sp.getBoolean(MUSIC, true);
     }

     public boolean isSound(){return bSound;}
     public boolean isVibrate(){return bVibrate;}
     public boolean isMusic(){return bMusic;}

     public void save(){
         //saving new setting to file
         SharedPreferences.Editor editor = sp.edit();
         editor.putBoolean(VIB, bVibrate);
         editor.putBoolean(SOUND, bSound);
         editor.putBoolean(MUSIC, bMusic);
         editor.commit();

     }

     public void setVibration(boolean checked) {
         bVibrate = checked;
    }

     public void setSound(boolean checked) {
        bSound = checked;
    }

     public void setMusic(boolean checked) {
         bMusic = checked;
     }
}
