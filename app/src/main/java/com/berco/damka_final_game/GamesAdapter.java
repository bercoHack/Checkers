package com.berco.damka_final_game;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class GamesAdapter extends ArrayAdapter<Game> {
    private Context context;
    private List<Game> games;

    public GamesAdapter(Context context, int resource, List<Game> games) {
        super(context,resource, games);
        this.context = context;
        this.games = games;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //creating an adapter from list  of games to a list of 'game rows' that can be showed to the view
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.game_row,parent,false);

        TextView player1 =view.findViewById(R.id.player1name);
        TextView player2 =view.findViewById(R.id.player2name);
        TextView winner =view.findViewById(R.id.winnerPlayer);
        TextView steps =view.findViewById(R.id.steps);

        Game temp = (Game)games.get(position);
        player1.setText(String.valueOf(temp.getP1()));
        player2.setText(String.valueOf(temp.getP2()));
        winner.setText("winner " + String.valueOf(temp.getWinner()));
        steps.setText(String.valueOf(temp.getSteps()));

        return view;
    }

}
