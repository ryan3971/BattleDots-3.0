package com.example.ryan3971.battledots_multiplayer;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ryan3971 on 2/4/2016.
 */
public class ManageObjects {

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

    ArrayList<RapidFire> RAPID_FIRE_LIST;
    int RAPID_FIRES_LEFT = 0;
    boolean DESTROY_RAPID_FIRE = false;
    int DESTROY_RAPID_FIRE_LIST_NUM = 0;

    ArrayList<SentryGun> SENTRY_GUN_LIST;

    TripleBalls TRIPLE_BALLS_ITEM;

    ObstacleDrop OBSTACLE_DROP_ITEM;


    GameConstants gameConstants;
    Random random;

    sendData sendData;

    public ManageObjects() {

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
        sendData = new sendData();
    }

    public double randomDeciaml(double min, double max) {
        double range = max - min;
        double scaled = random.nextDouble() * range;
        double shifted = scaled + min;
        return shifted; // == (rand.nextDouble() * (max-min)) + min;
    }

    public void manageLaser(double dot_location_x, double dot_location_y, double instantaneous_movement_x, double instantaneous_movement_y, Canvas canvas) {

        if (PowerUpVariables.getLaser() == true) {

            PowerUpVariables.setLaser(false);
            PowerUpVariables.showLaserImage(true);

            shootLaser newLaser = new shootLaser(canvas, this, null);
            double two_pi = 2 * Math.PI;
            double angle = randomDeciaml(0, two_pi);
            newLaser.createLaser(dot_location_x, dot_location_y, dot_location_x, dot_location_y, angle);
            LASER_LIST.add(newLaser);

            sendData.sendLaser(dot_location_x, dot_location_y, angle);

        }

        int laser_list_size = LASER_LIST.size();


        for (int list_num = 0; list_num < laser_list_size; list_num++) {
            LASER_LIST.get(list_num).updateAndDrawLaser(list_num, dot_location_x, dot_location_y, instantaneous_movement_x, instantaneous_movement_y);
        }
        if (DESTROY_LASER == true) {         //removes laser from list after running through for loop to prevent index out of range error
            DESTROY_LASER = false;
            LASER_LIST.remove(DESTROY_LASER_LIST_NUM);
        }
        if (LASER_LIST.size() == 0)
            PowerUpVariables.showLaserImage(false);

    }

    public void removeLaserFromList(int list_num) {

        DESTROY_LASER = true;
        DESTROY_LASER_LIST_NUM = list_num;
    }

    public void manageBall(double dot_location_x, double dot_location_y, double instantaneous_movement_x, double instantaneous_movement_y, Canvas canvas) {

        if (PowerUpVariables.getBall() == true) {
            PowerUpVariables.setBall(false);
            PowerUpVariables.showBallImage(true);

            BouncingBall newBall = new BouncingBall(canvas, this, null);
            double two_pi = 2 * Math.PI;
            double angle = randomDeciaml(0, two_pi);
            newBall.createBall(dot_location_x, dot_location_y, dot_location_x, dot_location_y, angle);
            BALL_LIST.add(newBall);

            sendData.sendBall(dot_location_x, dot_location_y, angle);

        }

        int ball_list_size = BALL_LIST.size();

        for (int list_num = 0; list_num < ball_list_size; list_num++) {
            BALL_LIST.get(list_num).updateAndDrawBall(dot_location_x, dot_location_y, list_num, instantaneous_movement_x, instantaneous_movement_y);
        }

        if (DESTROY_BALL == true) {      // removes laser from list after running through for loop to prevent index out of range error
            DESTROY_BALL = false;
            BALL_LIST.remove(DESTROY_BALL_LIST_NUM);
        }
        if (BALL_LIST.size() == 0)
            PowerUpVariables.showBallImage(false);
    }

    public void removeBallFromList(int list_num) {
        DESTROY_BALL = true;
        DESTROY_BALL_LIST_NUM = list_num;
    }

    public void manageMultiLaser(double dot_location_x, double dot_location_y, double instantaneous_movement_x, double instantaneous_movement_y, Canvas canvas) {

        if (PowerUpVariables.getMultiLaser() == true) {
            PowerUpVariables.setMultiLaser(false);
            PowerUpVariables.showMultiLaserImage(true);

            ArrayList<Double> dot_location_x_list = new ArrayList<>();
            ArrayList<Double> dot_location_y_list = new ArrayList<>();
            ArrayList<Double> angle_list = new ArrayList<>();

            for (int num = 0; num < 15; num++) {
                MultiLaser newMultiLaser = new MultiLaser(canvas, this, null);
                double two_pi = 2 * Math.PI;
                double angle = randomDeciaml(0, two_pi);
                newMultiLaser.createMultiLaser(dot_location_x, dot_location_y, dot_location_x, dot_location_y, angle);
                MULTI_LASER_LIST.add(newMultiLaser);

                dot_location_x_list.add(dot_location_x);
                dot_location_y_list.add(dot_location_y);
                angle_list.add(angle);
            }

            sendData.sendMultiLaser(dot_location_x_list, dot_location_y_list, angle_list);


        }
        int laser_list_size = MULTI_LASER_LIST.size();

        for (int list_num = 0; list_num < laser_list_size; list_num++) {
            MULTI_LASER_LIST.get(list_num).updateAndDrawMultiLaser(list_num, dot_location_x, dot_location_y, instantaneous_movement_x, instantaneous_movement_y);
        }
        if (DESTROY_MULTI_LASER == true) {     //removes laser from list after running through for loop to prevent index out of range error
            DESTROY_MULTI_LASER = false;
            MULTI_LASER_LIST.remove(DESTROY_MULTI_LASER_LIST_NUM);

            if (MULTI_LASER_LIST.size() == 0)
                PowerUpVariables.showMultiLaserImage(false);

        }
    }

    public void removeMultiLaserFromList(int list_num) {
        DESTROY_MULTI_LASER = true;
        DESTROY_MULTI_LASER_LIST_NUM = list_num;
    }

    public void manageTargetBall(double dot_location_x, double dot_location_y, double instantaneous_movement_x, double instantaneous_movement_y, Canvas canvas) {

        if (PowerUpVariables.getTargetBall() == true) {
            PowerUpVariables.setTargetBall(false);
            PowerUpVariables.showTargetBallImage(true);
            // might be issue with initial formation of Target ball for if it appears on dot or not + how other player sees it

            TargetBall newTargetBall = new TargetBall(canvas, this, null);
            newTargetBall.createTargetBall(dot_location_x, dot_location_y, dot_location_x, dot_location_y, Constants.getOtherDotX(), Constants.getOtherDotY());
            TARGET_BALL_LIST.add(newTargetBall);

            sendData.sendTargetBall(dot_location_x, dot_location_y);

        }
        int target_ball_list_size = TARGET_BALL_LIST.size();

        for (int list_num = 0; list_num < target_ball_list_size; list_num++) {
            TARGET_BALL_LIST.get(list_num).updateAndDrawTargetBall(list_num, Constants.getOtherDotX(), Constants.getOtherDotY(), instantaneous_movement_x, instantaneous_movement_y);
        }

        if (DESTROY_TARGET_BALL == true) {      // removes laser from list after running through for loop to prevent index out of range error
            DESTROY_TARGET_BALL = false;
            TARGET_BALL_LIST.remove(DESTROY_TARGET_BALL_LIST_NUM);

            if (TARGET_BALL_LIST.size() == 0)
                PowerUpVariables.showTargetBallImage(false);
        }
    }

    public void removeTargetBallFromList(int list_num) {
        DESTROY_TARGET_BALL = true;
        DESTROY_TARGET_BALL_LIST_NUM = list_num;
    }

    public void managePowerWave(double dot_location_x, double dot_location_y, double instantaneous_movement_x, double instantaneous_movement_y, Canvas canvas) {

        if (PowerUpVariables.getNewPowerWave() == true) {
            PowerUpVariables.setNewPowerWave(false);
            PowerUpVariables.showPowerWaveImage(true);

            powerWave = new PowerWave(dot_location_x, dot_location_y, dot_location_x, dot_location_y, canvas, this, null);

            sendData.sendPowerWave(dot_location_x, dot_location_y);

        }

        if (PowerUpVariables.getPowerWave() == true) {
            boolean wave = powerWave.drawWave(dot_location_x, dot_location_y, instantaneous_movement_x, instantaneous_movement_y);

            if (wave == true) {
                PowerUpVariables.setPowerWave(false);
            }
        }
    }

    public void removePowerWave() {
        PowerUpVariables.setPowerWave(false);
        PowerUpVariables.showPowerWaveImage(false);
    }


    public void manageRapidFire(double dot_location_x, double dot_location_y, double instantaneous_movement_x, double instantaneous_movement_y, Canvas canvas) {

        if (PowerUpVariables.getRapidFire()) {

            PowerUpVariables.setRapidFire(false);
            PowerUpVariables.showRapidFireImage(true);

            RAPID_FIRES_LEFT += 300;    // so there is pause between each shot
        }

        if (RAPID_FIRES_LEFT != 0) {
            RAPID_FIRES_LEFT -= 1;

            if (RAPID_FIRES_LEFT % 10 == 0) {

                RapidFire newRapidFire = new RapidFire(canvas, this, null);
                double angle = Math.atan2(TouchPad.getSmallPadY() - TouchPad.getLargePadY(), TouchPad.getSmallPadX() - TouchPad.getLargePadX());

                newRapidFire.createRapidFire(dot_location_x, dot_location_y, dot_location_x, dot_location_y, angle);
                RAPID_FIRE_LIST.add(newRapidFire);

                sendData.sendRapidFire(dot_location_x, dot_location_y, angle);

            }
        }

        int rapid_fire_list_size = RAPID_FIRE_LIST.size();


        for (int list_num = 0; list_num < rapid_fire_list_size; list_num++) {
            RAPID_FIRE_LIST.get(list_num).updateAndDrawRapidFire(list_num, dot_location_x, dot_location_y, instantaneous_movement_x, instantaneous_movement_y);
        }
        if (DESTROY_RAPID_FIRE == true) {         //removes laser from list after running through for loop to prevent index out of range error
            DESTROY_RAPID_FIRE = false;
            RAPID_FIRE_LIST.remove(DESTROY_RAPID_FIRE_LIST_NUM);
        }
        if (RAPID_FIRE_LIST.size() == 0)
            PowerUpVariables.showRapidFireImage(false);

    }

    public void removeRapidFireFromList(int list_num) {

        DESTROY_RAPID_FIRE = true;
        DESTROY_RAPID_FIRE_LIST_NUM = list_num;
    }

    public void manageSentryGun(double dot_location_x, double dot_location_y, double instantaneous_movement_x, double instantaneous_movement_y, Canvas canvas) {

        if (PowerUpVariables.getSentryGun()) {

            PowerUpVariables.setSentryGun(false);
            PowerUpVariables.showSentryGunImage(true);

            SentryGun newSentryGun = new SentryGun(canvas, this, null);
            newSentryGun.createSentryGun(dot_location_x, dot_location_y, dot_location_x, dot_location_y);
            SENTRY_GUN_LIST.add(newSentryGun);

            sendData.sendSentryGun(dot_location_x, dot_location_y);

        }

        int sentry_gun_list_size = SENTRY_GUN_LIST.size();

        for (int list_num = 0; list_num < sentry_gun_list_size; list_num++) {
            SENTRY_GUN_LIST.get(list_num).updateAndDrawSentryGun(list_num, dot_location_x, dot_location_y, instantaneous_movement_x, instantaneous_movement_y, Constants.getOtherDotX(), Constants.getOtherDotY());
        }
    }

    public void manageTripleBouncingBalls(double dot_location_x, double dot_location_y, double instantaneous_movement_x, double instantaneous_movement_y, TouchPad touchPad, Canvas canvas) {

        if (PowerUpVariables.getTripleBalls()) {
            PowerUpVariables.setTripleBalls(false);
            PowerUpVariables.showTripleBallImage(true);

            TRIPLE_BALLS_ITEM = new TripleBalls(canvas, this, null);
            TRIPLE_BALLS_ITEM.createCirclingBalls();
        }
        if (TRIPLE_BALLS_ITEM != null) {
            TRIPLE_BALLS_ITEM.circleTheBalls(dot_location_x, dot_location_y);
        }
    }

    public void tripleBallToBouncingBall(double ball_x, double ball_y, double dot_location_x, double dot_location_y, double angle, Canvas canvas)     {

        BouncingBall newBall = new BouncingBall(canvas, this, null);

        newBall.createBall(ball_x, ball_y, dot_location_x, dot_location_y, angle);
        PowerUpVariables.showBallImage(true);
        BALL_LIST.add(newBall);

     //   sendData.sendBall(dot_location_x, dot_location_y, angle);

    }

    public void destroyTripleBall() {
        TRIPLE_BALLS_ITEM = null;
        GameConstants.FIRE_TRIPLE_BALLS = false;
        PowerUpVariables.showTripleBallImage(false);
    }



    public void manageObstacleDrop(double dot_location_x, double dot_location_y, double instantaneous_movement_x, double instantaneous_movement_y, TouchPad touchPad, Canvas canvas) {

        if (PowerUpVariables.getObstacleDrop()) {
            PowerUpVariables.setObstacleDrop(false);
            PowerUpVariables.showObstacleDropImage(true);

            OBSTACLE_DROP_ITEM = new ObstacleDrop(canvas, this, null);
        }
        if (OBSTACLE_DROP_ITEM != null) {
            OBSTACLE_DROP_ITEM.followTheBall(dot_location_x, dot_location_y);
        }

    }

    public void obstacleDropToObstacle(double obstacle_x, double obstacle_y)     {

        ArrayList<Double> list_x = GameConstants.getObstacleListX();
        ArrayList<Double> list_y = GameConstants.getObstacleListX();

        list_x.add(obstacle_x);
        list_y.add(obstacle_x);

        GameConstants.setObstacleListXandY(list_x, list_y);

        // sendData.sendObstaclesXAndObstaclesY(XAndYObstaclesList); // when for statement is over, send remainder

    }

    public void destroyObstacleDrop() {
        OBSTACLE_DROP_ITEM = null;
        GameConstants.FIRE_OBSTACLE_DROP = false;
        PowerUpVariables.showObstacleDropImage(false);
    }

}
