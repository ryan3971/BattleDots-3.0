package com.example.ryan3971.battledots_multiplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.ImageView;

/**
 * Created by ryan3971 on 2/5/2016.
 */
public class Lives {

    static int NUMBER_OF_LIVES;

    Drawable HEART_IMAGE;

    double HEART_IMAGE_WIDTH, HEART_IMAGE_HEIGHT;

    double HEIGHT, WIDTH;

    GameConstants gameConstants;

    public Lives(Context context)  {

        gameConstants = new GameConstants();
        HEIGHT = gameConstants.getHeight();
        WIDTH = gameConstants.getWidth();

        NUMBER_OF_LIVES = 15;

        HEART_IMAGE_WIDTH = WIDTH * 0.05;
        HEART_IMAGE_HEIGHT = HEIGHT * 0.1;

        HEART_IMAGE = ResourcesCompat.getDrawable(context.getResources(), R.drawable.heart_scaled, null);
    }


    public void drawLives(Canvas canvas)    {

        for (int lives = 0; lives < NUMBER_OF_LIVES; lives++)   {
            int left = (int)(lives * HEART_IMAGE_WIDTH);
            int top = 0;
            int right = (int)(left + HEART_IMAGE_WIDTH);
            int bottom = (int)HEART_IMAGE_HEIGHT;

            HEART_IMAGE.setBounds(left, top, right, bottom);
            HEART_IMAGE.draw(canvas);

        }
    }

    public static void setLives(int lives)  {
        NUMBER_OF_LIVES = lives;
    }

    public static int getLives()  {
        return NUMBER_OF_LIVES;
    }
}
