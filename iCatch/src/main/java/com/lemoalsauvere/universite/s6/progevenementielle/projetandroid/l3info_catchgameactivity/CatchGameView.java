package com.lemoalsauvere.universite.s6.progevenementielle.projetandroid.l3info_catchgameactivity;

import android.R.color;
import android.app.Activity;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
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
    int yAxisFallingFactor = 5;

	
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.i(this.getClass().getSimpleName(), "View touched");

        Iterator<Map.Entry<Fruit,Rect>> i = this.appleHitboxes.entrySet().iterator();
        while(i.hasNext()) {
            Map.Entry<Fruit, Rect> entry = i.next();
            if(this.appleHitboxes.containsKey(entry.getKey())) {
                Rect fruitHitbox = entry.getValue();
                if(fruitHitbox.contains((int) event.getY(), (int) event.getX())) {
                    this.fallingDownFruitsList.remove(entry.getKey());
                    i.remove();
                    ScoreController.getInstance().incrementScoreByOne();
                    Log.i(this.getClass().getSimpleName(), "Fruit removed because it was clicked.");
                }
            }
        }

        return true;
    }

	public void refreshView(){

        // Log.i(this.getClass().getSimpleName(), "Making the fruits fallen...");

        // For each apple, we change its positions
        Iterator<Fruit> iterator = this.fallingDownFruitsList.iterator();
        while(iterator.hasNext()) {
            Fruit fruit = iterator.next();
            Point currentFruitLocation = fruit.getLocationInScreen();
            currentFruitLocation.x += yAxisFallingFactor;

            if(currentFruitLocation.x >= this.getBottom() - 200) {
                this.appleHitboxes.remove(fruit);
                iterator.remove();
                ScoreController.getInstance().looseLife();
                Log.i(this.getClass().getSimpleName(), "Fruit removed because it reaches the bottom of the screen.");
            } else {
                fruit.setLocation(currentFruitLocation);
            }
        }

        final Activity parent = (Activity) this.getContext();
        parent.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                // Refresh score
                ((TextView) parent.findViewById(R.id.textScore)).setText(ScoreController.getInstance().getScore() + "");
                ((TextView) parent.findViewById(R.id.textVie)).setText(ScoreController.getInstance().getLife() + "");

            }
        });


        this.postInvalidate();
	}

    private void deleteFruit(Fruit f) {
        this.appleHitboxes.remove(f);
        this.fallingDownFruitsList.remove(f);
    }
	
	public void setFruitList(List<Fruit> fruitList){
		this.fallingDownFruitsList = fruitList;
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.drawColor(color.holo_green_dark);

		for (Fruit fruit : fallingDownFruitsList){
            Rect fruitBounds = new Rect(fruit.getLocationInScreen().x, fruit.getLocationInScreen().y,
                    fruit.getLocationInScreen().x + applePict.getWidth(), fruit.getLocationInScreen().y + applePict.getHeight());
			canvas.drawBitmap(applePict, fruitBounds.top, fruitBounds.left, null);
            this.appleHitboxes.put(fruit, fruitBounds);
		}
		
	}

}
