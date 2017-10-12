package com.example.ryan3971.battledots_multiplayer;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;

import java.util.Random;

/**
 * Created by ryan3971 on 2/5/2016.
 */
public class RapidFire {

    double LEFT;
    double RIGHT;
    double TOP;
    double BOTTOM;

    double WIDTH;
    double HEIGHT;

    double DOT_RADIUS;

    double MIDDLE_X;
    double MIDDLE_Y;

    double RAPID_FIRE_X_MOVE;
    double RAPID_FIRE_Y_MOVE;

    double RAPID_FIRE_X_COORDINATES;
    double RAPID_FIRE_Y_COORDINATES;

    double RAPID_FIRE_SPEED;
    double RAPID_FIRE_RADIUS;

    double START_X, START_Y;
    double ADJUSTED_X, ADJUSTED_Y;

    Paint RAPID_FIRE_COLOR;

    GameConstants gameConstants;
    Random random;
    Canvas canvas;

    ManageObjects MANAGE_OBJECTS_OBJECT;
    OtherManageObjects OTHER_MANAGE_OBJECTS_OBJECT;


    public static float convertDpToPixel(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public RapidFire(Canvas canvasParameter, ManageObjects manageObjects, OtherManageObjects otherManageObjects)     {

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

        RAPID_FIRE_SPEED = 20;
        RAPID_FIRE_RADIUS = WIDTH * 0.025;

        random = new Random();
        canvas = canvasParameter;

        RAPID_FIRE_COLOR = new Paint();
        RAPID_FIRE_COLOR.setColor(Color.rgb(75, 255, 75));

        MANAGE_OBJECTS_OBJECT = manageObjects;
        OTHER_MANAGE_OBJECTS_OBJECT = otherManageObjects;

    }

    public void createRapidFire(double dot_location_x, double dot_location_y, double start_location_x, double start_location_y, double angle)   {

        double x = Math.cos(angle);
        double y = Math.sin(angle);

        RAPID_FIRE_X_MOVE = x * RAPID_FIRE_SPEED;
        RAPID_FIRE_Y_MOVE = y * RAPID_FIRE_SPEED;

        float dot_radius = (float)(DOT_RADIUS * PowerUpVariables.getDotSize());

        if (PowerUpVariables.getDotShield() == true)
            dot_radius = (float)(dot_radius * 1.5);

        RAPID_FIRE_X_COORDINATES = dot_location_x + (x * dot_radius); //makes appear outside the dots radius
        RAPID_FIRE_Y_COORDINATES = dot_location_y + (y * dot_radius); //makes appear outside the dots radius

        START_X = start_location_x;
        START_Y = start_location_y;

        ADJUSTED_X = 0;
        ADJUSTED_Y = 0;
    }

    public void updateAndDrawRapidFire(int list_num, double dot_location_x, double dot_location_y, double instantaneous_movement_x, double instantaneous_movement_y) {

        ADJUSTED_X -= instantaneous_movement_x;
        ADJUSTED_Y -= instantaneous_movement_y;

        double x = RAPID_FIRE_X_COORDINATES - START_X;
        double y = RAPID_FIRE_Y_COORDINATES - START_Y;

        x = x + MIDDLE_X;
        y = y + MIDDLE_Y;

        x = x + ADJUSTED_X;
        y = y + ADJUSTED_Y;

        canvas.drawCircle((float) x, (float) y, (float) RAPID_FIRE_RADIUS, RAPID_FIRE_COLOR);

        boolean rapid_fire_collide_with_2 = checkRapidFireCollision(list_num, dot_location_x, dot_location_y);

        if (rapid_fire_collide_with_2 == false)
            checkRapidFireLocation(list_num);


    }

    public void checkRapidFireLocation(int list_num)     {

        double rapid_fire_x_coordinates = RAPID_FIRE_X_COORDINATES;
        double rapid_fire_y_coordinates = RAPID_FIRE_Y_COORDINATES;

        double top = (TOP - (RAPID_FIRE_RADIUS));
        double bottom = (BOTTOM + (RAPID_FIRE_RADIUS));
        double right = (RIGHT + (RAPID_FIRE_RADIUS));
        double left = (LEFT - (RAPID_FIRE_RADIUS));

        if (rapid_fire_y_coordinates < top || rapid_fire_y_coordinates > bottom
                || rapid_fire_x_coordinates > right || rapid_fire_x_coordinates < left) {

            if (MANAGE_OBJECTS_OBJECT != null)
                MANAGE_OBJECTS_OBJECT.removeRapidFireFromList(list_num);   //Gives the go ahead for rapid fire to be remove from the list
            else if (OTHER_MANAGE_OBJECTS_OBJECT != null)
                OTHER_MANAGE_OBJECTS_OBJECT.removeRapidFireFromList(list_num);   //Gives the go ahead for rapid fire to be remove from the list

        }else {

            RAPID_FIRE_X_COORDINATES += RAPID_FIRE_X_MOVE;
            RAPID_FIRE_Y_COORDINATES += RAPID_FIRE_Y_MOVE;
        }
    }
    public boolean checkRapidFireCollision(int list_num, double dot_location_x, double dot_location_y) {

        if (MANAGE_OBJECTS_OBJECT != null) {
            float dot_radius = (float) (DOT_RADIUS * GameConstants.getDot2Size());

            if (GameConstants.getDot2Shield())
                dot_radius =(float)(dot_radius * 1.5);

            double distance = Math.sqrt(Math.pow((RAPID_FIRE_X_COORDINATES - Constants.getOtherDotX()), 2) + Math.pow((RAPID_FIRE_Y_COORDINATES - Constants.getOtherDotY()), 2));

            if (distance <= RAPID_FIRE_RADIUS + dot_radius) {
                MANAGE_OBJECTS_OBJECT.removeRapidFireFromList(list_num);  //Gives the go ahead for rapid fire to tbe remove from the list
                return true;
            }
        }else if (OTHER_MANAGE_OBJECTS_OBJECT != null) {

            float dot_radius = (float) (DOT_RADIUS * PowerUpVariables.getDotSize());

            if (PowerUpVariables.getDotShield())
                dot_radius = (float) (dot_radius * 1.5);

            double distance = Math.sqrt(Math.pow((RAPID_FIRE_X_COORDINATES - dot_location_x), 2) + Math.pow((RAPID_FIRE_Y_COORDINATES - dot_location_y), 2));

            if (distance <= RAPID_FIRE_RADIUS + dot_radius) {
                OTHER_MANAGE_OBJECTS_OBJECT.removeRapidFireFromList(list_num);  //Gives the go ahead for rapid fire to be remove from the list

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
