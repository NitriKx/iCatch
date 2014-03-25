package com.lemoalsauvere.universite.s6.progevenementielle.projetandroid.l3info_catchgamedatastructure;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import org.w3c.dom.Comment;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Benoît Sauvère on 24/03/2014.
 */
public class LeaderboardController {

    private static LeaderboardController ourInstance = new LeaderboardController();

    public static LeaderboardController getInstance() {
        return ourInstance;
    }

    private LeaderboardController() {
    }

    private Context context;
    private LeaderboardSQLiteOpenHelper sqlLiteOpenHelper;
    private SQLiteDatabase database;
    private boolean isInitialized = false;

    public void init(Context context) {
        try {
            this.sqlLiteOpenHelper = new LeaderboardSQLiteOpenHelper(context);
            this.context = context;
            isInitialized = true;
            // resetLeaderboard();
        } catch (Exception e) {
            throw new RuntimeException(String.format("Can not initialize LeaderboardSQLiteOpenHelper."), e);
        }

    }


    public void open() throws SQLException {
        if(!isInitialized) throw new RuntimeException(String.format("LeaderboardController is not initilized."));
        database = sqlLiteOpenHelper.getWritableDatabase();
    }

    public void close() {
        if(!isInitialized) throw new RuntimeException(String.format("LeaderboardController is not initialized."));
        database.close();
    }

    public synchronized void resetLeaderboard() {
        if(!isInitialized) throw new RuntimeException(String.format("LeaderboardController is not initialized."));

        Log.i(this.getClass().getSimpleName(), "Resetting leaderboard table...");
        try {
            open();
            database.execSQL("DELETE FROM " + LeaderboardSQLiteOpenHelper.TABLE_NAME);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Can not dorp the leaderboard table."), e);
        } finally {
            close();
        }


    }

    public synchronized long addNewScore(String playerName, long score) {
        if(!isInitialized) throw new RuntimeException(String.format("LeaderboardController is not initialized."));

        Log.i(this.getClass().getName(), String.format("Adding new score in the database, score=[%d]", score));

        try {
            open();

            Date currentDate = new Date();
            ContentValues values = new ContentValues();
            values.put(LeaderboardSQLiteOpenHelper.COLUMN_PLAYER_NAME, playerName);
            values.put(LeaderboardSQLiteOpenHelper.COLUMN_SCORE, score);
            values.put(LeaderboardSQLiteOpenHelper.COLUMN_DATE, LeaderboardSQLiteOpenHelper.dateFormat.format(currentDate));

            long insertId = database.insert(LeaderboardSQLiteOpenHelper.TABLE_NAME, null, values);
            return insertId;
        } catch (Exception e) {
            throw new RuntimeException(String.format("Can not add the score=[%d] for playerName=[%s] to the database.", score, playerName), e);
        } finally {
            close();
        }
    }

    public synchronized List<LeaderboardModel> getLeaderboardScores() {
        if(!isInitialized) throw new RuntimeException(String.format("LeaderboardController is not initialized."));
        List<LeaderboardModel> comments = new ArrayList<LeaderboardModel>();

        try {
            open();

            Cursor cursor = database.query(LeaderboardSQLiteOpenHelper.TABLE_NAME,
                    LeaderboardSQLiteOpenHelper.COLUMN_LIST, null, null, null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                LeaderboardModel model = cursorToModel(cursor);
                comments.add(model);
                cursor.moveToNext();
            }
            // make sure to close the cursor
            cursor.close();
            return comments;

        } catch (Exception e) {
            throw new RuntimeException(String.format("Can not get the leaderboard scores from the database."), e);
        } finally {
            close();
        }
    }

    private LeaderboardModel cursorToModel(Cursor cursor) {
        LeaderboardModel model = new LeaderboardModel();
        model.setId(cursor.getLong(0));
        model.setPlayerName(cursor.getString(1));
        model.setScore(cursor.getLong(2));
        try {
            model.setDate(LeaderboardSQLiteOpenHelper.dateFormat.parse(cursor.getString(3)));
        } catch (ParseException e) {
            throw new RuntimeException(String.format("The row with id=[%d] have a malformed date.", model.getId()));
        }
        return model;
    }

}



