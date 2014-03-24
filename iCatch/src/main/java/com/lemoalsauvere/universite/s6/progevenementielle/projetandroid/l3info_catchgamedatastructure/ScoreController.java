package com.lemoalsauvere.universite.s6.progevenementielle.projetandroid.l3info_catchgamedatastructure;

/**
 * Created by flemoal on 24/03/2014.
 */
public class ScoreController {
    private static ScoreController sc = new ScoreController();
    private int score = 0;
    private int life = 3;

    private ScoreController() {
    }

    public static ScoreController getInstance() {
        return sc;
    }

    public synchronized int getScore() {
        return score;
    }

    public synchronized void setScore(int score) {
        this.score = score;
    }

    public synchronized void incrementScoreByOne() {this.score++;}

    /**
     *
     * @return true if life is > 0 after loosing one
     */
    public synchronized boolean looseLife() {
        if (life > 0) {
            life--;
        }

        return life > 0;
    }

    public synchronized int getLife() {
        return life;
    }

    public synchronized void reset() {
        score = 0;
        life = 3;
    }

    public synchronized void addScore(int add) {
        score += add;
    }
}
