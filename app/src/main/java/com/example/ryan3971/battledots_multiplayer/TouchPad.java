package com.example.ryan3971.battledots_multiplayer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by ryan3971 on 2/5/2016.
 */
public class TouchPad {

    static double LARGE_PAD_X, LARGE_PAD_Y;
    static double SMALL_PAD_X, SMALL_PAD_Y;

    static double LARGE_PAD_RADIUS;
    static double SMALL_PAD_RADIUS;

    static double HEIGHT, WIDTH;


    GameConstants gameConstants;

    public TouchPad()   {

        gameConstants = new GameConstants();
        HEIGHT = gameConstants.getHeight();
        WIDTH = gameConstants.getWidth();

        LARGE_PAD_RADIUS = WIDTH * 0.10;
        SMALL_PAD_RADIUS = WIDTH * 0.025;

        setLargePadX(0);
        setLargePadY(0);
        setSmallPadX(0);
        setSmallPadY(0);
    }

    public void drawTouchPad(Canvas canvas)  {

        double largePadX = getLargePadX();
        double largePadY = getLargePadY();
        double smallPadX = getSmallPadX();
        double smallPadY = getSmallPadY();

        Paint largePadColor = new Paint();
        largePadColor.setColor(Color.argb(100, 0, 0, 255));
        canvas.drawCircle((float)largePadX, (float)largePadY, (float)LARGE_PAD_RADIUS, largePadColor);

        Paint smallPadColor = new Paint();
        smallPadColor.setColor(Color.argb(100, 0, 255, 0));
        canvas.drawCircle((float)smallPadX, (float)smallPadY, (float)SMALL_PAD_RADIUS, smallPadColor);
    }

    public static void setLargePadX(double newX)  {
        LARGE_PAD_X = newX;
    }
    public static void setLargePadY(double newY)  {
        LARGE_PAD_Y = newY;
    }
    public static double getLargePadX()  {
        return LARGE_PAD_X;
    }
    public static double getLargePadY()  {
        return LARGE_PAD_Y;
    }
    public static void setSmallPadX(double newX)  {
        SMALL_PAD_X = newX;
    }
    public static void setSmallPadY(double newY)  {
        SMALL_PAD_Y = newY;
    }
    public static double getSmallPadX()  {
        return SMALL_PAD_X;
    }
    public static double getSmallPadY()  {
        return SMALL_PAD_Y;
    }
}
