package com.example.ryan3971.battledots_multiplayer;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;

/**
 * Created by ryan3971 on 2/14/2016.
 */
public class Other_Dot {

    double MIDDLE_X;
    double MIDDLE_Y;
    double DOT_RADIUS;

    GameConstants gameConstants;
    Constants CONSTANTS;

    public Other_Dot()    {

        gameConstants = new GameConstants();

        MIDDLE_Y = gameConstants.MIDDLE_Y;
        MIDDLE_X = gameConstants.MIDDLE_X;

        DOT_RADIUS = gameConstants.DOT_RADIUS;
    }

    public void drawDot(Canvas canvas, double dot_1_x, double dot_1_y) {

        Paint dot_color = new Paint();

        double dot_radius = DOT_RADIUS * GameConstants.getDot2Size();

        if (GameConstants.getDot2Invisible() == true)
            dot_color.setColor(Color.argb(0, 255, 0, 0));
        else if (GameConstants.getDot2Invisible() == false)
            dot_color.setColor(Color.rgb(255, 0, 0));

        double other_dot_x = Constants.getOtherDotX();
        double other_dot_y = Constants.getOtherDotY();

        double other_dot_on_screen_location_x = (other_dot_x - dot_1_x) + MIDDLE_X;
        double other_dot_on_screen_location_y = (other_dot_y - dot_1_y) + MIDDLE_Y;

        if (GameConstants.getDot2Shield() == true && GameConstants.getDot2Invisible() == false)   {
            Paint shield_color = new Paint();
            shield_color.setColor(Color.BLUE);

            double shield_radius = dot_radius * 1.5;
            canvas.drawCircle((float)other_dot_on_screen_location_x, (float)other_dot_on_screen_location_y, (float)shield_radius, shield_color);
        }

        canvas.drawCircle((float)other_dot_on_screen_location_x, (float)other_dot_on_screen_location_y, (float)dot_radius, dot_color);

    }
}
