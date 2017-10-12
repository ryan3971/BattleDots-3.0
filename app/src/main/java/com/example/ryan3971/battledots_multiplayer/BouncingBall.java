package com.example.ryan3971.battledots_multiplayer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by ryan3971 on 2/5/2016.
 */
public class BouncingBall {

    double LEFT;
    double RIGHT;
    double TOP;
    double BOTTOM;

    double WIDTH;
    double HEIGHT;

    double DOT_RADIUS;

    double MIDDLE_X;
    double MIDDLE_Y;

    double BALL_X_MOVE;
    double BALL_Y_MOVE;

    double BALL_X_COORDINATES;
    double BALL_Y_COORDINATES;

    double BALL_SPEED;
    double BALL_RADIUS;

    double ADJUSTED_X;
    double ADJUSTED_Y;

    double START_X, START_Y;

    Paint BALL_COLOR;


    Random random;

    GameConstants gameConstants;

    Canvas canvas;

    ManageObjects MANAGE_OBJECTS_OBJECT;
    OtherManageObjects OTHER_MANAGE_OBJECTS_OBJECT;

    public BouncingBall(Canvas canvasParameter, ManageObjects manageObjects, OtherManageObjects otherManageObjects)   {

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
    }
    
    public double randomDeciaml(double min, double max) {
        double range = max - min;
        double scaled = random.nextDouble() * range;
        double shifted = scaled + min;
        return shifted; // == (rand.nextDouble() * (max-min)) + min;
    }

    public void createBall(double dot_location_x, double dot_location_y, double start_location_x, double start_location_y, double angle) {

        double x = Math.cos(angle);
        double y = Math.sin(angle);

        BALL_X_MOVE = x * BALL_SPEED;
        BALL_Y_MOVE = y * BALL_SPEED;

        BALL_X_COORDINATES = dot_location_x;
        BALL_Y_COORDINATES = dot_location_y;

        double dot_radius = DOT_RADIUS * PowerUpVariables.getDotSize();

        if (PowerUpVariables.getDotShield() == true)
            dot_radius = dot_radius * 1.5;

        BALL_X_COORDINATES = dot_location_x + (x * dot_radius); //makes appear outside the dots radius
        BALL_Y_COORDINATES = dot_location_y + (y * dot_radius); //makes appear outside the dots radius

        START_X = start_location_x;
        START_Y = start_location_y;

        ADJUSTED_X = 0;
        ADJUSTED_Y = 0;

    }

    public void updateAndDrawBall(double dot_location_x, double dot_location_y, int list_num, double instantaneous_movement_x, double instantaneous_movement_y) {

        ADJUSTED_X -= instantaneous_movement_x;
        ADJUSTED_Y -= instantaneous_movement_y;

        double x = BALL_X_COORDINATES - START_X;
        double y = BALL_Y_COORDINATES - START_Y;

        x = x + MIDDLE_X;
        y = y + MIDDLE_Y;

        x = x + ADJUSTED_X;
        y = y + ADJUSTED_Y;

        canvas.drawCircle((float)x, (float)y, (float)BALL_RADIUS, BALL_COLOR);

        boolean ball_collide_with_2 = checkBallCollision(list_num, dot_location_x, dot_location_y);

        if (ball_collide_with_2 == false)
            checkBallLocation();

    }

    public void checkBallLocation() {

        double next_ball_x_coordinates = BALL_X_COORDINATES + BALL_X_MOVE;
        double next_ball_y_coordinates = BALL_Y_COORDINATES + BALL_Y_MOVE;


        double top = (TOP + BALL_RADIUS);
        double bottom = (BOTTOM - BALL_RADIUS);
        double right = (RIGHT - BALL_RADIUS);
        double left = (LEFT + BALL_RADIUS);


        if (next_ball_y_coordinates > bottom) {
            next_ball_y_coordinates = 2 * (bottom) - next_ball_y_coordinates;
            BALL_Y_MOVE = -Math.abs(BALL_Y_MOVE);

        }else if (next_ball_y_coordinates < top) {
            next_ball_y_coordinates = 2 * (top) - next_ball_y_coordinates;
            BALL_Y_MOVE = Math.abs(BALL_Y_MOVE);
        }
        if (next_ball_x_coordinates < left) {
            next_ball_x_coordinates = 2 * (left) - next_ball_x_coordinates;
            BALL_X_MOVE = Math.abs(BALL_X_MOVE);

        }else if(next_ball_x_coordinates > right) {
            next_ball_x_coordinates = 2 * (right) - next_ball_x_coordinates;
            BALL_X_MOVE = -Math.abs(BALL_X_MOVE);
        }
        BALL_Y_COORDINATES = next_ball_y_coordinates;
        BALL_X_COORDINATES = next_ball_x_coordinates;
    }

    public boolean checkBallCollision(int list_num, double dot_location_x, double dot_location_y) {

        if (MANAGE_OBJECTS_OBJECT != null) {
            float dot_radius = (float) (DOT_RADIUS * GameConstants.getDot2Size());

            if (GameConstants.getDot2Shield())
                dot_radius =(float)(dot_radius * 1.5);

            double distance = Math.sqrt(Math.pow((BALL_X_COORDINATES - Constants.getOtherDotX()), 2) + Math.pow((BALL_Y_COORDINATES - Constants.getOtherDotY()), 2));

            if (distance <= BALL_RADIUS + dot_radius) {
                MANAGE_OBJECTS_OBJECT.removeBallFromList(list_num);  //Gives the go ahead for ball to tbe remove from the list
                return true;
            }
        }else if (OTHER_MANAGE_OBJECTS_OBJECT != null) {

            float dot_radius = (float) (DOT_RADIUS * PowerUpVariables.getDotSize());

            if (PowerUpVariables.getDotShield())
                dot_radius = (float) (dot_radius * 1.5);

            double distance = Math.sqrt(Math.pow((BALL_X_COORDINATES - dot_location_x), 2) + Math.pow((BALL_Y_COORDINATES - dot_location_y), 2));

            if (distance <= BALL_RADIUS + dot_radius) {
                OTHER_MANAGE_OBJECTS_OBJECT.removeBallFromList(list_num);  //Gives the go ahead for ball to be remove from the list

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
