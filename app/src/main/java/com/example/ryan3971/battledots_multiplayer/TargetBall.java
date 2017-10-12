package com.example.ryan3971.battledots_multiplayer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by ryan3971 on 2/5/2016.
 */
public class TargetBall {

    double LEFT;
    double RIGHT;
    double TOP;
    double BOTTOM;

    double WIDTH;
    double HEIGHT;

    double DOT_RADIUS;

    double MIDDLE_X;
    double MIDDLE_Y;

    double TARGET_BALL_X_MOVE;
    double TARGET_BALL_Y_MOVE;

    double TARGET_BALL_X_COORDINATES;
    double TARGET_BALL_Y_COORDINATES;

    double TARGET_BALL_SPEED;
    double TARGET_BALL_RADIUS;

    double TARGET_BALL_X_ON_SCREEN_LOCATION, TARGET_BALL_Y_ON_SCREEN_LOCATION;
    double START_X, START_Y;

    double ADJUSTED_X, ADJUSTED_Y;

    Paint TARGET_BALL_COLOR;

    GameConstants gameConstants;
    Canvas canvas;

    ManageObjects MANAGE_OBJECTS_OBJECT;
    OtherManageObjects OTHER_MANAGE_OBJECTS_OBJECT;

    public TargetBall(Canvas canvasParameter, ManageObjects manageObjects, OtherManageObjects otherManageObjects)     {

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

        TARGET_BALL_SPEED = 5;       //change latter
        TARGET_BALL_RADIUS = WIDTH * 0.025;

        TARGET_BALL_COLOR = new Paint();
        TARGET_BALL_COLOR.setColor(Color.rgb(100,0,100));

        canvas = canvasParameter;

        MANAGE_OBJECTS_OBJECT = manageObjects;
        OTHER_MANAGE_OBJECTS_OBJECT = otherManageObjects;
    }

    public void createTargetBall(double dot_location_x, double dot_location_y, double start_location_x, double start_location_y, double dot_location_x_2, double dot_location_y_2)    {

        double angle = Math.atan2(dot_location_y_2 - dot_location_y, dot_location_x_2 - dot_location_x);

        double x = Math.cos(angle);
        double y = Math.sin(angle);

        double dot_radius = DOT_RADIUS * PowerUpVariables.getDotSize();

        if (PowerUpVariables.getDotShield() == true)
            dot_radius = dot_radius * 1.5;

        TARGET_BALL_X_COORDINATES = dot_location_x + (x * dot_radius); //makes appear outside the dots radius
        TARGET_BALL_Y_COORDINATES = dot_location_y + (y * dot_radius); //makes appear outside the dots radius

        START_X = start_location_x;
        START_Y = start_location_y;

        ADJUSTED_X = 0;
        ADJUSTED_Y = 0;
    }
    
    public void updateAndDrawTargetBall(int list_num, double dot_location_x, double dot_location_y, double instantaneous_movement_x, double instantaneous_movement_y)     {

        double angle = Math.atan2(dot_location_y - TARGET_BALL_Y_COORDINATES, dot_location_x - TARGET_BALL_X_COORDINATES);

        double x = Math.cos(angle) * TARGET_BALL_SPEED;
        double y = Math.sin(angle) * TARGET_BALL_SPEED;

        ADJUSTED_X -= instantaneous_movement_x;
        ADJUSTED_Y -= instantaneous_movement_y;

        TARGET_BALL_X_COORDINATES += x;
        TARGET_BALL_Y_COORDINATES += y;

        double target_x = TARGET_BALL_X_COORDINATES - START_X;
        double target_y = TARGET_BALL_Y_COORDINATES - START_Y;

        target_x = target_x + MIDDLE_X;
        target_y = target_y + MIDDLE_Y;

        target_x = target_x + ADJUSTED_X;
        target_y = target_y + ADJUSTED_Y;

        canvas.drawCircle((float)target_x, (float)target_y, (float)TARGET_BALL_RADIUS, TARGET_BALL_COLOR);

        checkTargetBallCollision(list_num, dot_location_x, dot_location_y);

    }

    public boolean checkTargetBallCollision(int list_num, double dot_location_x, double dot_location_y) {

        if (MANAGE_OBJECTS_OBJECT != null) {
            float dot_radius = (float) (DOT_RADIUS * GameConstants.getDot2Size());

            if (GameConstants.getDot2Shield())
                dot_radius =(float)(dot_radius * 1.5);

            double distance = Math.sqrt(Math.pow((TARGET_BALL_X_COORDINATES - Constants.getOtherDotX()), 2) + Math.pow((TARGET_BALL_Y_COORDINATES - Constants.getOtherDotY()), 2));

            if (distance <= TARGET_BALL_RADIUS + dot_radius) {
                MANAGE_OBJECTS_OBJECT.removeTargetBallFromList(list_num);  //Gives the go ahead for ball to tbe remove from the list
                return true;
            }
        }else if (OTHER_MANAGE_OBJECTS_OBJECT != null) {

            float dot_radius = (float) (DOT_RADIUS * PowerUpVariables.getDotSize());

            if (PowerUpVariables.getDotShield())
                dot_radius = (float) (dot_radius * 1.5);

            double distance = Math.sqrt(Math.pow((TARGET_BALL_X_COORDINATES - dot_location_x), 2) + Math.pow((TARGET_BALL_Y_COORDINATES - dot_location_y), 2));

            if (distance <= TARGET_BALL_RADIUS + dot_radius) {
                OTHER_MANAGE_OBJECTS_OBJECT.removeTargetBallFromList(list_num);  //Gives the go ahead for ball to be remove from the list

                if (PowerUpVariables.getDotShield() == true) {
                    PowerUpVariables.setDotShield(false);
                }else {
                    Lives.setLives(Lives.getLives() - 1);
                }
                return true;
            }
        }
        return false;
    }
}
