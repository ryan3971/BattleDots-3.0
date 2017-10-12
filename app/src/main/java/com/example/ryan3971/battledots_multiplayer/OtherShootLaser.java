package com.example.ryan3971.battledots_multiplayer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by ryan3971 on 2/5/2016.
 */
public class OtherShootLaser {

    double LEFT;
    double RIGHT;
    double TOP;
    double BOTTOM;

    double WIDTH;
    double HEIGHT;

    double DOT_RADIUS;

    double MIDDLE_X;
    double MIDDLE_Y;

    double LASER_X_MOVE;
    double LASER_Y_MOVE;

    double LASER_X_COORDINATES;
    double LASER_Y_COORDINATES;

    double LASER_SPEED;
    double LASER_RADIUS;

    double START_X, START_Y;
    double ADJUSTED_X, ADJUSTED_Y;

    Paint LASER_COLOR;

    GameConstants gameConstants;
    Random random;
    PowerUpVariables powerUpVariables;
    Canvas canvas;

    public OtherShootLaser(Canvas canvasParameter)     {

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

        LASER_SPEED = WIDTH * 0.001 ;       //change latter
        LASER_RADIUS = WIDTH * 0.025;

        random = new Random();
        powerUpVariables = new PowerUpVariables();
        canvas = canvasParameter;

        LASER_COLOR = new Paint();
        LASER_COLOR.setColor(Color.rgb(0,0,0));

    }

    public void createLaser(double dot_location_x, double dot_location_y, double start_location_x, double start_location_y, double angle)   {

        double x = Math.cos(angle);
        double y = Math.sin(angle);

        LASER_X_MOVE = x * LASER_SPEED;
        LASER_Y_MOVE = y * LASER_SPEED;

        float dot_radius = (float)(DOT_RADIUS * powerUpVariables.getDotSize());

        if (powerUpVariables.getDotShield() == true)
            dot_radius = (float)(dot_radius * 1.5);

        LASER_X_COORDINATES = dot_location_x + (x * dot_radius); //makes appear outside the dots radius
        LASER_Y_COORDINATES = dot_location_y + (y * dot_radius); //makes appear outside the dots radius

        START_X = start_location_x;
        START_Y = start_location_y;

        ADJUSTED_X = 0;
        ADJUSTED_Y = 0;
    }

    public void updateAndDrawLaser(int list_num, double dot_location_x, double dot_location_y, double instantaneous_movement_x, double instantaneous_movement_y, OtherManageObjects ManageObjects_Object) {

        ADJUSTED_X -= instantaneous_movement_x;
        ADJUSTED_Y -= instantaneous_movement_y;

        double x = LASER_X_COORDINATES - START_X;
        double y = LASER_Y_COORDINATES - START_Y;

        x = x + MIDDLE_X;
        y = y + MIDDLE_Y;

        x = x + ADJUSTED_X;
        y = y + ADJUSTED_Y;

        canvas.drawCircle((float) x, (float) y, (float) LASER_RADIUS, LASER_COLOR);

        boolean laser_collide_with_2 = checkLaserCollision(list_num, dot_location_x, dot_location_y, ManageObjects_Object);

        if (laser_collide_with_2 == false)
            checkLaserLocation(list_num, ManageObjects_Object);


    }
    
    public void checkLaserLocation(int list_num, OtherManageObjects ManageObjects_Object)     {

        double laser_x_coordinates = LASER_X_COORDINATES;
        double laser_y_coordinates = LASER_Y_COORDINATES;

        double  next_laser_x_coordinates = LASER_X_COORDINATES + LASER_X_MOVE;
        double next_laser_y_coordinates = LASER_Y_COORDINATES + LASER_Y_MOVE;

        double top = (TOP + (LASER_RADIUS * 2));
        double bottom = (BOTTOM - (LASER_RADIUS * 2));
        double right = (RIGHT - (LASER_RADIUS * 2));
        double left = (LEFT + (LASER_RADIUS * 2));

        if (laser_y_coordinates == top || laser_y_coordinates == bottom
        || laser_x_coordinates == right || laser_x_coordinates == left) {

            ManageObjects_Object.removeLaserFromList(list_num);   //Gives the go ahead for laser to be remove from the list

        } else if (next_laser_y_coordinates <= top || next_laser_y_coordinates >= bottom
                || next_laser_x_coordinates >= right || next_laser_x_coordinates <= left) {

            if (next_laser_x_coordinates >= right) {
                laser_x_coordinates = right;
            }
            if (next_laser_x_coordinates <= left) {
                laser_x_coordinates = left;
            }
            if (next_laser_y_coordinates <= top) {
                laser_y_coordinates = top;
            }
            if (next_laser_y_coordinates >= bottom) {
                laser_y_coordinates = bottom;
            }

            LASER_X_COORDINATES = laser_x_coordinates;
            LASER_Y_COORDINATES = laser_y_coordinates;

        }else {
            LASER_X_COORDINATES = next_laser_x_coordinates;
            LASER_Y_COORDINATES = next_laser_y_coordinates;
        }
    }
    public boolean checkLaserCollision(int list_num, double dot_location_x, double dot_location_y, OtherManageObjects ManageObjects_Object) {

        float dot_radius = (float) (DOT_RADIUS * powerUpVariables.getDotSize());

        if (powerUpVariables.getDotShield())
            dot_radius = (float) (dot_radius * 1.5);

        double distance = Math.sqrt(Math.pow((LASER_X_COORDINATES - dot_location_x), 2) + Math.pow((LASER_Y_COORDINATES - dot_location_y), 2));

        if (distance <= LASER_RADIUS + dot_radius) {
            ManageObjects_Object.removeLaserFromList(list_num);  //Gives the go ahead for laser to be remove from the list

            if (powerUpVariables.getDotShield() == true) {
                powerUpVariables.setDotShield(false);
            }else {
                Lives.setLives(Lives.getLives() - 1);
            }
            return true;
    }
    return false;
    }
}
