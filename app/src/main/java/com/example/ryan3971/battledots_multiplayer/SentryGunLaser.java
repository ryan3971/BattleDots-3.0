package com.example.ryan3971.battledots_multiplayer;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Random;

/**
 * Created by ryan3971 on 2/5/2016.
 */
public class SentryGunLaser {

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

    double SHOOTER_LENGTH;

    Paint LASER_COLOR;

    GameConstants gameConstants;
    Random random;
    Canvas canvas;

    ManageObjects MANAGE_OBJECTS_OBJECT;
    OtherManageObjects OTHER_MANAGE_OBJECTS_OBJECT;

    SentryGun SENTRY_GUN_OBJECT;


    public SentryGunLaser(Canvas canvasParameter, SentryGun SentryGunObject, ManageObjects manageObjects, OtherManageObjects otherManageObjects){

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

        LASER_SPEED = 10;
        LASER_RADIUS = WIDTH * 0.01;

        random = new Random();
        canvas = canvasParameter;

        LASER_COLOR = new Paint();
        LASER_COLOR.setColor(Color.rgb(255,165,0));

        SHOOTER_LENGTH = WIDTH * 0.035;

        SENTRY_GUN_OBJECT = SentryGunObject;

        MANAGE_OBJECTS_OBJECT = manageObjects;
        OTHER_MANAGE_OBJECTS_OBJECT = otherManageObjects;

    }

    public void createSentryGunLaser(double dot_location_x, double dot_location_y, double start_location_x, double start_location_y, double angle)   {

        double x = Math.cos(angle);
        double y = Math.sin(angle);

        LASER_X_MOVE = x * LASER_SPEED;
        LASER_Y_MOVE = y * LASER_SPEED;

        LASER_X_COORDINATES = dot_location_x + (x * SHOOTER_LENGTH); //makes appear at the shooters end
        LASER_Y_COORDINATES = dot_location_y + (y * SHOOTER_LENGTH); //makes appear at the shooters end

        START_X = start_location_x;
        START_Y = start_location_y;

        ADJUSTED_X = 0;
        ADJUSTED_Y = 0;
    }

    public void updateAndDrawSentryGunLaser(int list_num, double dot_location_x, double dot_location_y, double instantaneous_movement_x, double instantaneous_movement_y) {

        ADJUSTED_X -= instantaneous_movement_x;
        ADJUSTED_Y -= instantaneous_movement_y;

        double x = LASER_X_COORDINATES - START_X;
        double y = LASER_Y_COORDINATES - START_Y;

        x = x + MIDDLE_X;
        y = y + MIDDLE_Y;

        x = x + ADJUSTED_X;
        y = y + ADJUSTED_Y;

        canvas.drawCircle((float) x, (float) y, (float) LASER_RADIUS, LASER_COLOR);

        boolean laser_collide_with_2 = checkLaserCollision(list_num, dot_location_x, dot_location_y);

        if (laser_collide_with_2 == false)
            checkSentryGunLaserLocation(list_num);


    }

    public void checkSentryGunLaserLocation(int list_num)     {

        double laser_x_coordinates = LASER_X_COORDINATES;
        double laser_y_coordinates = LASER_Y_COORDINATES;

        double top = (TOP - (LASER_RADIUS));
        double bottom = (BOTTOM + (LASER_RADIUS));
        double right = (RIGHT + (LASER_RADIUS));
        double left = (LEFT - (LASER_RADIUS));

        if (laser_y_coordinates < top || laser_y_coordinates > bottom
                || laser_x_coordinates > right || laser_x_coordinates < left) {

            if (MANAGE_OBJECTS_OBJECT != null)
                SENTRY_GUN_OBJECT.removeSentryGunLaserFromList(list_num);   //Gives the go ahead for laser to be remove from the list
            else if (OTHER_MANAGE_OBJECTS_OBJECT != null)
                SENTRY_GUN_OBJECT.removeOtherSentryGunLaserFromList(list_num);   //Gives the go ahead for laser to be remove from the list

        }else {

            LASER_X_COORDINATES += LASER_X_MOVE;
            LASER_Y_COORDINATES += LASER_Y_MOVE;
        }
    }
    public boolean checkLaserCollision(int list_num, double dot_location_x, double dot_location_y) {

        if (MANAGE_OBJECTS_OBJECT != null) {
            float dot_radius = (float) (DOT_RADIUS * GameConstants.getDot2Size());

            if (GameConstants.getDot2Shield())
                dot_radius =(float)(dot_radius * 1.5);

            double distance = Math.sqrt(Math.pow((LASER_X_COORDINATES - Constants.getOtherDotX()), 2) + Math.pow((LASER_Y_COORDINATES - Constants.getOtherDotY()), 2));

            if (distance <= LASER_RADIUS + dot_radius) {
                SENTRY_GUN_OBJECT.removeSentryGunLaserFromList(list_num);
                return true;
            }
        }else if (OTHER_MANAGE_OBJECTS_OBJECT != null) {

            float dot_radius = (float) (DOT_RADIUS * PowerUpVariables.getDotSize());

            if (PowerUpVariables.getDotShield())
                dot_radius = (float) (dot_radius * 1.5);

            double distance = Math.sqrt(Math.pow((LASER_X_COORDINATES - dot_location_x), 2) + Math.pow((LASER_Y_COORDINATES - dot_location_y), 2));

            if (distance <= LASER_RADIUS + dot_radius) {
                SENTRY_GUN_OBJECT.removeOtherSentryGunLaserFromList(list_num);

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
