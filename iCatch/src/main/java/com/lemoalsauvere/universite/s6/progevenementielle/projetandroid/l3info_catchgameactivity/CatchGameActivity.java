package com.lemoalsauvere.universite.s6.progevenementielle.projetandroid.l3info_catchgameactivity;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupMenu;
import com.lemoalsauvere.universite.s6.progevenementielle.projetandroid.R;
import com.lemoalsauvere.universite.s6.progevenementielle.projetandroid.l3info_catchgamedatastructure.Fruit;

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
    boolean launched = false;
    Timer timerFallingFruits;
    Timer timerSpawnFruits;
    int fruitFallDelay = 50;
    int fruitSpawnDelay = 600;
	List<Fruit> fruitList;
	CatchGameView fruitView;
	Button bStart;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_catch_game);
		fruitView = (CatchGameView)findViewById(R.id.l3InfoCatchGameView1);
        bStart = (Button)findViewById(R.id.buttonStart);

		bStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				buttonStartClickEventHandler();
				
			}

		});

        fruitList = new ArrayList<Fruit>();
		testInitFruitList();
		fruitView.setFruitList(fruitList);
		
	}

    private void testInitFruitList() {

		fruitList.add(new Fruit(new Point(15, 15)));
		fruitList.add(new Fruit(new Point(15, 630)));
		
	}

	private void buttonStartClickEventHandler() {
        if(launched) {
            this.stopFallTimer();
            this.stopSpawnTimer();
            bStart.setText(getResources().getString(R.string.btn_start));
            launched = false;
        } else {
            this.initFallTimer();
            this.initSpawnTimer();
            bStart.setText(getResources().getString(R.string.btn_stop));
            launched = true;
        }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.catch_game, menu);
		return true;
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
                fruitList.add(new Fruit(new Point(15, (int) (Math.random() * (fruitView.getWidth() - 50)))));
            }

        }, 0, fruitSpawnDelay);
    }



    public void stopSpawnTimer(){
        timerSpawnFruits.cancel();
    }

}
