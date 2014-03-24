package com.lemoalsauvere.universite.s6.progevenementielle.projetandroid.l3info_catchgameactivity;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
    Timer timerFallingFruits;
    int fruitFallDelay = 1000;
	List<Fruit> fruitList;
	CatchGameView fruitView;
	Button bStart;
	Button bStop;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_catch_game);
		fruitView = (CatchGameView)findViewById(R.id.l3InfoCatchGameView1);
		bStart = (Button)findViewById(R.id.buttonStart);
		bStop = (Button)findViewById(R.id.buttonStop);
		bStop.setEnabled(false);
		
		bStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				buttonStartClickEventHandler();
				
			}

		});

        bStop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonStopClickEventHandler();
            }
        });

		testInitFruitList();
		fruitView.setFruitList(fruitList);
		
	}

    private void buttonStopClickEventHandler() {
        this.stopTimer();
        bStop.setEnabled(false);
        bStart.setEnabled(true);
    }

    private void testInitFruitList() {
		fruitList = new ArrayList<Fruit>();
		fruitList.add(new Fruit(new Point(15, 15), 25));
		fruitList.add(new Fruit(new Point(15, 630), 25));
		
	}

	private void buttonStartClickEventHandler() {
		this.initTimer();
		bStop.setEnabled(true);
		bStart.setEnabled(false);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.catch_game, menu);
		return true;
	}

    public void initTimer(){
        timerFallingFruits = new Timer();
        timerFallingFruits.schedule(new TimerTask() {
            @Override
            public void run() {
                fruitView.refreshView();
            }

        }, 0, fruitFallDelay);

    }

    public void stopTimer(){
        timerFallingFruits.cancel();
    }



}
