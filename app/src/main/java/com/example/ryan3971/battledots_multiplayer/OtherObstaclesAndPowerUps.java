package com.example.ryan3971.battledots_multiplayer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ryan3971 on 2/4/2016.
 */
public class OtherObstaclesAndPowerUps {

    double LEFT;
    double RIGHT;
    double TOP;
    double BOTTOM;

    double WIDTH;
    double HEIGHT;

    double RADIUS;
    double DIAMETER;

    double MIDDLE_X;
    double MIDDLE_Y;

    double DOT_RADIUS;
    
    GameConstants gameConstants;
    Random random;

    Lives lives;
    //PowerUp_Images powerUp_Images;

    sendData sendData;
    Constants CONSTANTS;

    public OtherObstaclesAndPowerUps() {

        gameConstants = new GameConstants();
        HEIGHT = gameConstants.getHeight();
        WIDTH = gameConstants.getWidth();
        MIDDLE_Y = gameConstants.MIDDLE_Y;
        MIDDLE_X = gameConstants.MIDDLE_X;
        DOT_RADIUS = gameConstants.DOT_RADIUS;

        RADIUS = gameConstants.OBSTACLE_AND_POWERUPS_RADIUS;
        DIAMETER = gameConstants.OBSTACLE_AND_POWERUPS_DIAMETER;

        TOP = gameConstants.TOP;
        BOTTOM = gameConstants.BOTTOM;
        RIGHT = gameConstants.RIGHT;
        LEFT = gameConstants.LEFT;
        random = new Random();

        //     lives = new Lives();
        //   self.PowerUp_Images = PowerUpVariables.PowerUp_Images


        sendData = new sendData();
        CONSTANTS = new Constants();

    }

    public void updateAndDrawObstacles(Canvas canvas, double obstacles_movement_y, double obstacles_movement_x) {

        ArrayList<Double> obstacles_x_list = GameConstants.getObstacleListX();
        ArrayList<Double> obstacles_y_list = GameConstants.getObstacleListY();

        double list_size = obstacles_y_list.size();  //both the x and y list are the same, so doesn't matter which is used

        for (int list_num = 0; list_num < list_size; list_num++) {

            double obstacle_y = obstacles_y_list.get(list_num);
            double obstacle_x = obstacles_x_list.get(list_num);

            obstacle_y = obstacle_y + obstacles_movement_y;
            obstacle_x = obstacle_x + obstacles_movement_x;

            Paint obstacles_color = new Paint();
            obstacles_color.setColor(Color.GREEN);

            canvas.drawCircle((float) obstacle_x, (float) obstacle_y, (float) RADIUS, obstacles_color);

        }
    }

    public void updateAndDrawPowerUps(Canvas canvas, double powerups_movement_y, double powerups_movement_x) {

        ArrayList<Double> powerup_x_list = GameConstants.getPowerUpListX();
        ArrayList<Double> powerup_y_list = GameConstants.getPowerUpListY();

        double list_size = powerup_y_list.size();  //both the x and y list are the same, so doesn't matter which is used

        for (int list_num = 0; list_num < list_size; list_num++) {

            double powerups_y = powerup_y_list.get(list_num);
            double powerups_x = powerup_x_list.get(list_num);

            powerups_y = powerups_y + powerups_movement_y;
            powerups_x = powerups_x + powerups_movement_x;

            Paint powerups_color = new Paint();
            powerups_color.setColor(Color.BLUE);

            canvas.drawCircle((float) powerups_x, (float) powerups_y, (float)RADIUS, powerups_color);

        }
    }

    public void checkPowerUpCollisions(double powerups_movement_y, double powerups_movement_x) {

        double dot_x = MIDDLE_X;
        double dot_y = MIDDLE_Y;

        ArrayList<Double> powerup_x_list = GameConstants.getPowerUpListX(); //in case one is removed while in for loop. prevent index out of range error
        ArrayList<Double> powerup_y_list = GameConstants.getPowerUpListY(); // code up a little also

        float dot_radius = (float)(DOT_RADIUS * PowerUpVariables.getDotSize());

        if (PowerUpVariables.getDotShield() == true) {
            dot_radius = (float)(dot_radius * 1.5);
        }

        double list_size = powerup_y_list.size();  //both the x and y list are the same, so doesn't matter which is used

        for (int list_num = 0; list_num < list_size; list_num++) {

            try {

                double powerups_y = powerup_y_list.get(list_num);
                double powerups_x = powerup_x_list.get(list_num);

                powerups_y = powerups_y + powerups_movement_y;
                powerups_x = powerups_x + powerups_movement_x;

                double distance = Math.sqrt(Math.pow((powerups_x - dot_x), 2) + Math.pow((powerups_y - dot_y), 2));

                if (distance <= RADIUS + dot_radius) {

                    powerup_y_list.remove(list_num);
                    powerup_x_list.remove(list_num);

                    GameConstants.setPowerUpListXandY(powerup_x_list, powerup_y_list);

                    ArrayList<Double> XAndYPowerupsList = new ArrayList<>();

                    int size = powerup_x_list.size();

                    for (int num = 0; num < size; num++) {
                        double x = powerup_x_list.get(num);
                        XAndYPowerupsList.add(x);
                        double y = powerup_y_list.get(num);
                        XAndYPowerupsList.add(y);

                    }
                    sendData.sendPowerupsXAndPowerupsY(XAndYPowerupsList);
                    sendData.sendNewPowerUp();
                    activatePowerUp();
                    return;
                }
            }catch (IndexOutOfBoundsException e)    {}
        }
    }

    public void checkObstacleCollisions(double obstacles_movement_y, double obstacles_movement_x) {

        double dot_x = MIDDLE_X;
        double dot_y = MIDDLE_Y;

        ArrayList<Double> obstacles_x_list = GameConstants.getObstacleListX(); //in case one is removed while in for loop. prevent index out of range error
        ArrayList<Double> obstacles_y_list = GameConstants.getObstacleListY();

        float dot_radius = (float)(DOT_RADIUS * PowerUpVariables.getDotSize());

        if (PowerUpVariables.getDotShield() == true) {
            dot_radius = (float)(dot_radius * 1.5);
        }

        double list_size = obstacles_y_list.size();  //both the x and y list are the same, so doesn't matter which is used

        for (int list_num = 0; list_num < list_size; list_num++) {     //prevents crashing for that first obstacle. not sure why.

            try {

                double obstacles_y = obstacles_y_list.get(list_num);
                double obstacles_x = obstacles_x_list.get(list_num);

                obstacles_y = obstacles_y + obstacles_movement_y;
                obstacles_x = obstacles_x + obstacles_movement_x;

                double distance = Math.sqrt(Math.pow((obstacles_x - dot_x), 2) + Math.pow((obstacles_y - dot_y), 2));

                if (distance <= RADIUS + dot_radius) {

                    obstacles_y_list.remove(list_num);
                    obstacles_x_list.remove(list_num);

                    GameConstants.setObstacleListXandY(obstacles_x_list, obstacles_y_list);


                    if (PowerUpVariables.getDotShield() == true) {
                        PowerUpVariables.setDotShield(false);
                    } else {
                        Lives.setLives(Lives.getLives() - 1);
                    }

                    ArrayList<Double> XAndYObstaclesList = new ArrayList<>();

                    int size = obstacles_x_list.size();

                    for (int num = 0; num < size; num++) {  // - 1 because of the adding of 2 array sizes

                        double x = obstacles_x_list.get(num);
                        XAndYObstaclesList.add(x);
                        double y = obstacles_y_list.get(num);
                        XAndYObstaclesList.add(y);
                    }
                    sendData.sendObstaclesXAndObstaclesY(XAndYObstaclesList);
                    sendData.sendNewObstacles();
                    return;

                }
            }catch (IndexOutOfBoundsException e)    {}
        }
    }

    public void activatePowerUp() {

        int power_up_num = random.nextInt(14);

        if (power_up_num == 0) {

            if (PowerUpVariables.getDotSizeSmall() == true) {
                PowerUpVariables.setDotSizeSmall(false);
            } else if (PowerUpVariables.getDotSizeLarge() == false) {
                PowerUpVariables.setDotSizeLarge(true);
            } else {
                activatePowerUp();
            }
        } else if (power_up_num == 1) {

            if (PowerUpVariables.getDotSizeLarge() == true) {
                PowerUpVariables.setDotSizeLarge(false);
            } else if (PowerUpVariables.getDotSizeSmall() == false) {
                PowerUpVariables.setDotSizeSmall(true);
            } else {
                activatePowerUp();
            }
        } else if (power_up_num == 2) {

            if (PowerUpVariables.getDotOppositeDirecton() == false) {
                PowerUpVariables.setDotOppositeDirection(true);
            } else {
                PowerUpVariables.setDotOppositeDirection(false);
            }
        } else if (power_up_num == 3) {

            if (PowerUpVariables.getDotSpeedSlow() == true) {
                PowerUpVariables.setDotSpeedSlow(false);
            } else if (PowerUpVariables.getDotSpeedFast() == false) {
                PowerUpVariables.setDotSpeedFast(true);
            } else {
                activatePowerUp();
            }
        } else if (power_up_num == 4) {

            if (PowerUpVariables.getDotSpeedFast() == true) {
                PowerUpVariables.setDotSpeedFast(false);
            } else if (PowerUpVariables.getDotSpeedSlow() == false) {
                PowerUpVariables.setDotSpeedSlow(true);
            } else {
                activatePowerUp();
            }
        } else if (power_up_num == 5) {

            if (PowerUpVariables.getDotInvisible() == true) {
                PowerUpVariables.setDotInvisible(false);
            } else {
                PowerUpVariables.setDotInvisible(true);
            }
        } else if (power_up_num == 6) {
            PowerUpVariables.setLaser(true);

        } else if (power_up_num == 7) {
            PowerUpVariables.setBall(true);

        } else if (power_up_num == 8) {

            if (PowerUpVariables.getDotShield() == false) {
                PowerUpVariables.setDotShield(true);
            } else {
                activatePowerUp();
            }
        } else if (power_up_num == 9) {
            PowerUpVariables.setMultiLaser(true);

        } else if (power_up_num == 10) {
            PowerUpVariables.setTargetBall(true);

        } else if (power_up_num == 11) {
            if (PowerUpVariables.getPowerWave() == false) {
                PowerUpVariables.setPowerWave(true);
            } else {
                activatePowerUp();
            }
        }else if (power_up_num == 12) {
            PowerUpVariables.setRapidFire(true);

        }else if (power_up_num == 13) {
            PowerUpVariables.setSentryGun(true);
        }
    }
}

