package com.example.ryan3971.battledots_multiplayer;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ryan3971 on 2/5/2016.
 */
public class SentryGun {

    double LEFT;
    double RIGHT;
    double TOP;
    double BOTTOM;

    double WIDTH;
    double HEIGHT;

    double DOT_RADIUS;

    double MIDDLE_X;
    double MIDDLE_Y;

    ArrayList<Double> CIRCLE_COORDINATES_X_LIST;
    ArrayList<Double> CIRCLE_COORDINATES_Y_LIST;

    double SHOOTER_COORDINATES_X = 0;
    double SHOOTER_COORDINATES_Y = 0;
    double SHOOTER_LENGTH = 0;
    float SHOOTER_WIDTH;

    double SENTRY_GUN_RADIUS = 0;

    Paint SENTRY_GUN_COLOR;
    Paint SHOOTER_COLOR;

    double START_X, START_Y;
    double ADJUSTED_X, ADJUSTED_Y;

    ArrayList<SentryGunLaser> SENTRY_GUN_LASER_LIST;
    boolean DESTROY_SENTRY_GUN_LASER = false;
    int DESTROY_SENTRY_GUN_LASER_LIST_NUM = 0;

    int SHOOT_SENTRY_GUN = 0;

    GameConstants gameConstants;
    Random random;
    Canvas canvas;
    sendData sendData;

    ManageObjects MANAGE_OBJECTS_OBJECT;
    OtherManageObjects OTHER_MANAGE_OBJECTS_OBJECT;


    public static float convertDpToPixel(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public SentryGun(Canvas canvasParameter, ManageObjects manageObjects, OtherManageObjects otherManageObjects)     {

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

        SENTRY_GUN_RADIUS = WIDTH * 0.025;

        SENTRY_GUN_COLOR = new Paint();
        SENTRY_GUN_COLOR.setColor(Color.rgb(255, 255, 0));

        SHOOTER_WIDTH = 10f;
        SHOOTER_COLOR = new Paint();
        SHOOTER_COLOR.setColor(Color.rgb(0, 0, 0));
        SHOOTER_COLOR.setStyle(Paint.Style.STROKE);
        SHOOTER_COLOR.setStrokeWidth(SHOOTER_WIDTH);

        SHOOTER_LENGTH = WIDTH * 0.035;

        CIRCLE_COORDINATES_X_LIST = new ArrayList<>();
        CIRCLE_COORDINATES_Y_LIST = new ArrayList<>();

        SENTRY_GUN_LASER_LIST = new ArrayList<>();

        random = new Random();
        canvas = canvasParameter;
        sendData = new sendData();

        MANAGE_OBJECTS_OBJECT = manageObjects;
        OTHER_MANAGE_OBJECTS_OBJECT = otherManageObjects;

    }

    public void createSentryGun(double dot_location_x, double dot_location_y, double start_location_x, double start_location_y)   {

        START_X = start_location_x;
        START_Y = start_location_y;

        ADJUSTED_X = 0;
        ADJUSTED_Y = 0;

        SHOOTER_COORDINATES_X = dot_location_x;
        SHOOTER_COORDINATES_Y = dot_location_y;

        double circle_x = dot_location_x - SENTRY_GUN_RADIUS;
        double circle_y = dot_location_y + SENTRY_GUN_RADIUS;

        CIRCLE_COORDINATES_X_LIST.add(circle_x);
        CIRCLE_COORDINATES_Y_LIST.add(circle_y);

        circle_x = dot_location_x;
        circle_y = dot_location_y - SENTRY_GUN_RADIUS;

        CIRCLE_COORDINATES_X_LIST.add(circle_x);
        CIRCLE_COORDINATES_Y_LIST.add(circle_y);

        circle_x = dot_location_x + SENTRY_GUN_RADIUS;
        circle_y = dot_location_y + SENTRY_GUN_RADIUS;

        CIRCLE_COORDINATES_X_LIST.add(circle_x);
        CIRCLE_COORDINATES_Y_LIST.add(circle_y);

        circle_x = dot_location_x;
        circle_y = dot_location_y;

        CIRCLE_COORDINATES_X_LIST.add(circle_x);
        CIRCLE_COORDINATES_Y_LIST.add(circle_y);

    }

    public void updateAndDrawSentryGun(int list_num, double dot_location_x, double dot_location_y, double instantaneous_movement_x, double instantaneous_movement_y, double dot_location_x_2, double dot_location_y_2) {

        ADJUSTED_X -= instantaneous_movement_x;
        ADJUSTED_Y -= instantaneous_movement_y;

        for (int circle_num = 0; circle_num < 3; circle_num++) {

            double circle_x = CIRCLE_COORDINATES_X_LIST.get(circle_num) - START_X + MIDDLE_X + ADJUSTED_X;
            double circle_y = CIRCLE_COORDINATES_Y_LIST.get(circle_num) - START_Y + MIDDLE_Y + ADJUSTED_Y;

            canvas.drawCircle((float) circle_x, (float) circle_y, (float) SENTRY_GUN_RADIUS, SENTRY_GUN_COLOR);

        }

        double angle = Math.atan2(dot_location_y_2 - SHOOTER_COORDINATES_Y, dot_location_x_2 - SHOOTER_COORDINATES_X);

        double shooter_x_1 = SHOOTER_COORDINATES_X - START_X + MIDDLE_X + ADJUSTED_X;
        double shooter_y_1 = SHOOTER_COORDINATES_Y - START_Y + MIDDLE_Y + ADJUSTED_Y;


        double shooter_x_2 = shooter_x_1 + Math.cos(angle) * SHOOTER_LENGTH;
        double shooter_y_2 = shooter_y_1 + Math.sin(angle) * SHOOTER_LENGTH;

        canvas.drawLine((float) shooter_x_1, (float) shooter_y_1, (float) shooter_x_2, (float) shooter_y_2, SHOOTER_COLOR);

        if (MANAGE_OBJECTS_OBJECT != null)
            manageSentryGunLaser(dot_location_x, dot_location_y, instantaneous_movement_x, instantaneous_movement_y, dot_location_x_2, dot_location_y_2);
        else if (OTHER_MANAGE_OBJECTS_OBJECT != null)
            manageOtherSentryGunLaser(dot_location_x, dot_location_y, instantaneous_movement_x, instantaneous_movement_y);


    }

    public void  manageSentryGunLaser(double dot_location_x, double dot_location_y, double instantaneous_movement_x, double instantaneous_movement_y, double dot_location_x_2, double dot_location_y_2) {

        double distance = Math.sqrt(Math.pow((dot_location_x_2 - SHOOTER_COORDINATES_X), 2) + Math.pow((dot_location_y_2 - SHOOTER_COORDINATES_Y), 2));

        if (distance <= HEIGHT) {

            SHOOT_SENTRY_GUN += 1;

            if (SHOOT_SENTRY_GUN % 20 == 0) {

                SentryGunLaser newSentryGun = new SentryGunLaser(canvas, this, MANAGE_OBJECTS_OBJECT, OTHER_MANAGE_OBJECTS_OBJECT);

                double angle = Math.atan2(dot_location_y_2 - SHOOTER_COORDINATES_Y, dot_location_x_2 - SHOOTER_COORDINATES_X);

                newSentryGun.createSentryGunLaser(SHOOTER_COORDINATES_X, SHOOTER_COORDINATES_Y, dot_location_x, dot_location_y, angle);
                SENTRY_GUN_LASER_LIST.add(newSentryGun);

                sendData.sendSentryGunLaser(SHOOTER_COORDINATES_X, SHOOTER_COORDINATES_Y, angle);
            }
        }

        double laser_list_size = SENTRY_GUN_LASER_LIST.size();

        for (int list_num = 0; list_num < laser_list_size; list_num++) {
            SENTRY_GUN_LASER_LIST.get(list_num).updateAndDrawSentryGunLaser(list_num, dot_location_x, dot_location_y, instantaneous_movement_x, instantaneous_movement_y);
        }
            if (DESTROY_SENTRY_GUN_LASER == true)   {    //removes laser from list after running through for loop to prevent index out of range error
                DESTROY_SENTRY_GUN_LASER = false;
                SENTRY_GUN_LASER_LIST.remove(DESTROY_SENTRY_GUN_LASER_LIST_NUM);
            }
    }

    public void removeSentryGunLaserFromList(int list_num) {
        Log.w("SENTRY", "HERE");
        DESTROY_SENTRY_GUN_LASER = true;
        DESTROY_SENTRY_GUN_LASER_LIST_NUM = list_num;
    }


    public void  manageOtherSentryGunLaser(double dot_location_x, double dot_location_y, double instantaneous_movement_x, double instantaneous_movement_y) {

        if (GameConstants.getNewSentryGunLaser()) {
            GameConstants.setNewSentryGunLaser(false);

            SentryGunLaser newSentryGun = new SentryGunLaser(canvas, this, MANAGE_OBJECTS_OBJECT, OTHER_MANAGE_OBJECTS_OBJECT);//, this, null);

            double sentry_gun_x = GameConstants.getNewSentryGunLaserX();
            double sentry_gun_y = GameConstants.getNewSentryGunLaserY();
            double sentry_gun_angle = GameConstants.getNewSentryGunLaserAngle();

            newSentryGun.createSentryGunLaser(sentry_gun_x, sentry_gun_y, dot_location_x, dot_location_y, sentry_gun_angle);
            newSentryGun.LASER_COLOR.setColor(Color.rgb(0, 0, 0));
            SENTRY_GUN_LASER_LIST.add(newSentryGun);
        }

        double laser_list_size = SENTRY_GUN_LASER_LIST.size();

        for (int list_num = 0; list_num < laser_list_size; list_num++) {
            SENTRY_GUN_LASER_LIST.get(list_num).updateAndDrawSentryGunLaser(list_num, dot_location_x, dot_location_y, instantaneous_movement_x, instantaneous_movement_y);
        }
        if (DESTROY_SENTRY_GUN_LASER == true)   {    //removes laser from list after running through for loop to prevent index out of range error
            DESTROY_SENTRY_GUN_LASER = false;
            SENTRY_GUN_LASER_LIST.remove(DESTROY_SENTRY_GUN_LASER_LIST_NUM);
        }
    }

    public void removeOtherSentryGunLaserFromList(int list_num) {
        DESTROY_SENTRY_GUN_LASER = true;
        DESTROY_SENTRY_GUN_LASER_LIST_NUM = list_num;
    }
}
