package com.example.ryan3971.battledots_multiplayer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by ryan3971 on 2/5/2016.
 */
public class PowerWave {
    
    GameConstants gameConstants;

    double CENTER_WAVE_X;
    double CENTER_WAVE_Y;

    double HEIGHT;
    double WIDTH;
    double DOT_RADIUS;
    double MIDDLE_Y;
    double MIDDLE_X;

    double WAVE_RADIUS;
    float WAVE_BORDER;

    double POWER_WAVE_X_COORDINATES, POWER_WAVE_Y_COORDINATES;
    double START_X, START_Y;
    double ADJUSTED_X, ADJUSTED_Y;

    Paint WAVE_COLOR;
    Canvas canvas;

    ManageObjects MANAGE_OBJECTS_OBJECT;
    OtherManageObjects OTHER_MANAGE_OBJECTS_OBJECT;

    public PowerWave(double dot_location_x, double dot_location_y, double start_location_x, double start_location_y, Canvas canvasParameter, ManageObjects manageObjects, OtherManageObjects otherManageObjects)  {

        CENTER_WAVE_X = dot_location_x;     //used for determineing collision with other player
        CENTER_WAVE_Y = dot_location_y;

        MANAGE_OBJECTS_OBJECT = manageObjects;
        OTHER_MANAGE_OBJECTS_OBJECT = otherManageObjects;

        gameConstants = new GameConstants();
        HEIGHT = gameConstants.getHeight();
        WIDTH = gameConstants.getWidth();
        DOT_RADIUS = gameConstants.DOT_RADIUS;
        MIDDLE_Y = gameConstants.MIDDLE_Y;
        MIDDLE_X = gameConstants.MIDDLE_X;

        float dot_radius = (float)(DOT_RADIUS * PowerUpVariables.getDotSize());

        if (PowerUpVariables.getDotShield() == true)
            dot_radius = (float)(dot_radius * 1.5);

        WAVE_RADIUS = dot_radius;
        WAVE_BORDER = 10f;
        WAVE_COLOR = new Paint();
        WAVE_COLOR.setColor(Color.rgb(0, 255, 150));
        WAVE_COLOR.setStyle(Paint.Style.STROKE);
        WAVE_COLOR.setStrokeWidth(WAVE_BORDER);

        canvas = canvasParameter;

        POWER_WAVE_X_COORDINATES = dot_location_x;
        POWER_WAVE_Y_COORDINATES = dot_location_y;

        START_X = start_location_x;
        START_Y = start_location_y;

        ADJUSTED_X = 0;
        ADJUSTED_Y = 0;
    }
    
    public boolean drawWave(double dot_location_x, double dot_location_y, double instantaneous_movement_x, double instantaneous_movement_y) {

        WAVE_RADIUS += 10;

        ADJUSTED_X -= instantaneous_movement_x;
        ADJUSTED_Y -= instantaneous_movement_y;

        double x = POWER_WAVE_X_COORDINATES - START_X;
        double y = POWER_WAVE_Y_COORDINATES - START_Y;

        x = x + MIDDLE_X;
        y = y + MIDDLE_Y;

        x = x + ADJUSTED_X;
        y = y + ADJUSTED_Y;

        canvas.drawCircle((float) x, (float) y, (float)WAVE_RADIUS, WAVE_COLOR);

        checkPowerWaveCollision(dot_location_x, dot_location_y);

        if (WAVE_RADIUS * 2 >= WIDTH) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPowerWaveCollision(double dot_location_x, double dot_location_y) {

        if (MANAGE_OBJECTS_OBJECT != null) {
            float dot_radius = (float) (DOT_RADIUS * GameConstants.getDot2Size());

            if (GameConstants.getDot2Shield())
                dot_radius =(float)(dot_radius * 1.5);

            double distance = Math.sqrt(Math.pow((POWER_WAVE_X_COORDINATES - Constants.getOtherDotX()), 2) + Math.pow((POWER_WAVE_Y_COORDINATES - Constants.getOtherDotY()), 2));

            if (distance <= WAVE_RADIUS + dot_radius) {
                MANAGE_OBJECTS_OBJECT.removePowerWave();  //Gives the go ahead for wave to tbe remove from the list
                return true;
            }
        }else if (OTHER_MANAGE_OBJECTS_OBJECT != null) {

            float dot_radius = (float) (DOT_RADIUS * PowerUpVariables.getDotSize());

            if (PowerUpVariables.getDotShield())
                dot_radius = (float) (dot_radius * 1.5);

            double distance = Math.sqrt(Math.pow((POWER_WAVE_X_COORDINATES - dot_location_x), 2) + Math.pow((POWER_WAVE_Y_COORDINATES - dot_location_y), 2));

            if (distance <= WAVE_RADIUS + dot_radius) {
                OTHER_MANAGE_OBJECTS_OBJECT.removePowerWave();  //Gives the go ahead for wave to be remove from the list

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
