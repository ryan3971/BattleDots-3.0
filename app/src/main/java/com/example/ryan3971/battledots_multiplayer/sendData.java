package com.example.ryan3971.battledots_multiplayer;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ryan3971 on 7/5/2016.
 */
public class sendData {

    MainActivity mainActivity;
    Constants CONSTANTS;


    public sendData()   {

        mainActivity = new MainActivity();
        CONSTANTS = new Constants();

    }

    public void sendXandY(double x, double y)     {

        ArrayList<Double> XandYList = new ArrayList<Double>();

        float x1 = convertPixelsToDp((float) x);
        float y1 = convertPixelsToDp((float)y);

        XandYList.add((double)x1);
        XandYList.add((double)y1);

        mainActivity.DataToByteAndSend(CONSTANTS.OTHER_DOT_X_AND_Y_CONSTANT, XandYList);

    }

    public void sendObstaclesXAndObstaclesY(ArrayList<Double> XAndYList)     {

        ArrayList<Double> XAndYListConverted = new ArrayList<>();
        double size = XAndYList.size();

        for (int i = 0; i < size; i++) {

            double data = XAndYList.get(i);

            float dataConverted = convertPixelsToDp((float)data);

            XAndYListConverted.add((double)dataConverted);

        }
        MainActivity.DataToByteAndSend(CONSTANTS.OBSTACLES_CONSTANT, XAndYListConverted);
    }

    public void sendPowerupsXAndPowerupsY(ArrayList<Double> XAndYList)     {

        ArrayList<Double> XAndYListConverted = new ArrayList<>();
        double size = XAndYList.size();

        for (int i = 0; i < size; i++) {

            double data = XAndYList.get(i);

            float dataConverted = convertPixelsToDp((float)data);

            XAndYListConverted.add((double)dataConverted);

        }
        MainActivity.DataToByteAndSend(CONSTANTS.POWERUPS_CONSTANT, XAndYListConverted);
    }

    public void sendNewPowerUp()    {

        MainActivity.DataToByteAndSend(CONSTANTS.NEW_POWERUPS_CONSTANT, null);

    }

    public void sendNewObstacles()    {

        MainActivity.DataToByteAndSend(CONSTANTS.NEW_OBSTACLES_CONSTANT, null);

    }

    public void sendSize(double dot_size)   {

        ArrayList<Double> size_list = new ArrayList<>();
        size_list.add(dot_size);
        MainActivity.DataToByteAndSend(CONSTANTS.DOT_SIZE, size_list);

    }

    public void sendInvisible(double visible_or_invisible)   {

        ArrayList<Double> invisible_list = new ArrayList<>();
        invisible_list.add(visible_or_invisible);
        MainActivity.DataToByteAndSend(CONSTANTS.DOT_INVISIBLE, invisible_list);

    }

    public void sendShield(double isShield)   {

        ArrayList<Double> shield_list = new ArrayList<>();
        shield_list.add(isShield);
        MainActivity.DataToByteAndSend(CONSTANTS.DOT_SHIELD, shield_list);

    }

    public void sendLaser(double dot_x, double dot_y, double angle)  {
        ArrayList<Double> laser_list = new ArrayList<>();

        double dot_x_converted = convertPixelsToDp((float)dot_x);
        double dot_y_converted = convertPixelsToDp((float)dot_y);

        laser_list.add(dot_x_converted);
        laser_list.add(dot_y_converted);
        laser_list.add(angle);

        MainActivity.DataToByteAndSend(CONSTANTS.LASER, laser_list);

    }

    public void sendBall(double dot_x, double dot_y, double angle)  {
        ArrayList<Double> ball_list = new ArrayList<>();

        double dot_x_converted = convertPixelsToDp((float)dot_x);
        double dot_y_converted = convertPixelsToDp((float)dot_y);

        ball_list.add(dot_x_converted);
        ball_list.add(dot_y_converted);
        ball_list.add(angle);

        MainActivity.DataToByteAndSend(CONSTANTS.BALL, ball_list);

    }

    public void sendMultiLaser(ArrayList<Double> x_list, ArrayList<Double> y_list, ArrayList<Double> angle_list)     {

        ArrayList<Double> x_y_angle_list_converted = new ArrayList<>();

        double size = x_list.size();

        for (int i = 0; i < size; i++) {

            double data = x_list.get(i);
            float dataConverted = convertPixelsToDp((float) data);
            x_y_angle_list_converted.add((double)dataConverted);

            data = y_list.get(i);
            dataConverted = convertPixelsToDp((float) data);
            x_y_angle_list_converted.add((double)dataConverted);

            double angle = angle_list.get(i);
            x_y_angle_list_converted.add(angle);

        }
        MainActivity.DataToByteAndSend(CONSTANTS.MULTI_LASER, x_y_angle_list_converted);
    }

    public void sendTargetBall(double dot_x, double dot_y)  {
        ArrayList<Double> ball_list = new ArrayList<>();

        double dot_x_converted = convertPixelsToDp((float) dot_x);
        double dot_y_converted = convertPixelsToDp((float)dot_y);

        ball_list.add(dot_x_converted);
        ball_list.add(dot_y_converted);

        MainActivity.DataToByteAndSend(CONSTANTS.TARGET_BALL, ball_list);

    }

    public void sendPowerWave(double dot_x, double dot_y)  {
        ArrayList<Double> wave_list = new ArrayList<>();

        double dot_x_converted = convertPixelsToDp((float) dot_x);
        double dot_y_converted = convertPixelsToDp((float)dot_y);

        wave_list.add(dot_x_converted);
        wave_list.add(dot_y_converted);

        MainActivity.DataToByteAndSend(CONSTANTS.POWER_WAVE, wave_list);

    }

    public void sendRapidFire(double dot_x, double dot_y, double angle)  {
        ArrayList<Double> rapid_fire_list = new ArrayList<>();

        double dot_x_converted = convertPixelsToDp((float)dot_x);
        double dot_y_converted = convertPixelsToDp((float)dot_y);

        rapid_fire_list.add(dot_x_converted);
        rapid_fire_list.add(dot_y_converted);
        rapid_fire_list.add(angle);

        MainActivity.DataToByteAndSend(CONSTANTS.RAPID_FIRE, rapid_fire_list);

    }

    public void sendSentryGun(double dot_x, double dot_y)  {
        ArrayList<Double> sentry_gun_list = new ArrayList<>();

        double dot_x_converted = convertPixelsToDp((float)dot_x);
        double dot_y_converted = convertPixelsToDp((float)dot_y);

        sentry_gun_list.add(dot_x_converted);
        sentry_gun_list.add(dot_y_converted);

        MainActivity.DataToByteAndSend(CONSTANTS.SENTRY_GUN, sentry_gun_list);
    }

    public void sendSentryGunLaser(double dot_x, double dot_y, double angle)  {
        ArrayList<Double> laser_list = new ArrayList<>();

        double dot_x_converted = convertPixelsToDp((float)dot_x);
        double dot_y_converted = convertPixelsToDp((float)dot_y);

        laser_list.add(dot_x_converted);
        laser_list.add(dot_y_converted);
        laser_list.add(angle);

        MainActivity.DataToByteAndSend(CONSTANTS.SENTRY_GUN_LASER, laser_list);

    }

    public void sendEndGame()   {
        MainActivity.DataToByteAndSend(CONSTANTS.END_GAME, null);

    }

    public static float convertPixelsToDp(float px){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

}
