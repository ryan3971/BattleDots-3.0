package com.example.ryan3971.battledots_multiplayer;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by ryan3971 on 2/4/2016.
 */
public class GameConstants {

    double WIDTH;
    double HEIGHT;
    double DOT_RADIUS;

    double OBSTACLE_AND_POWERUPS_RADIUS;
    double OBSTACLE_AND_POWERUPS_DIAMETER;

    double MIDDLE_X;
    double MIDDLE_Y;

    double TOP;
    double BOTTOM;
    double LEFT;
    double RIGHT;

    static double PLAYER_TAG;

    static ArrayList<Double> OBSTACLE_LIST_X = new ArrayList<Double>();
    static ArrayList<Double> OBSTACLE_LIST_Y = new ArrayList<Double>();

    static ArrayList<Double> POWERUPS_LIST_X = new ArrayList<Double>();
    static ArrayList<Double> POWERUPS_LIST_Y = new ArrayList<Double>();

    static boolean NEW_POWERUP = false, NEW_OBSTACLE = false;

    static double DOT_2_SIZE = 1;

    static boolean DOT_2_INVISIBLE = false;

    static boolean DOT_2_SHIELD = false;

    static boolean NEW_LASER = false;
    static double NEW_LASER_X = 0;
    static double NEW_LASER_Y = 0;
    static double NEW_LASER_ANGLE = 0;


    static boolean NEW_BALL = false;
    static double NEW_BALL_X = 0;
    static double NEW_BALL_Y = 0;
    static double NEW_BALL_ANGLE = 0;

    static boolean NEW_MULTI_LASER = false;
    static ArrayList<Double> NEW_MULTI_LASER_X = new ArrayList<>();
    static ArrayList<Double> NEW_MULTI_LASER_Y = new ArrayList<>();
    static ArrayList<Double> NEW_MULTI_LASER_ANGLE = new ArrayList<>();

    static boolean NEW_TARGET_BALL = false;
    static double NEW_TARGET_BALL_X = 0;
    static double NEW_TARGET_BALL_Y = 0;


    static boolean NEW_POWER_WAVE = false;
    static double NEW_POWER_WAVE_X = 0;
    static double NEW_POWER_WAVE_Y = 0;


    static boolean NEW_RAPID_FIRE = false;
    static double NEW_RAPID_FIRE_X = 0;
    static double NEW_RAPID_FIRE_Y = 0;
    static double NEW_RAPID_FIRE_ANGLE = 0;


    static boolean NEW_SENTRY_GUN = false;
    static double NEW_SENTRY_GUN_X = 0;
    static double NEW_SENTRY_GUN_Y = 0;


    static boolean NEW_SENTRY_GUN_LASER = false;
    static double NEW_SENTRY_GUN_LASER_X = 0;
    static double NEW_SENTRY_GUN_LASER_Y = 0;
    static double NEW_SENTRY_GUN_LASER_ANGLE = 0;

    static boolean FIRE_TRIPLE_BALLS = false;
    static boolean DO_FIRE_TRIPLE_BALLS = false;
    static double TRIPLE_BALLS_FIRE_ANGLE;

    static boolean FIRE_OBSTACLE_DROP = false;
    static boolean DO_FIRE_OBSTACLE_DROP = false;


    public GameConstants()      {

        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics(); //height and width may be reversed//checked and is fine
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        setHeight(height);
        setWidth(width);

        DOT_RADIUS = getWidth() * 0.025;

        OBSTACLE_AND_POWERUPS_RADIUS = getWidth() * 0.025;
        OBSTACLE_AND_POWERUPS_DIAMETER = OBSTACLE_AND_POWERUPS_RADIUS * 2;

        MIDDLE_X = getWidth() * 0.50;
        MIDDLE_Y = getHeight() * 0.50;

        TOP = 0;
        BOTTOM = getHeight() * 3;
        RIGHT = getWidth() * 3;
        LEFT = 0;

    }

    public void setHeight(double newHeight)     {
        HEIGHT = newHeight;
    }

    public void setWidth(double newWidth)      {
        WIDTH = newWidth;
    }

    public double getHeight()     {
        return HEIGHT;
    }

    public double getWidth()      {
        return WIDTH;
    }

    public static void setObstacleListXandY (ArrayList<Double> X, ArrayList<Double> Y)  {
        OBSTACLE_LIST_X = X;
        OBSTACLE_LIST_Y = Y;
        Log.w("END", "X   -  " + X.size());

    }

    public static ArrayList<Double> getObstacleListX ()  {
        return OBSTACLE_LIST_X;
    }

    public static ArrayList<Double> getObstacleListY ()  {
        return OBSTACLE_LIST_Y;
    }

    public static void setPowerUpListXandY (ArrayList<Double> X, ArrayList<Double> Y)  {
        POWERUPS_LIST_X = X;
        POWERUPS_LIST_Y = Y;
    }

    public static ArrayList<Double> getPowerUpListX ()  {
        return POWERUPS_LIST_X;
    }

    public static ArrayList<Double> getPowerUpListY ()  {
        return POWERUPS_LIST_Y;
    }

    public static void setNewPowerUp(boolean newPowerUp) {
        NEW_POWERUP = newPowerUp;
    }

    public static boolean getNewPowerUp() {
        return NEW_POWERUP;
    }
    public static void setNewObstacles(boolean newObstacles) {
        NEW_OBSTACLE = newObstacles;
    }
    public static boolean getNewObstacles() {
        return NEW_OBSTACLE;
    }

    public static void setDot2Size(double size)    {
        DOT_2_SIZE = size;
    }

    public static double getDot2Size()    {
        return DOT_2_SIZE;
    }

    public static void setDot2Invisible(boolean invisible)    {
        DOT_2_INVISIBLE = invisible;
    }

    public static boolean getDot2Invisible()    {
        return DOT_2_INVISIBLE;
    }

    public static void setDot2Shield(boolean shield)    {
        DOT_2_SHIELD = shield;
    }

    public static boolean getDot2Shield()    {
        return DOT_2_SHIELD;
    }

    public static void setNewLaser(boolean newLaser)  {   NEW_LASER = newLaser; }
    public static boolean getNewLaser()  { return NEW_LASER; }

    public static void setNewLaserX(double x)  {   NEW_LASER_X = x; }
    public static double getNewLaserX()  { return NEW_LASER_X; }

    public static void setNewLaserY(double y)  {
        NEW_LASER_Y = y;
    }
    public static double getNewLaserY()  { return NEW_LASER_Y; }

    public static void setNewLaserAngle(double angle)  {
        NEW_LASER_ANGLE = angle;
    }
    public static double getNewLaserAngle()  { return NEW_LASER_ANGLE; }


    public static void setNewBall(boolean newBall)  {   NEW_BALL = newBall; }
    public static boolean getNewBall()  { return NEW_BALL; }

    public static void setNewBallX(double x)  {   NEW_BALL_X = x; }
    public static double getNewBallX()  { return NEW_BALL_X; }

    public static void setNewBallY(double y)  {NEW_BALL_Y = y;}
    public static double getNewBallY()  { return NEW_BALL_Y; }

    public static void setNewBallAngle(double angle)  {
        NEW_BALL_ANGLE = angle;
    }
    public static double getNewBallAngle()  { return NEW_BALL_ANGLE; }


    public static void setNewMultiLaser(boolean newMultiLaser)  {   NEW_MULTI_LASER = newMultiLaser; }
    public static boolean getNewMultiLaser()  { return NEW_MULTI_LASER; }


    public static void setNewMultiLaserX(ArrayList<Double> x_list)  {   NEW_MULTI_LASER_X = x_list; }
    public static ArrayList<Double> getNewMultiLaserX()  { return NEW_MULTI_LASER_X; }

    public static void setNewMultiLaserY(ArrayList<Double> y_list)  {NEW_MULTI_LASER_Y = y_list;}
    public static ArrayList<Double> getNewMultiLaserY()  { return NEW_MULTI_LASER_Y; }

    public static void setNewMultiLaserAngle(ArrayList<Double> angle_list)  {
        NEW_MULTI_LASER_ANGLE = angle_list;
    }
    public static ArrayList<Double> getNewMultiLaserAngle()  { return NEW_MULTI_LASER_ANGLE; }



    public static void setNewTargetBall(boolean newBall)  {   NEW_TARGET_BALL = newBall; }
    public static boolean getNewTargetBall()  { return NEW_TARGET_BALL; }

    public static void setNewTargetBallX(double x)  {   NEW_TARGET_BALL_X = x; }
    public static double getNewTargetBallX()  { return NEW_TARGET_BALL_X; }

    public static void setNewTargetBallY(double y)  {NEW_TARGET_BALL_Y = y;}
    public static double getNewTargetBallY()  { return NEW_TARGET_BALL_Y; }


    public static void setNewPowerWave(boolean newPowerWave)  {   NEW_POWER_WAVE = newPowerWave; }
    public static boolean getNewPowerWave()  { return NEW_POWER_WAVE; }

    public static void setNewPowerWaveX(double x)  {   NEW_POWER_WAVE_X = x; }
    public static double getNewPowerWaveX()  { return NEW_POWER_WAVE_X; }

    public static void setNewPowerWaveY(double y)  {NEW_POWER_WAVE_Y = y;}
    public static double getNewPowerWaveY()  { return NEW_POWER_WAVE_Y; }


    public static void setNewRapidFire(boolean newRapidFire)  {   NEW_RAPID_FIRE = newRapidFire; }
    public static boolean getNewRapidFire()  { return NEW_RAPID_FIRE; }

    public static void setNewRapidFireX(double x)  {   NEW_RAPID_FIRE_X = x; }
    public static double getNewRapidFireX()  { return NEW_RAPID_FIRE_X; }

    public static void setNewRapidFireY(double y)  {
        NEW_RAPID_FIRE_Y = y;
    }
    public static double getNewRapidFireY()  { return NEW_RAPID_FIRE_Y; }

    public static void setNewRapidFireAngle(double angle)  {
        NEW_RAPID_FIRE_ANGLE = angle;
    }
    public static double getNewRapidFireAngle()  { return NEW_RAPID_FIRE_ANGLE; }


    public static void setNewSentryGun(boolean newSentryGun)  {   NEW_SENTRY_GUN = newSentryGun; }
    public static boolean getNewSentryGun()  { return NEW_SENTRY_GUN; }

    public static void setNewSentryGunX(double x)  {   NEW_SENTRY_GUN_X = x; }
    public static double getNewSentryGunX()  { return NEW_SENTRY_GUN_X; }

    public static void setNewSentryGunY(double y)  {
        NEW_SENTRY_GUN_Y = y;
    }
    public static double getNewSentryGunY()  { return NEW_SENTRY_GUN_Y; }


    public static void setNewSentryGunLaser(boolean newSentryGunLaser)  {   NEW_SENTRY_GUN_LASER = newSentryGunLaser; }
    public static boolean getNewSentryGunLaser()  { return NEW_SENTRY_GUN_LASER; }

    public static void setNewSentryGunLaserX(double x)  {   NEW_SENTRY_GUN_LASER_X = x; }
    public static double getNewSentryGunLaserX()  { return NEW_SENTRY_GUN_LASER_X; }

    public static void setNewSentryGunLaserY(double y)  {
        NEW_SENTRY_GUN_LASER_Y = y;
    }
    public static double getNewSentryGunLaserY()  { return NEW_SENTRY_GUN_LASER_Y; }

    public static void setNewSentryGunLaserAngle(double angle)  {
        NEW_SENTRY_GUN_LASER_ANGLE = angle;
    }
    public static double getNewSentryGunLaserAngle()  { return NEW_SENTRY_GUN_LASER_ANGLE; }
}
