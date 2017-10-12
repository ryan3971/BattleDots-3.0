package com.example.ryan3971.battledots_multiplayer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Ryan Tyrrell on 2016-08-26.
 */
public class ObstacleDrop {


    double LEFT;
    double RIGHT;
    double TOP;
    double BOTTOM;

    double WIDTH;
    double HEIGHT;

    double DOT_RADIUS;

    double MIDDLE_X;
    double MIDDLE_Y;

    Random random;

    GameConstants gameConstants;

    Canvas canvas;

    ManageObjects MANAGE_OBJECTS_OBJECT;
    OtherManageObjects OTHER_MANAGE_OBJECTS_OBJECT;

    ArrayList<Double> PREVIOUS_DOT_LOCATION_X, PREVIOUS_DOT_LOCATION_Y;
    Paint OBSTACLE_COLOR;

    public ObstacleDrop(Canvas canvasParameter, ManageObjects manageObjects, OtherManageObjects otherManageObjects)  {

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

        OBSTACLE_COLOR = new Paint();
        OBSTACLE_COLOR.setColor(Color.GREEN);

        random = new Random();
        canvas = canvasParameter;

        MANAGE_OBJECTS_OBJECT = manageObjects;
        OTHER_MANAGE_OBJECTS_OBJECT = otherManageObjects;

        PREVIOUS_DOT_LOCATION_X = new ArrayList<>();
        PREVIOUS_DOT_LOCATION_Y = new ArrayList<>();


        PREVIOUS_DOT_LOCATION_X.add(0.0); // so they are not empty
        PREVIOUS_DOT_LOCATION_Y.add(0.0);

    }

    public void createFollowingObstacles()      {





    }

    int counter1 = 0, counter2 = 0;
    int OBSTACLE_COUNTER = 0;

    public void followTheBall(double dot_location_x, double dot_location_y)     {

        double x = 0;
        double y = 0;
        double obstacle_location_x = 0;
        double obstacle_location_y = 0;

        if (PREVIOUS_DOT_LOCATION_X.get((PREVIOUS_DOT_LOCATION_X.size() - 1)) != dot_location_x && PREVIOUS_DOT_LOCATION_Y.get((PREVIOUS_DOT_LOCATION_Y.size() - 1)) != dot_location_y) {

            PREVIOUS_DOT_LOCATION_X.add(dot_location_x);
            PREVIOUS_DOT_LOCATION_Y.add(dot_location_y);

            int list_size = PREVIOUS_DOT_LOCATION_X.size();

            if (list_size >= 20) {
                counter1++;
            }
            if (list_size >= 40) {
                counter2++;
            }
        }

        int list_size = PREVIOUS_DOT_LOCATION_X.size();

        if (list_size >= 20) {
            x = PREVIOUS_DOT_LOCATION_X.get(counter1);
            y = PREVIOUS_DOT_LOCATION_Y.get(counter1);

        } else {
            x = PREVIOUS_DOT_LOCATION_X.get(1);
            y = PREVIOUS_DOT_LOCATION_Y.get(1);
        }

        if (OBSTACLE_COUNTER < 3) {
            obstacle_location_x = (x - dot_location_x) + MIDDLE_X;
            obstacle_location_y = (y - dot_location_y) + MIDDLE_Y;

            canvas.drawCircle((float) obstacle_location_x, (float) obstacle_location_y, (float) DOT_RADIUS, OBSTACLE_COLOR);
        }
        if (list_size > 40) {
            x = PREVIOUS_DOT_LOCATION_X.get(counter2);
            y = PREVIOUS_DOT_LOCATION_Y.get(counter2);

        } else {

            x = PREVIOUS_DOT_LOCATION_X.get(1);
            y = PREVIOUS_DOT_LOCATION_Y.get(1);
        }

        if (OBSTACLE_COUNTER < 2) {
            obstacle_location_x = (x - dot_location_x) + MIDDLE_X;
            obstacle_location_y = (y - dot_location_y) + MIDDLE_Y;

            canvas.drawCircle((float) obstacle_location_x, (float) obstacle_location_y, (float) DOT_RADIUS, OBSTACLE_COLOR);
        }

        if (list_size >= 60) {

            x = PREVIOUS_DOT_LOCATION_X.get(1);
            y = PREVIOUS_DOT_LOCATION_Y.get(1);

            PREVIOUS_DOT_LOCATION_X.remove(0);
            PREVIOUS_DOT_LOCATION_Y.remove(0);
            counter1--;
            counter2--;

        } else {

            x = PREVIOUS_DOT_LOCATION_X.get(1);
            y = PREVIOUS_DOT_LOCATION_Y.get(1);
        }

        if (OBSTACLE_COUNTER < 1) {

            obstacle_location_x = (x - dot_location_x) + MIDDLE_X;
            obstacle_location_y = (y - dot_location_y) + MIDDLE_Y;

            canvas.drawCircle((float) obstacle_location_x, (float) obstacle_location_y, (float) DOT_RADIUS, OBSTACLE_COLOR);
        }

        if (GameConstants.DO_FIRE_OBSTACLE_DROP)    {
            GameConstants.DO_FIRE_OBSTACLE_DROP = false;
            OBSTACLE_COUNTER++;
            MANAGE_OBJECTS_OBJECT.obstacleDropToObstacle(x, y);

            if (OBSTACLE_COUNTER == 3)
                MANAGE_OBJECTS_OBJECT.destroyObstacleDrop();
        }
    }
}
