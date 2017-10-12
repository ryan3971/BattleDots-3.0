package com.example.ryan3971.battledots_multiplayer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ryan3971 on 2/4/2016.
 */
public class OtherManageObjects {

    double MIDDLE_X, MIDDLE_Y;

    ArrayList<shootLaser> LASER_LIST;
    boolean DESTROY_LASER;
    int DESTROY_LASER_LIST_NUM;

    ArrayList<BouncingBall> BALL_LIST;
    boolean DESTROY_BALL;
    int DESTROY_BALL_LIST_NUM;

    ArrayList<MultiLaser> MULTI_LASER_LIST;
    boolean DESTROY_MULTI_LASER;
    int DESTROY_MULTI_LASER_LIST_NUM;

    ArrayList<TargetBall> TARGET_BALL_LIST;
    boolean DESTROY_TARGET_BALL;
    int DESTROY_TARGET_BALL_LIST_NUM;

    PowerWave powerWave;
    boolean IS_POWER_WAVE;

    ArrayList<RapidFire> RAPID_FIRE_LIST;
    boolean DESTROY_RAPID_FIRE = false;
    int DESTROY_RAPID_FIRE_LIST_NUM = 0;

    ArrayList<SentryGun> SENTRY_GUN_LIST;

    GameConstants gameConstants;
    Random random;

    int MANAGE_OBJECTS_ID = 2;

    public OtherManageObjects()  {

        gameConstants = new GameConstants();
        MIDDLE_Y = gameConstants.MIDDLE_Y;
        MIDDLE_X = gameConstants.MIDDLE_X;

        LASER_LIST = new ArrayList<>();
        BALL_LIST = new ArrayList<>();
        MULTI_LASER_LIST = new ArrayList<>();
        TARGET_BALL_LIST = new ArrayList<>();
        RAPID_FIRE_LIST = new ArrayList<>();
        SENTRY_GUN_LIST = new ArrayList<>();

        random = new Random();
    }

    public void manageLaser(double dot_location_x, double dot_location_y, double instantaneous_movement_x, double instantaneous_movement_y, Canvas canvas)   {

        if (GameConstants.getNewLaser())    {
            Log.w("LASER", "CREATED");
            GameConstants.setNewLaser(false);

            double laser_x = GameConstants.getNewLaserX();
            double laser_y = GameConstants.getNewLaserY();
            double laser_angle = GameConstants.getNewLaserAngle();

            shootLaser newLaser = new shootLaser(canvas, null, this);
            newLaser.createLaser(laser_x, laser_y, dot_location_x, dot_location_y, laser_angle);
            newLaser.LASER_COLOR.setColor(Color.rgb(0, 0, 0));
            LASER_LIST.add(newLaser);
        }

        int laser_list_size = LASER_LIST.size();

        for (int list_num = 0; list_num < laser_list_size; list_num++) {
            LASER_LIST.get(list_num).updateAndDrawLaser(list_num, dot_location_x, dot_location_y, instantaneous_movement_x, instantaneous_movement_y);
        }
        if (DESTROY_LASER == true) {         //removes laser from list after running through for loop to prevent index out of range error
            DESTROY_LASER = false;
            LASER_LIST.remove(DESTROY_LASER_LIST_NUM);
        }
    }

    public void removeLaserFromList(int list_num) {

        DESTROY_LASER = true;
        DESTROY_LASER_LIST_NUM = list_num;
    }

    public void manageBall(double dot_location_x, double dot_location_y, double instantaneous_movement_x, double instantaneous_movement_y,  Canvas canvas) {

        if (GameConstants.getNewBall()) {
            GameConstants.setNewBall(false);

            double ball_x = GameConstants.getNewBallX();
            double ball_y = GameConstants.getNewBallY();
            double ball_angle = GameConstants.getNewBallAngle();

            BouncingBall newBall = new BouncingBall(canvas, null, this);
            newBall.createBall(ball_x, ball_y, dot_location_x, dot_location_y, ball_angle);
            newBall.BALL_COLOR.setColor(Color.rgb(200, 200, 100));
            BALL_LIST.add(newBall);

        }

        int ball_list_size = BALL_LIST.size();

        for (int list_num = 0; list_num < ball_list_size; list_num++) {
            BALL_LIST.get(list_num).updateAndDrawBall(dot_location_x, dot_location_y, list_num, instantaneous_movement_x, instantaneous_movement_y);

        }

        if (DESTROY_BALL == true) {      // removes laser from list after running through for loop to prevent index out of range error
            DESTROY_BALL = false;
            BALL_LIST.remove(DESTROY_BALL_LIST_NUM);
        }
    }

        public void removeBallFromList(int list_num) {
            DESTROY_BALL = true;
            DESTROY_BALL_LIST_NUM = list_num;
    }

    public void manageMultiLaser(double dot_location_x, double dot_location_y, double instantaneous_movement_x, double instantaneous_movement_y,  Canvas canvas) {

        if (GameConstants.getNewMultiLaser()) {
            GameConstants.setNewMultiLaser(false);

            ArrayList<Double> dot_location_x_list = GameConstants.getNewMultiLaserX();
            ArrayList<Double> dot_location_y_list = GameConstants.getNewMultiLaserY();
            ArrayList<Double> angle_list = GameConstants.getNewMultiLaserAngle();

            for (int num = 0; num < 15; num++) {

                double multi_laser_x = dot_location_x_list.get(num);
                double multi_laser_y = dot_location_y_list.get(num);
                double multi_laser_angle = angle_list.get(num);

                MultiLaser newMultiLaser = new MultiLaser(canvas, null, this);
                newMultiLaser.createMultiLaser(multi_laser_x, multi_laser_y, dot_location_x, dot_location_y, multi_laser_angle);
                newMultiLaser.LASER_COLOR.setColor(Color.rgb(0, 0, 0));
                MULTI_LASER_LIST.add(newMultiLaser);
            }
        }
        int laser_list_size = MULTI_LASER_LIST.size();

        for (int list_num = 0; list_num < laser_list_size; list_num++) {
            MULTI_LASER_LIST.get(list_num).updateAndDrawMultiLaser(list_num, dot_location_x, dot_location_y, instantaneous_movement_x, instantaneous_movement_y);
        }
        if (DESTROY_MULTI_LASER == true) {     //removes laser from list after running through for loop to prevent index out of range error
            DESTROY_MULTI_LASER = false;
            MULTI_LASER_LIST.remove(DESTROY_MULTI_LASER_LIST_NUM);

        }
    }

    public void removeMultiLaserFromList(int list_num) {
        DESTROY_MULTI_LASER = true;
        DESTROY_MULTI_LASER_LIST_NUM = list_num;
    }

    public void manageTargetBall(double dot_location_x, double dot_location_y, double instantaneous_movement_x, double instantaneous_movement_y, Canvas canvas) {

        if (GameConstants.getNewTargetBall()) {
            GameConstants.setNewTargetBall(false);

            double target_ball_x = GameConstants.getNewTargetBallX();
            double target_ball_y = GameConstants.getNewTargetBallY();

            TargetBall newTargetBall = new TargetBall(canvas, null, this);
            newTargetBall.createTargetBall(target_ball_x, target_ball_y, dot_location_x, dot_location_y, Constants.getOtherDotX(), Constants.getOtherDotY());
            newTargetBall.TARGET_BALL_COLOR.setColor(Color.rgb(150, 50, 150));
            TARGET_BALL_LIST.add(newTargetBall);

        }
        int target_ball_list_size = TARGET_BALL_LIST.size();

        for (int list_num = 0; list_num < target_ball_list_size; list_num++) {
            TARGET_BALL_LIST.get(list_num).updateAndDrawTargetBall(list_num, dot_location_x, dot_location_y, instantaneous_movement_x, instantaneous_movement_y);
        }

        if (DESTROY_TARGET_BALL == true) {      // removes laser from list after running through for loop to prevent index out of range error
            DESTROY_TARGET_BALL = false;
            TARGET_BALL_LIST.remove(DESTROY_TARGET_BALL_LIST_NUM);
        }
    }

    public void removeTargetBallFromList(int list_num) {
        DESTROY_TARGET_BALL = true;
        DESTROY_TARGET_BALL_LIST_NUM = list_num;
    }

    public void managePowerWave(double dot_location_x, double dot_location_y, double instantaneous_movement_x, double instantaneous_movement_y, Canvas canvas) {

        if (GameConstants.getNewPowerWave()) {
            GameConstants.setNewPowerWave(false);
            IS_POWER_WAVE = true;

            double power_wave_x = GameConstants.getNewPowerWaveX();
            double power_wave_y = GameConstants.getNewPowerWaveY();

            powerWave = new PowerWave(power_wave_x, power_wave_y, dot_location_x, dot_location_y, canvas, null, this);
            powerWave.WAVE_COLOR.setColor(Color.rgb(0, 200, 100));
        }
        if (IS_POWER_WAVE) {
            boolean wave = powerWave.drawWave(dot_location_x, dot_location_y, instantaneous_movement_x, instantaneous_movement_y);

            if (wave) {
                removePowerWave();
            }
        }
    }

        public void removePowerWave() {
            IS_POWER_WAVE = false;
        }

    public void manageRapidFire(double dot_location_x, double dot_location_y, double instantaneous_movement_x, double instantaneous_movement_y, Canvas canvas)   {

        if (GameConstants.getNewRapidFire()) {

            GameConstants.setNewRapidFire(false);

            double rapid_fire_x = GameConstants.getNewRapidFireX();
            double rapid_fire_y = GameConstants.getNewRapidFireY();
            double rapid_fire_angle = GameConstants.getNewRapidFireAngle();

            RapidFire newRapidFire = new RapidFire(canvas, null, this);
            newRapidFire.createRapidFire(rapid_fire_x, rapid_fire_y, dot_location_x, dot_location_y, rapid_fire_angle);
            newRapidFire.RAPID_FIRE_COLOR.setColor(Color.rgb(75, 125, 75));
            RAPID_FIRE_LIST.add(newRapidFire);
        }

        int rapid_fire_list_size = RAPID_FIRE_LIST.size();

        for (int list_num = 0; list_num < rapid_fire_list_size; list_num++) {
            RAPID_FIRE_LIST.get(list_num).updateAndDrawRapidFire(list_num, dot_location_x, dot_location_y, instantaneous_movement_x, instantaneous_movement_y);
        }
        if (DESTROY_RAPID_FIRE == true) {         //removes laser from list after running through for loop to prevent index out of range error
            DESTROY_RAPID_FIRE = false;
            RAPID_FIRE_LIST.remove(DESTROY_RAPID_FIRE_LIST_NUM);
        }
    }
    public void removeRapidFireFromList(int list_num) {

        DESTROY_RAPID_FIRE = true;
        DESTROY_RAPID_FIRE_LIST_NUM = list_num;
    }

    public void manageSentryGun(double dot_location_x, double dot_location_y, double instantaneous_movement_x, double instantaneous_movement_y, Canvas canvas) {

        if (GameConstants.getNewSentryGun()) {
            GameConstants.setNewSentryGun(false);

            double sentry_gun_x = GameConstants.getNewSentryGunX();
            double sentry_gun_y = GameConstants.getNewSentryGunY();

            SentryGun newSentryGun = new SentryGun(canvas, null, this);
            newSentryGun.createSentryGun(sentry_gun_x, sentry_gun_y, dot_location_x, dot_location_y);
            SENTRY_GUN_LIST.add(newSentryGun);

        }

        int sentry_gun_list_size = SENTRY_GUN_LIST.size();

        for (int list_num = 0; list_num < sentry_gun_list_size; list_num++) {
            SENTRY_GUN_LIST.get(list_num).updateAndDrawSentryGun(list_num, dot_location_x, dot_location_y, instantaneous_movement_x, instantaneous_movement_y, dot_location_x, dot_location_y);
        }
    }
}
