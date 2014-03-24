package com.lemoalsauvere.universite.s6.progevenementielle.projetandroid.l3info_catchgameactivity;

import android.R.color;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.lemoalsauvere.universite.s6.progevenementielle.projetandroid.R;
import com.lemoalsauvere.universite.s6.progevenementielle.projetandroid.l3info_catchgamedatastructure.Fruit;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/* 
 * Custom view for displaying falling fruits
 * Comments to be added
 * To be modified to implement your own version of the game
 */
public class CatchGameView extends View {

	List<Fruit> fallingDownFruitsList = new ArrayList<Fruit>();
	Bitmap applePict = BitmapFactory.decodeResource(getResources(), R.drawable.apple);
	Bitmap applePict2 = BitmapFactory.decodeResource(getResources(),R.drawable.apple);
	int fruitFallDelay = 1000;
    int yAxisFallingFactor = 10;
	Timer timerFallingFruits;
	
	public CatchGameView(Context context) {
		super(context);
		fallingDownFruitsList.clear();
	}
	
	public CatchGameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		fallingDownFruitsList.clear();
	}
	
	public CatchGameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		fallingDownFruitsList.clear();
	}
	
	public void initTimer(){
		timerFallingFruits = new Timer();
		timerFallingFruits.schedule(new TimerTask() {
			@Override
			public void run() {
				timerEventHandler();
			}

		}, 0, fruitFallDelay);

	}
	
	public void stopTimer(){
		timerFallingFruits.cancel();
	}
	
	private void timerEventHandler(){

        Log.i("CatchGameView", "Making the fruits fallen...");

        // For each apple, we change its positions
        for(Fruit fruit : this.fallingDownFruitsList) {
            Point currentFruitLocation = fruit.getLocationInScreen();
            currentFruitLocation.x += yAxisFallingFactor;
            fruit.setLocation(currentFruitLocation);
        }
        this.postInvalidate();
	}
	
	public void setFruitFallDelay(int delay){
		fruitFallDelay = delay;
	}
	
	public void setFruitList(List<Fruit> fruitList){
		this.fallingDownFruitsList = fruitList;
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(color.holo_green_dark);

		for (Fruit fruit : fallingDownFruitsList){
            Rect fruitBounds = new Rect(fruit.getLocationInScreen().x, fruit.getLocationInScreen().y, 2*(fruit.getRadius()), 2*(fruit.getRadius()));
			canvas.drawBitmap(applePict, fruitBounds.top, fruitBounds.left,null);
		}
		
	}

}
