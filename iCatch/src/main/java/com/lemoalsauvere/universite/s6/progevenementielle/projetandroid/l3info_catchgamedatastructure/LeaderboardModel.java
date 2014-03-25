package com.lemoalsauvere.universite.s6.progevenementielle.projetandroid.l3info_catchgamedatastructure;

import java.util.Date;

/**
 * Created by Benoît Sauvère on 24/03/2014.
 */
public class LeaderboardModel {

    private long id;
    private String playerName;
    private long score;
    private Date date;

    //
    // CONSTRUCTORS
    //

    public LeaderboardModel() {

    }

    public LeaderboardModel(long id, String playerName, long score, Date date) {
        this.id = id;
        this.playerName = playerName;
        this.score = score;
        this.date = date;
    }

    //
    // GETTERS AND SETTERS
    //

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("%d", this.score);
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
