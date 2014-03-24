package com.lemoalsauvere.universite.s6.progevenementielle.projetandroid.l3info_catchgameactivity;

import android.R.color;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
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

	List<Rect> fallingDownFruitsList = new ArrayList<Rect>();
	Bitmap applePict = BitmapFactory.decodeResource(getResources(), R.drawable.apple);
	Bitmap applePict2 = BitmapFactory.decodeResource(getResources(),R.drawable.apple);
	int fruitFallDelay = 1000;
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
		Log.i("CatchGameView", "timer event handler");
        this.postInvalidate();
	}
	
	public void setFruitFallDelay(int delay){
		fruitFallDelay = delay;
	}
	
	public void setFruitList(List<Fruit> fruitList){
		Rect fruitBounds;
		fallingDownFruitsList.clear();
		for (Fruit fruit:fruitList){
			fruitBounds = new Rect(fruit.getLocationInScreen().x, fruit.getLocationInScreen().y, 2*(fruit.getRadius()), 2*(fruit.getRadius()));
			fallingDownFruitsList.add(fruitBounds);
		}
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(color.holo_green_dark);
		
		for (Rect fruitBounds:fallingDownFruitsList){
			canvas.drawBitmap(applePict, fruitBounds.top, fruitBounds.left,null);
		}
		
	}

}
