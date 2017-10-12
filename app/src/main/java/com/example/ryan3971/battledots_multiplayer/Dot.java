package com.example.ryan3971.battledots_multiplayer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;

/**
 * Created by ryan3971 on 2/4/2016.
 */
public class Dot {

    double WIDTH;
    double HEIGHT;
    double MIDDLE_X;
    double MIDDLE_Y;
    double DOT_RADIUS;

    GameConstants gameConstants;

    public Dot()    {

        gameConstants = new GameConstants();
        HEIGHT = gameConstants.getHeight();
        WIDTH = gameConstants.getWidth();
        DOT_RADIUS = gameConstants.DOT_RADIUS;

        MIDDLE_Y = gameConstants.MIDDLE_Y;
        MIDDLE_X = gameConstants.MIDDLE_X;

    }

    public void drawDot(Canvas canvas) {


        float dot_radius = (float)(DOT_RADIUS * PowerUpVariables.getDotSize());

        Paint dot_color = new Paint();

        if (PowerUpVariables.getDotInvisible() == true) {
            dot_color.setColor(Color.rgb(255, 125, 125));
        }else if (PowerUpVariables.getDotInvisible() == false) {
            dot_color.setColor(Color.rgb(255, 0, 0));
        }

        if (PowerUpVariables.getDotShield() == true) {
            Paint shield_color = new Paint();
            shield_color.setColor(Color.BLUE);

            float shield_radius = (float)(dot_radius * 1.5);
            canvas.drawCircle((float)MIDDLE_X, (float)MIDDLE_Y, shield_radius, shield_color);
        }

        canvas.drawCircle((float)MIDDLE_X, (float)MIDDLE_Y, dot_radius, dot_color);

    }
}
