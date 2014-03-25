package com.lemoalsauvere.universite.s6.progevenementielle.projetandroid.l3info_catchgameactivity;

import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import java.util.concurrent.CopyOnWriteArrayList;

/* 
 * Custom view for displaying falling fruits
 * Comments to be added
 * To be modified to implement your own version of the game
 */
public class CatchGameView extends View {

	List<Fruit> fallingDownFruitsList = new ArrayList<Fruit>();
    Map<Fruit, Rect> appleHitboxes = Collections.synchronizedMap(new HashMap<Fruit, Rect>());
	Bitmap applePict = BitmapFactory.decodeResource(getResources(), R.drawable.apple);

    final CatchGameActivity catchGameActivity = (CatchGameActivity) this.getContext();

	
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

        // Let the ScaleGestureDetector inspect all events.
        super.onTouchEvent(event);

        // We only kill an apple if the game is running
        if(catchGameActivity.isGameRunning()) {

            // If the user press the screen
            int action = event.getActionMasked();
            switch (action) {
                case MotionEvent.ACTION_DOWN: {

                    Log.i(this.getClass().getSimpleName(), "View touched");

                    // This logic is synchronized in order to avoid the "multiple event for on press" problem
                    synchronized (CatchGameView.class) {

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
                                    return true;
                                }
                            }
                        }
                    }
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
            currentFruitLocation.x += catchGameActivity.getFallingFactor();

            if(currentFruitLocation.x >= this.getBottom() - 200) {
                this.appleHitboxes.remove(fruit);
                iterator.remove();
                ScoreController.getInstance().looseLife();
                Log.i(this.getClass().getSimpleName(), "Fruit removed because it reaches the bottom of the screen.");

                catchGameActivity.refreshLives();

                // If the player is dead we show the "GameOver" popver an then we reset the game
                if(ScoreController.getInstance().getLife() == 0) {
                    catchGameActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Pause the game
                            catchGameActivity.startAndPauseButtonPressed();

                            // Create and display the popup
                            AlertDialog gameOverPopup = new AlertDialog.Builder(catchGameActivity)
                                    .setTitle(R.string.alertdialog_gameover_title)
                                    .setMessage(getResources().getString(R.string.alertdialog_gameover_text, ScoreController.getInstance().getScore()))
                                    .setCancelable(true)
                                    .setNeutralButton(R.string.alertdialog_gameover_buttontext, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    })
                                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                        @Override
                                        public void onDismiss(DialogInterface dialog) {
                                            catchGameActivity.resetGame();
                                        }
                                    })
                                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                        @Override
                                        public void onCancel(DialogInterface dialog) {
                                            catchGameActivity.resetGame();
                                        }
                                    })
                                    .create();
                            gameOverPopup.show();
                        }
                    });
                }
            } else {
                fruit.setLocation(currentFruitLocation);
            }
        }

        this.postInvalidate();
	}
	
	public void setFruitList(List<Fruit> fruitList){
		this.fallingDownFruitsList = fruitList;
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

        canvas.drawColor(color.holo_green_dark);

        catchGameActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Refresh score
                ((TextView) catchGameActivity.findViewById(R.id.textScore))
                        .setText(getResources().getString(R.string.text_score,ScoreController.getInstance().getScore()));
            }
        });

        // This array is a special ArrayList which guarantee that the iterator will never throw a ConcurrentModificationException
        CopyOnWriteArrayList<Fruit> copyOnWriteArrayList = new CopyOnWriteArrayList<Fruit>(fallingDownFruitsList);
        for (Fruit fruit : copyOnWriteArrayList){
            Rect fruitBounds = new Rect(fruit.getLocationInScreen().x, fruit.getLocationInScreen().y,
                    fruit.getLocationInScreen().x + applePict.getWidth(), fruit.getLocationInScreen().y + applePict.getHeight());
            canvas.drawBitmap(applePict, fruitBounds.top, fruitBounds.left, null);
            this.appleHitboxes.put(fruit, fruitBounds);
        }
	}

    public void resetView() {
        this.appleHitboxes.clear();
        //Redraw
        this.postInvalidate();
    }
}
