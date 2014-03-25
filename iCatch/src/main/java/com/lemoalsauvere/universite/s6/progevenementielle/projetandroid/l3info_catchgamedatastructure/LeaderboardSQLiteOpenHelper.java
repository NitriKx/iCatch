package com.lemoalsauvere.universite.s6.progevenementielle.projetandroid.l3info_catchgamedatastructure;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Benoît Sauvère on 24/03/2014.
 */
public class LeaderboardSQLiteOpenHelper extends SQLiteOpenHelper {


    public static final String TABLE_NAME = "leaderboard";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PLAYER_NAME = "playername";
    public static final String COLUMN_SCORE = "score";
    public static final String COLUMN_DATE = "date";

    public static final String[] COLUMN_LIST = new String[] {
            COLUMN_ID,
            COLUMN_PLAYER_NAME,
            COLUMN_SCORE,
            COLUMN_DATE
    };

    private static final String DATABASE_NAME = "leaderboard.db";
    private static final int DATABASE_VERSION = 1;

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    // Database creation sql statement
    private static final String DATABASE_CREATE = "CREATE TABLE "
            + TABLE_NAME
            + "("
                + COLUMN_ID           + " integer primary key autoincrement, "
                + COLUMN_PLAYER_NAME  + " text not null, "
                + COLUMN_SCORE        + " integer not null, "
                + COLUMN_DATE         + " text not null"
            + ");";

    public LeaderboardSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(LeaderboardSQLiteOpenHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
