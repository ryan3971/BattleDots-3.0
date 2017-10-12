package com.example.ryan3971.battledots_multiplayer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Ryan Tyrrell on 2016-08-21.
 */
public class FireButton {
    double WIDTH;
    double HEIGHT;

    double BUTTON_RADIUS;
    double BUTTON_X, BUTTON_Y;

    GameConstants gameConstants;

    public FireButton()     {

        gameConstants = new GameConstants();
        HEIGHT = gameConstants.getHeight();
        WIDTH = gameConstants.getWidth();

        BUTTON_X = WIDTH * 0.75;
        BUTTON_Y = HEIGHT * 0.65;
        BUTTON_RADIUS = WIDTH * 0.0300;

    }

    public void drawFireButton(Canvas canvas)  {

        Paint button_color = new Paint();

        if (PowerUpVariables.IsSpeedBoost() == false)    {
            button_color.setColor(Color.argb(100, 0, 150, 0));
            canvas.drawCircle((float) BUTTON_X, (float) BUTTON_Y, (float) BUTTON_RADIUS, button_color);
        }else {
            button_color.setColor(Color.argb(50, 0, 50, 0));
            canvas.drawCircle((float) BUTTON_X, (float) BUTTON_Y, (float) BUTTON_RADIUS, button_color);
        }
    }
}
