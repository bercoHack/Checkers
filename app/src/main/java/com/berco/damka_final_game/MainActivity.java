package com.berco.damka_final_game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Locale;

import static com.berco.damka_final_game.Model.SIZE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, BackAble {
    private Controller c;
    private RelativeLayout[] rows;
    private Dialog d;
    private Switch flag;
    private Button sub;
    private Intent musicIntent;
    private int firstPlace;
    private ImageButton firstView;
    private LinearLayout turnOf;

    //broadcast receiver for phone calls
    BroadcastReceiver phoneCallRec = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (state.equals("RINGING")) {
                createPhoneCallDialog();
            }
            if (state.equals("IDLE")) {
                d.dismiss();
            }
        }
    };

    //broadcast receiver for battery changes to low
    BroadcastReceiver broadCastBatteryLow = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            createBatteryLowDialog();
            new CountDownTimer(3000, 1000) {

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    d.dismiss();
                }
            }.start();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        c = new Controller();
        Intent temp = getIntent();

        //get values past to this activity by intent
        Player p1 = new Player(temp.getStringExtra("name1"), temp.getStringExtra("email1"), temp.getStringExtra("gender1"));
        c.setP1(p1.toString());
        Player p2 = new Player(temp.getStringExtra("name2"), temp.getStringExtra("email2"), temp.getStringExtra("gender2"));
        c.setP2(p2.toString());

        //find all 8 relative layouts of the board
        rows = new RelativeLayout[8];
        rows[0] = findViewById(R.id.rl1);
        rows[1] = findViewById(R.id.rl2);
        rows[2] = findViewById(R.id.rl3);
        rows[3] = findViewById(R.id.rl4);
        rows[4] = findViewById(R.id.rl5);
        rows[5] = findViewById(R.id.rl6);
        rows[6] = findViewById(R.id.rl7);
        rows[7] = findViewById(R.id.rl8);

        //registering the receivers
        this.registerReceiver(phoneCallRec, new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED));
        this.registerReceiver(broadCastBatteryLow, new IntentFilter(Intent.ACTION_BATTERY_LOW));

        //value that will help determinate if this is the first or second click and were the clicks are
        firstPlace = -1;

        //object for playing music
        musicIntent = new Intent(this, MusicServise.class);

        //putting values of 2 players in the places near the board
        LinearLayout ilBlack = findViewById(R.id.p1Layout);
        LinearLayout ilBlackIn = ilBlack.findViewById(R.id.p1LayoutIn);
        if(temp.getExtras().get("bitmap2") != null)
            ((ImageView)ilBlack.findViewById(R.id.pic1)).setImageBitmap((Bitmap)temp.getExtras().get("bitmap2"));
        ((TextView)ilBlackIn.findViewById(R.id.blackPlayerName)).setText(temp.getStringExtra("name2"));
        ((TextView)ilBlackIn.findViewById(R.id.blackPlayerGender)).setText(temp.getStringExtra("gender2"));
        ilBlack.findViewById(R.id.blackTurn).setVisibility(View.INVISIBLE);

        LinearLayout ilWhite = findViewById(R.id.p2layout);
        LinearLayout ilWhiteIn = ilWhite.findViewById(R.id.p2layoutIn);
        if(temp.getExtras().get("bitmap1") != null)
            ((ImageView)ilWhite.findViewById(R.id.pic2)).setImageBitmap((Bitmap)temp.getExtras().get("bitmap1"));
        ((TextView)ilWhiteIn.findViewById(R.id.whitePlayerName)).setText(temp.getStringExtra("name1"));
        ((TextView)ilWhiteIn.findViewById(R.id.whitePlayerGender)).setText(temp.getStringExtra("gender1"));
        ilWhite.findViewById(R.id.whiteTurn).setVisibility(View.VISIBLE);
        turnOf = ilWhite;
    }

    public boolean onCreateOptionsMenu(Menu menu){
        //connecting the menu to the view
        getMenuInflater().inflate(R.menu.menuitem, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        //on click listener for the button in the end game dialog that will submit the answer of the user
        if (view == sub) {
            GameDataBase g = new GameDataBase(this);
            if(flag.isChecked()) {
                Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
                g.setRecord(new Game(c.getWinnerString(), c.getP1(), c.getP2(), c.getStepsWinner()));
            }
            d.dismiss();
            //when game is done stop music and move to intro page
            stopService(musicIntent);
            Intent intent = new Intent(this, IntroPage.class);
            startActivity(intent);
        }
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(phoneCallRec);
        super.onDestroy();
    }

    public void createEndGameDialog() {
        //creating the dialog for when the game ends
        d = new Dialog(this);
        d.setContentView(R.layout.save_stats_dialog);
        d.setTitle("save?");
        d.setCancelable(false);
        flag = d.findViewById(R.id.flag);
        sub = d.findViewById(R.id.sub);
        sub.setOnClickListener(this);
        d.show();
    }

    public void createPhoneCallDialog() {
        //creating custom dialog for the phone call receiver
        d = new Dialog(this);
        d.setContentView(R.layout.phone_call);
        d.setCancelable(true);
        d.show();
    }

    public void createBatteryLowDialog() {
        //creating custom dialog for the battery low receiver
        d = new Dialog(this);
        d.setContentView(R.layout.battry_low);
        d.setCancelable(true);
        d.show();
    }

    public void onClickPon(View view) throws CloneNotSupportedException {
        //handling events of clicking pons by using the logic in the model
        int tag = Integer.parseInt(view.getTag().toString());
        if (firstPlace == -1) {
            //in case it is the first click
            firstView = (ImageButton)view;
            firstPlace = tag;
        }
        else {
            //in case it is the second one
            //if move was successful change the view as well
            if(c.movePlayer(firstPlace, tag)) {
                copyModelToView();
                switchTurnOf();
            }
            firstPlace = -1;
            //checks if the game is over
            if(c.gameOver())
                createEndGameDialog();
        }
    }

    private void switchTurnOf() {
        //changes the turn of the playing player to other one
        if(turnOf == findViewById(R.id.p1Layout)) {
            turnOf.findViewById(R.id.blackTurn).setVisibility(View.INVISIBLE);
            turnOf = findViewById(R.id.p2layout);
            turnOf.findViewById(R.id.whiteTurn).setVisibility(View.VISIBLE);
        }
        else{
            turnOf.findViewById(R.id.whiteTurn).setVisibility(View.INVISIBLE);
            turnOf = findViewById(R.id.p1Layout);
            turnOf.findViewById(R.id.blackTurn).setVisibility(View.VISIBLE);
        }

    }

    public void copyModelToView(){
        //copies the board from the java logic parts to the view using no logic but only technical
        Pon[][] board = c.getBoard();
        int counter = 1;

        for(int i=0; i<SIZE; i++){
            for(int k=0; k<SIZE; k++) {
                if (i % 2 != k % 2) {
                    if (board[i][k].isEmpty()) {
                        rows[i].findViewWithTag(Integer.toString(counter)).setBackground(this.getResources().getDrawable(R.drawable.dark));
                    } else {
                        if (board[i][k] instanceof Queen) {
                            if (board[i][k].getColor() == 'B')
                                rows[i].findViewWithTag(Integer.toString(counter)).setBackground(this.getResources().getDrawable(R.drawable.blackking));
                            else
                                rows[i].findViewWithTag(Integer.toString(counter)).setBackground(this.getResources().getDrawable(R.drawable.whitequeen));
                        } else {
                            if (board[i][k].getColor() == 'B')
                                rows[i].findViewWithTag(Integer.toString(counter)).setBackground(this.getResources().getDrawable(R.drawable.blackdark));
                            else
                                rows[i].findViewWithTag(Integer.toString(counter)).setBackground(this.getResources().getDrawable(R.drawable.whitedark));
                        }
                    }
                }
                counter++;
            }
        }
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