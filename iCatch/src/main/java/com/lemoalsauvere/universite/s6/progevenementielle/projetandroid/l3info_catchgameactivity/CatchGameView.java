package com.lemoalsauvere.universite.s6.progevenementielle.projetandroid.l3info_catchgameactivity;

import android.R.color;
import android.app.Activity;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.TextView;
import com.lemoalsauvere.universite.s6.progevenementielle.projetandroid.R;
import com.lemoalsauvere.universite.s6.progevenementielle.projetandroid.l3info_catchgamedatastructure.Fruit;
import com.lemoalsauvere.universite.s6.progevenementielle.projetandroid.l3info_catchgamedatastructure.ScoreController;

import java.util.*;

/* 
 * Custom view for displaying falling fruits
 * Comments to be added
 * To be modified to implement your own version of the game
 */
public class CatchGameView extends View {

	List<Fruit> fallingDownFruitsList = new ArrayList<Fruit>();
    Map<Fruit, Rect> appleHitboxes = new HashMap<Fruit, Rect>();
	Bitmap applePict = BitmapFactory.decodeResource(getResources(), R.drawable.apple);
	Bitmap applePict2 = BitmapFactory.decodeResource(getResources(),R.drawable.apple);
	int fruitFallDelay = 1000;
    int yAxisFallingFactor = 50;
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

        Log.i(this.getClass().getSimpleName(), "Making the fruits fallen...");

        // For each apple, we change its positions
        Point viewSize = new Point();
        this.getDisplay().getSize(viewSize);

        for(Fruit fruit : this.fallingDownFruitsList) {
            Point currentFruitLocation = fruit.getLocationInScreen();
            currentFruitLocation.x += yAxisFallingFactor;

            if(currentFruitLocation.x >= viewSize.x) {
                deleteFruit(fruit);
                ScoreController.getInstance().looseLife(); // TODO Récupérer le booléen pour le GAME-OVER
                Log.i(this.getClass().getSimpleName(), "Fruit removed because it reaches the bottom of the screen.");
            } else {
                fruit.setLocation(currentFruitLocation);
            }
        }

        // Refresh score
        ((TextView) ((Activity) this.getContext()).findViewById(R.id.textScore)).setText(ScoreController.getInstance().getScore() + "");


        this.postInvalidate();
	}

    private void deleteFruit(Fruit f) {
        this.appleHitboxes.remove(f);
        this.fallingDownFruitsList.remove(f);
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
            this.appleHitboxes.put(fruit, fruitBounds);
		}
		
	}

}
