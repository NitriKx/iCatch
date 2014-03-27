package com.lemoalsauvere.universite.s6.progevenementielle.projetandroid.l3info_catchgameactivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.lemoalsauvere.universite.s6.progevenementielle.projetandroid.R;
import com.lemoalsauvere.universite.s6.progevenementielle.projetandroid.l3info_catchgamedatastructure.ScoreController;

/**
 * Created by flemoal on 25/03/2014.
 */
public class LifeView extends View {
    Bitmap lifePic = BitmapFactory.decodeResource(getResources(), R.drawable.life);

    public LifeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void refreshView() {
        this.postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.i(this.getClass().getSimpleName(), "Refreshing lives' counter");

        int width = (int) (BitmapFactory.decodeResource(getResources(), R.drawable.life).getWidth() * 1.2);

        for(int i = 1; i <= ScoreController.getInstance().getLife(); i++) {
            canvas.drawBitmap(lifePic, this.getWidth() - i * width,  7, null);
        }
    }
}
