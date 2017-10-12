package com.example.ryan3971.battledots_multiplayer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ryan3971 on 2/5/2016.
 */
public class TripleBalls {

    double LEFT;
    double RIGHT;
    double TOP;
    double BOTTOM;

    double WIDTH;
    double HEIGHT;

    double DOT_RADIUS;

    double MIDDLE_X;
    double MIDDLE_Y;

    double BALL_SPEED;
    double BALL_RADIUS;

    Paint BALL_COLOR;


    Random random;

    GameConstants gameConstants;

    Canvas canvas;

    ManageObjects MANAGE_OBJECTS_OBJECT;
    OtherManageObjects OTHER_MANAGE_OBJECTS_OBJECT;

    public TripleBalls(Canvas canvasParameter, ManageObjects manageObjects, OtherManageObjects otherManageObjects)  {

        gameConstants = new GameConstants();
        HEIGHT = gameConstants.getHeight();
        WIDTH = gameConstants.getWidth();
        MIDDLE_Y = gameConstants.MIDDLE_Y;
        MIDDLE_X = gameConstants.MIDDLE_X;
        DOT_RADIUS = gameConstants.DOT_RADIUS;

        TOP = gameConstants.TOP;
        BOTTOM = gameConstants.BOTTOM;
        RIGHT = gameConstants.RIGHT;
        LEFT = gameConstants.LEFT;

        BALL_SPEED = 20;       //change latter
        BALL_RADIUS = WIDTH / 50;

        BALL_COLOR = new Paint();
        BALL_COLOR.setColor(Color.rgb(255, 0, 255));

        random = new Random();
        canvas = canvasParameter;

        MANAGE_OBJECTS_OBJECT = manageObjects;
        OTHER_MANAGE_OBJECTS_OBJECT = otherManageObjects;

        BALL_CIRCLING_LOCATION_ANGLE = new ArrayList<>();
        BALL_DISTANCE_FROM_DOT = BALL_RADIUS * 2.5;
        BALL_CIRCLING_MOVING_DISTANCE = Math.PI / 20;

    }

    ArrayList<Double> BALL_CIRCLING_LOCATION_ANGLE;
    double BALL_DISTANCE_FROM_DOT;
    double BALL_CIRCLING_MOVING_DISTANCE;

    public void createCirclingBalls()   {

        double angle = 0;

        angle = 0;
        BALL_CIRCLING_LOCATION_ANGLE.add(angle);
/*
        angle = (2 * Math.PI) / 3;
        BALL_CIRCLING_LOCATION_ANGLE.add(angle);

        angle = (4 * Math.PI) / 3;
        BALL_CIRCLING_LOCATION_ANGLE.add(angle);
*/
    }

    public void circleTheBalls(double dot_location_x, double dot_location_y)    {

        double ball_x = 0;
        double ball_y = 0;
        double angle = 0;

        double list_size = BALL_CIRCLING_LOCATION_ANGLE.size();

        for (int num = 0; num < list_size; num++) {
            angle = BALL_CIRCLING_LOCATION_ANGLE.get(num);

            angle += BALL_CIRCLING_MOVING_DISTANCE;
            BALL_CIRCLING_LOCATION_ANGLE.set(num, angle);
            ball_x = Math.cos(angle) * BALL_DISTANCE_FROM_DOT;
            ball_y = Math.sin(angle) * BALL_DISTANCE_FROM_DOT;

            double draw_ball_x = ball_x + MIDDLE_X;
            double draw_ball_y = ball_y + MIDDLE_Y;

            canvas.drawCircle((float)draw_ball_x, (float)draw_ball_y, (float)BALL_RADIUS, BALL_COLOR);

            if (GameConstants.DO_FIRE_TRIPLE_BALLS)     {
                angle = angle % (2 * Math.PI);

                Log.w("TB", "angle:   " + angle);
                Log.w("TB", "dot_angle:   " + GameConstants.TRIPLE_BALLS_FIRE_ANGLE);
                Log.w("TB", "BALL_CIRCLING_MOVING_DISTANCE:   " + BALL_CIRCLING_MOVING_DISTANCE);
                Log.w("TB", "dot_angle - angle:   " + (GameConstants.TRIPLE_BALLS_FIRE_ANGLE - angle));
                Log.w("TB", "angle - dot_angle:   " + (angle - GameConstants.TRIPLE_BALLS_FIRE_ANGLE));



                if ((GameConstants.TRIPLE_BALLS_FIRE_ANGLE - angle) < BALL_CIRCLING_MOVING_DISTANCE && (GameConstants.TRIPLE_BALLS_FIRE_ANGLE - angle) > -BALL_CIRCLING_MOVING_DISTANCE)   {       //this seems to work fine...
                    Log.w("TB", "FIRED");

                    GameConstants.DO_FIRE_TRIPLE_BALLS = false;
                    BALL_CIRCLING_LOCATION_ANGLE.remove(num);

                    list_size = BALL_CIRCLING_LOCATION_ANGLE.size();

                    ball_x = dot_location_x + ball_x;
                    ball_y = dot_location_y + ball_y;

                    MANAGE_OBJECTS_OBJECT.tripleBallToBouncingBall(ball_x, ball_y, dot_location_x, dot_location_y, angle, canvas);
                }
            }

            if (BALL_CIRCLING_LOCATION_ANGLE.size() == 0)   {
                MANAGE_OBJECTS_OBJECT.destroyTripleBall();
            }
        }
    }
}
