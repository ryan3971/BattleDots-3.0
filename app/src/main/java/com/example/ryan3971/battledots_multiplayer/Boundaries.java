package com.example.ryan3971.battledots_multiplayer;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by ryan3971 on 2/4/2016.
 */
public class Boundaries {

    double LEFT;
    double RIGHT;
    double TOP;
    double BOTTOM;
    double OBSTACLE_AND_POWERUPS_MOVEMENT_X;
    double OBSTACLE_AND_POWERUPS_MOVEMENT_Y;

    double WIDTH;
    double HEIGHT;
    double MIDDLE_X;
    double MIDDLE_Y;
    double DOT_RADIUS;

    double DOT_X;
    double DOT_Y;


    double INSTANTANEOUS_MOVEMENT_X;
    double INSTANTANEOUS_MOVEMENT_Y;

    GameConstants gameConstants;

    public Boundaries() {

        gameConstants = new GameConstants();
        HEIGHT = gameConstants.getHeight();
        WIDTH = gameConstants.getWidth();
        DOT_RADIUS = gameConstants.DOT_RADIUS;
        MIDDLE_Y = gameConstants.MIDDLE_Y;
        MIDDLE_X = gameConstants.MIDDLE_X;

        TOP = gameConstants.TOP;
        BOTTOM = gameConstants.BOTTOM;
        RIGHT = gameConstants.RIGHT;
        LEFT = gameConstants.LEFT;

        setDotY(BOTTOM / 2);
        setDotX(RIGHT / 2);

        OBSTACLE_AND_POWERUPS_MOVEMENT_Y = (-BOTTOM / 2) + MIDDLE_Y;
        OBSTACLE_AND_POWERUPS_MOVEMENT_X = (-RIGHT / 2) + MIDDLE_X;

    }

    public static float convertDpToPixel(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public void updateBoundaries(double padMiddleX, double padMiddleY, double touchX, double touchY, boolean isTouch)      {

        double x = 0;   // may change how this is handle when not touching the screen
        double y = 0;

        if (isTouch) {
            double angle = Math.atan2(touchY - padMiddleY, touchX - padMiddleX);

            x = Math.cos(angle);
            y = Math.sin(angle);
        }

        if (PowerUpVariables.IsSpeedBoost() == true) {    //Check if speed boost
            PowerUpVariables.speedBoost();
        }
        y = y * PowerUpVariables.getDotSpeed() * PowerUpVariables.getSpeedBoost();      //increases speed, multiplies here for simplicity
        x = x * PowerUpVariables.getDotSpeed() * PowerUpVariables.getSpeedBoost();     //Multiplying may cause issues with boundaries
        //add speed boost if any
        if (PowerUpVariables.getDotOppositeDirecton() == false) {
            y = y;
            x = x;
        }else if (PowerUpVariables.getDotOppositeDirecton() == true) {
            y = -y;
            x = -x;
        }

/*
        double X1 = Math.cos(0) * PowerUpVariables.getDotSpeed();

        Log.w("SPEED", "X-Speed   -   " + X1);
        Log.w("SPEED", "WIDTH     -   " + WIDTH);
        Log.w("SPEED", "TOGETHER  -   " + (WIDTH / X1));
*/

        double newX = getDotX() + x;
        double newY = getDotY() + y;

        checkX(newX, x);
        checkY(newY, y);

    }

    public void drawBoundaries(Canvas canvas) {

        double boundary_top = TOP + MIDDLE_Y;
        double boundary_bottom = BOTTOM - MIDDLE_Y;
        double boundary_right = RIGHT - MIDDLE_X;
        double boundary_left = LEFT + MIDDLE_X;

        Paint rectColor = new Paint();
        rectColor.setColor(Color.BLUE);

        if (getDotY() < boundary_top) {
            float rectHeight = (float)(boundary_top - getDotY());
            canvas.drawRect(0, 0, (float)WIDTH, rectHeight, rectColor);
        }

        if (getDotY() > boundary_bottom) {
            float rectHeight = (float)((HEIGHT) - (getDotY() - boundary_bottom));
            canvas.drawRect(0, rectHeight, (float)WIDTH, (float)HEIGHT, rectColor);
        }

        if (getDotX() > boundary_right) {
            float rectWidth = (float)(WIDTH - (getDotX() - boundary_right));
            canvas.drawRect(rectWidth, 0, (float)WIDTH, (float)HEIGHT, rectColor);
        }

        if (getDotX() < boundary_left) {
            float rectWidth = (float)(boundary_left - getDotX());
            canvas.drawRect(0, 0, rectWidth, (float)HEIGHT, rectColor);
        }
    }

    public void checkX(double newDotX, double x)    {

        float dot_radius = (float)(DOT_RADIUS * PowerUpVariables.getDotSize());

        if (PowerUpVariables.getDotShield() == true) {
            dot_radius = (float)(dot_radius * 1.5);
        }

        if (newDotX + dot_radius > RIGHT || newDotX - dot_radius < LEFT){
            if (newDotX + dot_radius > RIGHT) {

                double adjust_obstacle_placement = RIGHT - (getDotX() + dot_radius);
                updateObstaclesAndPowerUpsMovement_X(adjust_obstacle_placement);
                setInstantaneousMovementX(adjust_obstacle_placement);
                setDotX(getDotX() + adjust_obstacle_placement);

            }else if (newDotX - dot_radius < LEFT) {

                double adjust_obstacle_placement = LEFT + (getDotX() - dot_radius);
                updateObstaclesAndPowerUpsMovement_X(-adjust_obstacle_placement);
                setInstantaneousMovementX(adjust_obstacle_placement);
                setDotX(getDotX() - adjust_obstacle_placement);
            }
        }else {
            setDotX(newDotX);
            updateObstaclesAndPowerUpsMovement_X( x);
            setInstantaneousMovementX(x);
        }
    }

    public void checkY(double newDotY, double y)    {

        float dot_radius = (float)(DOT_RADIUS * PowerUpVariables.getDotSize());

        if (PowerUpVariables.getDotShield() == true) {
            dot_radius = (float)(dot_radius * 1.5);
        }

        if (newDotY - dot_radius < TOP || newDotY + dot_radius > BOTTOM) {
            if (newDotY - dot_radius < TOP) {

                double adjust_obstacle_placement = TOP + (getDotY() - dot_radius);
                updateObstaclesAndPowerUpsMovement_Y(-adjust_obstacle_placement);
                setInstantaneousMovementY(adjust_obstacle_placement);
                setDotY(getDotY() - adjust_obstacle_placement);

            }else if (newDotY + dot_radius > BOTTOM) {

                double adjust_obstacle_placement = BOTTOM - (getDotY() + dot_radius);
                updateObstaclesAndPowerUpsMovement_Y(adjust_obstacle_placement);
                setInstantaneousMovementY(adjust_obstacle_placement);
                setDotY(getDotY() + adjust_obstacle_placement);
            }
        }else {
            setDotY(newDotY);
            updateObstaclesAndPowerUpsMovement_Y(y);
            setInstantaneousMovementY(y);
        }
    }

    public void setDotX(double newX) {
        DOT_X = newX;
    }
    public void setDotY(double newY) {
        DOT_Y = newY;
    }
    public double getDotX() {
        return DOT_X;
    }
    public double getDotY() {
        return DOT_Y;
    }

    public void updateObstaclesAndPowerUpsMovement_X(double addX) {
        OBSTACLE_AND_POWERUPS_MOVEMENT_X = OBSTACLE_AND_POWERUPS_MOVEMENT_X - addX;
    }
    public void updateObstaclesAndPowerUpsMovement_Y(double addY) {
        OBSTACLE_AND_POWERUPS_MOVEMENT_Y = OBSTACLE_AND_POWERUPS_MOVEMENT_Y - addY;
    }
    public double getObstaclesAndPowerUpsMovement_X() {
        return OBSTACLE_AND_POWERUPS_MOVEMENT_X;
    }
    public double getObstaclesAndPowerUpsMovement_Y() {
        return OBSTACLE_AND_POWERUPS_MOVEMENT_Y;
    }
    public void setInstantaneousMovementX(double x) {
        INSTANTANEOUS_MOVEMENT_X = x;
    }
    public void setInstantaneousMovementY(double y) {
        INSTANTANEOUS_MOVEMENT_Y = y;
    }
    public double getInstantaneousMovementX() {
        return INSTANTANEOUS_MOVEMENT_X;
    }
    public double getInstantaneousMovementY() {
        return INSTANTANEOUS_MOVEMENT_Y;
    }
}