package com.example.ryan3971.battledots_multiplayer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by ryan3971 on 2/6/2016.
 */
public class speedBoost {

    double WIDTH;
    double HEIGHT;

    double BUTTON_RADIUS;
    double BUTTON_X, BUTTON_Y;

    GameConstants gameConstants;

    public speedBoost()     {

        gameConstants = new GameConstants();
        HEIGHT = gameConstants.getHeight();
        WIDTH = gameConstants.getWidth();

        BUTTON_X = WIDTH * 0.85;
        BUTTON_Y = HEIGHT * 0.75;
        BUTTON_RADIUS = WIDTH * 0.0625;

    }

    public void drawSpeedBoostButton(Canvas canvas)  {

        Paint button_color = new Paint();

        if (PowerUpVariables.IsSpeedBoost() == false)    {
            button_color.setColor(Color.argb(100, 0, 0, 150));
            canvas.drawCircle((float) BUTTON_X, (float) BUTTON_Y, (float) BUTTON_RADIUS, button_color);
        }else {
            button_color.setColor(Color.argb(50, 0, 0, 50));
            canvas.drawCircle((float) BUTTON_X, (float) BUTTON_Y, (float) BUTTON_RADIUS, button_color);
        }
    }
}
