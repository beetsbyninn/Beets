package com.github.beetsbyninn.beets;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Jonte on 13-Mar-17.
 */

public class StatsDB extends SQLiteOpenHelper {
    private static final int DATABSE_VERSION =1;

    private static final String DATABASE_NAME = "statsDb";
    private static final String TABLE_STATS = "stats";
    private static final String KEY_ID = "id";
    private static  final String KEY_STATS = "stats";
    private static  final String KEY_SONG = "song";


    public StatsDB(Context context){
        super(context, DATABASE_NAME, null, DATABSE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_STATS_TABLE = "CREATE TABLE " + TABLE_STATS + "(" + KEY_ID +
                " INTEGER PRIMARY KEY," + KEY_STATS + " INTEGER," + KEY_SONG + " INTEGER" + ")";
        sqLiteDatabase.execSQL(CREATE_STATS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_STATS);
        onCreate(sqLiteDatabase);
    }

    public void addScore(Score score){
        ContentValues values = new ContentValues();
        values.put(KEY_STATS, score.getStat());
        values.put(KEY_SONG, score.getSongId());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_STATS, null, values);
        db.close();
    }

    /**
     * Retrieves all scores from the database.
     * @return
     */
    public Score[] getAllScores(){

        int statsIndex,songIndex;
        String query = "SELECT * FROM " + TABLE_STATS; //+ " WHERE " + KEY_SONG + "=" + songName;
         SQLiteDatabase db = this.getWritableDatabase();
        Log.d("db","innehåll: " + query);
        Cursor cursor = db.rawQuery(query,null);
        statsIndex = cursor.getColumnIndex(KEY_STATS);
        songIndex = cursor.getColumnIndex(KEY_SONG);

        Score[] allScores = new Score[cursor.getCount()];
        if(cursor.moveToFirst()){
         for (int i = 0;i<allScores.length;i++){
            allScores[i] = new Score(cursor.getInt(statsIndex),cursor.getInt(songIndex));
         }
        }else{
            allScores = null;
        }
        db.close();
        return allScores;
    }
}
