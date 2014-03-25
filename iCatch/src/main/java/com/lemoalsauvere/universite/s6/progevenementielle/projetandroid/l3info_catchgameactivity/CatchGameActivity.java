package com.lemoalsauvere.universite.s6.progevenementielle.projetandroid.l3info_catchgameactivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.lemoalsauvere.universite.s6.progevenementielle.projetandroid.R;
import com.lemoalsauvere.universite.s6.progevenementielle.projetandroid.l3info_catchgamedatastructure.Fruit;
import com.lemoalsauvere.universite.s6.progevenementielle.projetandroid.l3info_catchgamedatastructure.ScoreController;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/* 
 * Main screen for the game
 * Comments to be added
 * To be modified to implement your own version of the game
 */
public class CatchGameActivity extends Activity {
    boolean play = false;
    boolean launched = false;
    Timer timerFallingFruits;
    Timer timerSpawnFruits;
    int fruitFallDelay = 40;
    int fruitSpawnDelay = 500;
    int fallingFactor = 5;
	List<Fruit> fruitList;
	CatchGameView fruitView;
    LifeView lifeView;
	Button bStart;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
//        ScoreController.getInstance().setLife(PreferenceManager.getDefaultSharedPreferences());

        updateLife();
        updateDifficulty();

        setContentView(R.layout.activity_catch_game);
        fruitView = (CatchGameView)findViewById(R.id.l3InfoCatchGameView1);
        lifeView = (LifeView)findViewById(R.id.l3InfoCatchLifeView1);

        bStart = (Button)findViewById(R.id.buttonStart);

        bStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				buttonStartClickEventHandler();
			}

		});

        fruitList = new ArrayList<Fruit>();
//		testInitFruitList();
		fruitView.setFruitList(fruitList);
		
	}

    private void updateDifficulty() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int difficulty = Integer.parseInt(prefs.getString("prefDifficulty",""));

        switch (difficulty) {
            case 1:
                this.fallingFactor = 3;
                this.fruitFallDelay = 50;
                this.fruitSpawnDelay = 600;
                break;
            case 2:
                this.fallingFactor = 5;
                this.fruitFallDelay = 40;
                this.fruitSpawnDelay = 450;
                break;
            case 3:
                this.fallingFactor = 8;
                this.fruitFallDelay = 40;
                this.fruitSpawnDelay = 300;
            case 4:
                this.fallingFactor = 11;
                this.fruitFallDelay = 40;
                this.fruitSpawnDelay = 150;
                break;
        }
    }

    public void resetGame() {
        // If the game is running we click the pause button
        if(launched) {
            this.stopFallTimer();
            this.stopSpawnTimer();
            bStart.setText(getResources().getString(R.string.btn_start));
        }

        play = false;
        launched = false;

        // Clear all the apples and restore the initial ones
        this.fruitList.clear();
//        testInitFruitList();

        // Reset the score and the lifes
        ScoreController.getInstance().reset();
        updateLife();
        updateDifficulty();

        fruitView.resetView();
        refreshLives();
    }

    private void updateLife() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int nbLive = Integer.parseInt(prefs.getString("prefNbLives",""));
        ScoreController.getInstance().setLife(nbLive);
    }

    private void testInitFruitList() {
		fruitList.add(new Fruit(new Point(15, 15)));
		fruitList.add(new Fruit(new Point(15, 630)));
	}

	private void buttonStartClickEventHandler() {
        startAndPauseButtonPressed();
	}

    public void startAndPauseButtonPressed() {
        if(!launched) {
            launched = true;
        }

        if(play) {
            this.stopFallTimer();
            this.stopSpawnTimer();
            bStart.setText(getResources().getString(R.string.btn_unpause));
            play = false;
        } else {
            this.initFallTimer();
            this.initSpawnTimer();
            bStart.setText(getResources().getString(R.string.btn_pause));
            play = true;
        }
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.catch_game, menu);
		return true;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                // Show the settings
                Intent i = new Intent(getApplicationContext(), UserSettingActivity.class);
                startActivityForResult(i, 1);
                return true;
            case R.id.action_leaderboard:
                // Show the leaderboard
                return true;
            case R.id.action_restart:
                this.resetGame();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void initFallTimer(){
        timerFallingFruits = new Timer();
        timerFallingFruits.schedule(new TimerTask() {
            @Override
            public void run() {
                fruitView.refreshView();
            }

        }, fruitFallDelay, fruitFallDelay);
    }

    public void stopFallTimer(){
        timerFallingFruits.cancel();
    }

    public void initSpawnTimer(){
        timerSpawnFruits = new Timer();
        timerSpawnFruits.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.i("Spawn : ", (int) (Math.random() * (fruitView.getWidth() - 50)) + "");
                Fruit fruit = new Fruit(new Point(15, (int) (Math.random() * (fruitView.getWidth() - 50))));
                fruitList.add(fruit);
            }
        }, 0, fruitSpawnDelay);
    }



    public void stopSpawnTimer(){
        timerSpawnFruits.cancel();
    }

    public boolean isGameRunning() {
        return launched && play;
    }

    public int getFallingFactor() {
        return fallingFactor;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_FIRST_USER:
                if (! launched) {
                    updateLife();
                    updateDifficulty();
                    fruitView.refreshView();
                    refreshLives();
                }

                break;
        }
    }

    public void refreshLives() {
        lifeView.refreshView();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(isGameRunning()) {
            startAndPauseButtonPressed();
        }
    }
}
