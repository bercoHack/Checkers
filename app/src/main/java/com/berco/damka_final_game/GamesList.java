package com.berco.damka_final_game;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class GamesList extends AppCompatActivity implements AdapterView.OnItemClickListener, BackAble {
    Button btnBack;
    ListView lv;
    GameDataBase g;
    GamesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //creates new object of the data base and gets all records from base in case new ones were added
        g = new GameDataBase(this);
        g.getAllRecords();
        //creats an adapter for the games
        adapter = new GamesAdapter(this, R.layout.game_row, g.getGames());

        //connect the adapter to the view
        lv = findViewById(R.id.lvPlayers);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
        //when item is clicked a dialog will pop and ask the user if he would like to delete this game record
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert");
        builder.setMessage("delete?");
        builder.setCancelable(false);
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i1) {
                g.getAllRecords();
                long id = g.getGames().get(i).getId();
                g.deleteGameByRow(id);
                g.getAllRecords();
                adapter = new GamesAdapter(GamesList.this, R.layout.game_row, g.getGames());
                lv.setAdapter(adapter);
                dialogInterface.dismiss();

            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i1) {
                dialogInterface.dismiss();

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onClickBack(View view) {
        finish();
    }
}