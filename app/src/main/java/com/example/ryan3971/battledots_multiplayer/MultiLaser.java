package com.example.ryan3971.battledots_multiplayer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by ryan3971 on 2/5/2016.
 */
public class MultiLaser {

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
    Canvas canvas;

    ManageObjects MANAGE_OBJECTS_OBJECT;
    OtherManageObjects OTHER_MANAGE_OBJECTS_OBJECT;

    public MultiLaser(Canvas canvasParameter, ManageObjects manageObjects, OtherManageObjects otherManageObjects)     {

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

        LASER_SPEED = 15;       //change latter
        LASER_RADIUS = WIDTH * 0.015 ;

        LASER_COLOR = new Paint();
        LASER_COLOR.setColor(Color.rgb(255, 255, 0));

        random = new Random();
        canvas = canvasParameter;


        MANAGE_OBJECTS_OBJECT = manageObjects;
        OTHER_MANAGE_OBJECTS_OBJECT = otherManageObjects;
    }

    public double randomDeciaml(double min, double max) {
        double range = max - min;
        double scaled = random.nextDouble() * range;
        double shifted = scaled + min;
        return shifted; // == (rand.nextDouble() * (max-min)) + min;
    }

    public void createMultiLaser(double dot_location_x, double dot_location_y, double start_location_x, double start_location_y, double angle)     {

        double x = Math.cos(angle);
        double y = Math.sin(angle);

        LASER_X_MOVE = x * LASER_SPEED;
        LASER_Y_MOVE = y * LASER_SPEED;

        double dot_radius = DOT_RADIUS * PowerUpVariables.getDotSize();

        if (PowerUpVariables.getDotShield() == true)
            dot_radius = dot_radius * 1.5;

        LASER_X_COORDINATES = dot_location_x + (x * dot_radius); //makes appear outside the dots radius
        LASER_Y_COORDINATES = dot_location_y + (y * dot_radius); //makes appear outside the dots radius

        START_X = start_location_x;
        START_Y = start_location_y;

        LASER_X_COORDINATES = dot_location_x;
        LASER_Y_COORDINATES = dot_location_y;

        ADJUSTED_X = 0;
        ADJUSTED_Y = 0;
    }
    
    public void updateAndDrawMultiLaser(int list_num, double dot_location_x, double dot_location_y, double instantaneous_movement_x, double instantaneous_movement_y)     {

        ADJUSTED_X -= instantaneous_movement_x;
        ADJUSTED_Y -= instantaneous_movement_y;

        double x = LASER_X_COORDINATES - START_X;
        double y = LASER_Y_COORDINATES - START_Y;

        x = x + MIDDLE_X;
        y = y + MIDDLE_Y;

        x = x + ADJUSTED_X;
        y = y + ADJUSTED_Y;

        canvas.drawCircle((float)x, (float)y, (float)LASER_RADIUS, LASER_COLOR);

        boolean laser_collide_with_2 = checkMultiLaserCollision(list_num, dot_location_x, dot_location_y);

        if (laser_collide_with_2 == false)
            checkMultiLaserLocation(list_num);

    }

    public void checkMultiLaserLocation(int list_num)     {

        double laser_x_coordinates = LASER_X_COORDINATES;
        double laser_y_coordinates = LASER_Y_COORDINATES;

        double top = (TOP - (LASER_RADIUS));
        double bottom = (BOTTOM + (LASER_RADIUS));
        double right = (RIGHT + (LASER_RADIUS));
        double left = (LEFT - (LASER_RADIUS));

        if (laser_y_coordinates < top || laser_y_coordinates > bottom
                || laser_x_coordinates > right || laser_x_coordinates < left) {

            if (MANAGE_OBJECTS_OBJECT != null)
                MANAGE_OBJECTS_OBJECT.removeMultiLaserFromList(list_num);   //Gives the go ahead for laser to be remove from the list
            else if (OTHER_MANAGE_OBJECTS_OBJECT != null)
                OTHER_MANAGE_OBJECTS_OBJECT.removeMultiLaserFromList(list_num);   //Gives the go ahead for laser to be remove from the list

        }else {

            LASER_X_COORDINATES += LASER_X_MOVE;
            LASER_Y_COORDINATES += LASER_Y_MOVE;
        }
    }
    public boolean checkMultiLaserCollision(int list_num, double dot_location_x, double dot_location_y) {

        if (MANAGE_OBJECTS_OBJECT != null) {
            float dot_radius = (float) (DOT_RADIUS * GameConstants.getDot2Size());

            if (GameConstants.getDot2Shield())
                dot_radius =(float)(dot_radius * 1.5);

            double distance = Math.sqrt(Math.pow((LASER_X_COORDINATES - Constants.getOtherDotX()), 2) + Math.pow((LASER_Y_COORDINATES - Constants.getOtherDotY()), 2));

            if (distance <= LASER_RADIUS + dot_radius) {
                MANAGE_OBJECTS_OBJECT.removeMultiLaserFromList(list_num);  //Gives the go ahead for laser to tbe remove from the list
                return true;
            }
        }else if (OTHER_MANAGE_OBJECTS_OBJECT != null) {

            float dot_radius = (float) (DOT_RADIUS * PowerUpVariables.getDotSize());

            if (PowerUpVariables.getDotShield())
                dot_radius = (float) (dot_radius * 1.5);

            double distance = Math.sqrt(Math.pow((LASER_X_COORDINATES - dot_location_x), 2) + Math.pow((LASER_Y_COORDINATES - dot_location_y), 2));

            if (distance <= LASER_RADIUS + dot_radius) {
                OTHER_MANAGE_OBJECTS_OBJECT.removeMultiLaserFromList(list_num);  //Gives the go ahead for laser to be remove from the list

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
