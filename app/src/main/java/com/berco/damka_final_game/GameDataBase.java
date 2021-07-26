package com.berco.damka_final_game;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;

public class GameDataBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "games.db";
    private static final String TABLE_RECORD = "tblgame";
    private static final int DATABASE_VERSION = 1;

    private static final String COLUMN_ID = "Id";
    private static final String COLUMN_P1 = "Player1";
    private static final String COLUMN_P2 = "Player2";
    private static final String COLUMN_STEPS = "Steps";
    private static final String COLUMN_WINNER = "Winner";

    private static final String[] allColumns = {COLUMN_ID, COLUMN_P1, COLUMN_P2, COLUMN_STEPS, COLUMN_WINNER};


    private static final String CREATE_TABLE_GAME = "CREATE TABLE IF NOT EXISTS " +
            TABLE_RECORD + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY," +
            COLUMN_P1 + " TEXT," +
            COLUMN_P2 + " TEXT," +
            COLUMN_STEPS + " INTEGER," +
            COLUMN_WINNER + " TEXT );";

    private SQLiteDatabase database; // access to table
    private static ArrayList<Game> games;


    public GameDataBase(Context context) {
        //in the create of the game database it will create a new base if there isn't one and will get all the
        // records from the existing one
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        getAllRecords();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //runs a sql statement that creates a new database
        sqLiteDatabase.execSQL(CREATE_TABLE_GAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //if version has changed drop the previous table and create a new one
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORD);
        onCreate(sqLiteDatabase);
    }

    public Game createRecord(Game record) {
        database = getWritableDatabase(); // get access to write the database
        ContentValues values = new ContentValues();
        values.put(COLUMN_P1, record.getP1());
        values.put(COLUMN_P2, record.getP2());
        values.put(COLUMN_WINNER, record.getWinner());
        values.put(COLUMN_STEPS, record.getSteps());
        //inserts a new record with the values above to the database
        long id = database.insert(TABLE_RECORD, null, values);
        record.setId(id);
        database.close();
        return record;
    }

    public void deleteGameByRow(long id) {
        //deletes the row with the specific id
        database = getWritableDatabase();
        database.delete(TABLE_RECORD, COLUMN_ID + " = " + id, null);
        database.close(); // close the database
    }

    public void updateRecordByRow(Game game){
        //update the row with the same id to new values
        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, game.getId());
        values.put(COLUMN_P1, game.getP1());
        values.put(COLUMN_P2, game.getP2());
        values.put(COLUMN_STEPS, game.getSteps());
        values.put(COLUMN_WINNER, game.getWinner());

        database.update(TABLE_RECORD, values, COLUMN_ID + "=" + game.getId(), null);
        database.close();

    }

    public void setRecord(Game game){
        //if a record exists it will check if the new steps are better that in the previous one and will change them if so
        Game current = null;
        for(Game g: games){
            if(g.getP1().equals(game.getP1()) && g.getP2().equals(game.getP2()) && game.getWinner().equals(g.getWinner())){
                current = g;
                break;
            }
        }
        if(current == null)
            createRecord(game);
        else{
            if(game.getSteps() < current.getSteps())
                current.setSteps(game.getSteps());
            updateRecordByRow(current);
        }

    }

    public void getAllRecords() {
        //getting all games from database and putting them in games arrayList sorted
        database = getReadableDatabase();
        games = new ArrayList<>();
        //the way of the sorting
        String sortOrder = COLUMN_STEPS + " DESC"; // sorting by steps
        Cursor cursor = database.query(TABLE_RECORD, allColumns,
                null, null, null, null, sortOrder);
        if(cursor.getCount() > 0)
        {
            while(cursor.moveToNext())
            {
                String p1 = cursor.getString(cursor.getColumnIndex(COLUMN_P1));
                String p2 = cursor.getString(cursor.getColumnIndex(COLUMN_P2));
                String winner = cursor.getString(cursor.getColumnIndex(COLUMN_WINNER));
                int steps = cursor.getInt(cursor.getColumnIndex(COLUMN_STEPS));
                long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
                Game record = new Game(winner, p1, p2, steps, id);
                games.add(record);
            }
        }
        database.close();
    }

    public static ArrayList<Game> getGames() {
        return games;
    }
}
