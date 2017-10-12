package com.example.ryan3971.battledots_multiplayer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ryan3971 on 2/4/2016.
 */
public class ObstaclesAndPowerUps {

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

    public ObstaclesAndPowerUps(Context context) {

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

        //   self.PowerUp_Images = PowerUpVariables.PowerUp_Images


        sendData = new sendData();
        CONSTANTS = new Constants();

    }

    public void createObstaclesAndPowerUps(double dot_x, double dot_y) {


        ArrayList<Double> OBSTACLE_LIST_X = new ArrayList<Double>();
        ArrayList<Double> OBSTACLE_LIST_Y = new ArrayList<Double>();

        ArrayList<Double> POWERUPS_LIST_X = new ArrayList<Double>();
        ArrayList<Double> POWERUPS_LIST_Y = new ArrayList<Double>();

        ArrayList<Double> XAndYObstaclesList = new ArrayList<>();
        ArrayList<Double> XAndYPowerupsList = new ArrayList<>();

        for (double y = TOP; y <= BOTTOM; y += DIAMETER) {
            for (double x = LEFT; x <= RIGHT; x += DIAMETER) {

                int num = random.nextInt(50);

                if (num == 0) {
                    double obstacle_y = y + RADIUS;
                    double obstacle_x = x + RADIUS;

                    double distance = Math.sqrt(Math.pow((obstacle_x - dot_x), 2) + Math.pow((obstacle_y - dot_y), 2));

                    if (distance >= RADIUS + (DOT_RADIUS * 2))     //ensure it doesn't appear on the dot

                        OBSTACLE_LIST_X.add(obstacle_x);
                    OBSTACLE_LIST_Y.add(obstacle_y);

                    XAndYObstaclesList.add(obstacle_x);
                    XAndYObstaclesList.add(obstacle_y);

                } else if (num == 1) {

                    double powerup_y = y + RADIUS;
                    double powerup_x = x + RADIUS;

                    double distance = Math.sqrt(Math.pow((powerup_x - dot_x), 2) + Math.pow((powerup_y - dot_y), 2));

                    if (distance >= RADIUS + (DOT_RADIUS * 2))     //ensure it doesn't appear on the dot

                        POWERUPS_LIST_X.add(powerup_x);
                    POWERUPS_LIST_Y.add(powerup_y);

                    XAndYPowerupsList.add(powerup_x);
                    XAndYPowerupsList.add(powerup_y);
                }
            }
        }

        GameConstants.setObstacleListXandY(OBSTACLE_LIST_X, OBSTACLE_LIST_Y);
        GameConstants.setPowerUpListXandY(POWERUPS_LIST_X, POWERUPS_LIST_Y);

        // sendData.sendObstaclesXAndObstaclesY(XAndYObstaclesList); // when for statement is over, send remainder
        // sendData.sendPowerupsXAndPowerupsY(XAndYPowerupsList);    // when for statement is over, send remainder

    }

    public void updateAndDrawObstacles(Canvas canvas, double obstacles_movement_y, double obstacles_movement_x, double dot_coordinates_x, double dot_coordinates_y) {

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

            canvas.drawCircle((float) obstacle_x, (float) obstacle_y, (float)RADIUS, obstacles_color);

        }

    //     if (GameConstants.getObstacleListX().size() < 30) {
    //         createNewObstacle(dot_coordinates_x, dot_coordinates_y, CONSTANTS.OTHER_DOT_X, CONSTANTS.OTHER_DOT_Y);
    //     }
    }

    public void updateAndDrawPowerUps(Canvas canvas, double powerups_movement_y, double powerups_movement_x, double dot_coordinates_x, double dot_coordinates_y) {

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

            canvas.drawCircle((float) powerups_x, (float) powerups_y, (float) RADIUS, powerups_color);

        }

    //     if (GameConstants.getPowerUpListX().size() < 30) {
    //         createNewPowerUps(dot_coordinates_x, dot_coordinates_y, CONSTANTS.OTHER_DOT_X, CONSTANTS.OTHER_DOT_Y);
    //     }
    }

    public void checkPowerUpCollisions(double powerups_movement_y, double powerups_movement_x, double dot_coordinates_x, double dot_coordinates_y) {

        double dot_x = MIDDLE_X;
        double dot_y = MIDDLE_Y;

        ArrayList<Double> powerup_x_list = GameConstants.getPowerUpListX(); //in case one is removed while in for loop. prevent index out of range error
        ArrayList<Double> powerup_y_list = GameConstants.getPowerUpListY(); // clean code up a little also

        float dot_radius = (float)(DOT_RADIUS * PowerUpVariables.getDotSize());

        if (PowerUpVariables.getDotShield() == true) {
            dot_radius = (float)(dot_radius * 1.5);
        }

        double list_size = powerup_y_list.size();  //both the x and y list are the same, so doesn't matter which is used

        for (int list_num = 0; list_num < list_size; list_num++) {


            double powerups_y = powerup_y_list.get(list_num);
            double powerups_x = powerup_x_list.get(list_num);

            powerups_y = powerups_y + powerups_movement_y;
            powerups_x = powerups_x + powerups_movement_x;

            double distance = Math.sqrt(Math.pow((powerups_x - dot_x), 2) + Math.pow((powerups_y - dot_y), 2));

            if (distance <= RADIUS + dot_radius) {

                powerup_y_list.remove(list_num);
                powerup_x_list.remove(list_num);

                GameConstants.setPowerUpListXandY(powerup_x_list, powerup_y_list); //update player 2's obstacles

                ArrayList<Double> XAndYPowerupsList = new ArrayList<>();

                int size = powerup_x_list.size();

                for (int num = 0; num < size; num++) {
                    double x = powerup_x_list.get(num);
                    XAndYPowerupsList.add(x);
                    double y = powerup_y_list.get(num);
                    XAndYPowerupsList.add(y);

                }
                // sendData.sendPowerupsXAndPowerupsY(XAndYPowerupsList);

                createNewPowerUps(dot_coordinates_x, dot_coordinates_y, CONSTANTS.OTHER_DOT_X, CONSTANTS.OTHER_DOT_Y);
                activatePowerUp();
                return;
            }
        }
        if (GameConstants.getNewPowerUp()) {
            GameConstants.setNewPowerUp(false);
            createNewPowerUps(dot_coordinates_x, dot_coordinates_y, CONSTANTS.OTHER_DOT_X, CONSTANTS.OTHER_DOT_Y);
        }
    }

    public void checkObstacleCollisions(double obstacles_movement_y, double obstacles_movement_x, double dot_coordinates_x, double dot_coordinates_y) {

        double dot_x = MIDDLE_X;
        double dot_y = MIDDLE_Y;

        ArrayList<Double> obstacles_x_list = GameConstants.getObstacleListX(); //in case one is removed while in for loop. prevent index out of range error
        ArrayList<Double> obstacles_y_list =  GameConstants.getObstacleListY();

        float dot_radius = (float)(DOT_RADIUS * PowerUpVariables.getDotSize());

        if (PowerUpVariables.getDotShield() == true) {
            dot_radius = (float)(dot_radius * 1.5);
        }

        double list_size = obstacles_y_list.size();  //both the x and y list are the same, so doesn't matter which is used

        for (int list_num = 0; list_num < list_size; list_num++) {     //prevents crashing for that first obstacle. not sure why.

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
                }else {
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
                // sendData.sendObstaclesXAndObstaclesY(XAndYObstaclesList);

                createNewObstacle(dot_coordinates_x, dot_coordinates_y, CONSTANTS.OTHER_DOT_X, CONSTANTS.OTHER_DOT_Y);
                return;
            }

        }
        if (GameConstants.getNewObstacles()) {
            GameConstants.setNewObstacles(false);
            createNewObstacle(dot_coordinates_x, dot_coordinates_y, CONSTANTS.OTHER_DOT_X, CONSTANTS.OTHER_DOT_Y); //fic call for x and y
        }
    }

    public void createNewPowerUps(double dot_x, double dot_y,  double dot_x_2, double dot_y_2) {

        //small chance that powerups will appear on obstacles

        ArrayList<Double> powerup_x_list = GameConstants.getPowerUpListX();
        ArrayList<Double> powerup_y_list = GameConstants.getPowerUpListY();

        ArrayList<Double> obstacles_x_list = GameConstants.getObstacleListX();
        ArrayList<Double> obstacles_y_list =  GameConstants.getObstacleListY();

        double area_x = RIGHT / DIAMETER;
        double area_y = BOTTOM / DIAMETER;

        double new_powerup_x = random.nextInt((int) area_x);
        double new_powerup_y = random.nextInt((int) area_y);

        new_powerup_x = new_powerup_x * DIAMETER;
        new_powerup_y = new_powerup_y * DIAMETER;

        new_powerup_x = new_powerup_x + RADIUS;
        new_powerup_y = new_powerup_y + RADIUS;

        double list_size = powerup_y_list.size();

        for (int list_num = 0; list_num < list_size; list_num++) {

            double powerups_y = powerup_y_list.get(list_num);
            double powerups_x = powerup_x_list.get(list_num);

            if (powerups_x == new_powerup_x && powerups_y == new_powerup_y) {
                createNewPowerUps(dot_x, dot_y, dot_x_2, dot_y_2);
                return;
            }
        }

        list_size = obstacles_y_list.size();

        for (int list_num = 0; list_num < list_size; list_num++) {

            double obstacle_y = obstacles_y_list.get(list_num);
            double obstacle_x = obstacles_x_list.get(list_num);

            if (obstacle_x == new_powerup_x && obstacle_y == new_powerup_y) {
                createNewPowerUps(dot_x, dot_y, dot_x_2, dot_y_2);
                return;
            }
        }


        float dot_radius = (float)(DOT_RADIUS * PowerUpVariables.getDotSize());

        if (PowerUpVariables.getDotShield() == true) {
            dot_radius = (float)(dot_radius * 1.5);
        }

        double distance = Math.sqrt(Math.pow((new_powerup_x - dot_x), 2) + Math.pow((new_powerup_y - dot_y), 2));

        if (distance <= RADIUS + (dot_radius * 2)) { //ensure it doesnt appear on the dot
            createNewPowerUps(dot_x, dot_y, dot_x_2, dot_y_2);
            return;
        }
        distance = Math.sqrt(Math.pow((new_powerup_x - dot_x_2), 2) + Math.pow((new_powerup_y - dot_y_2), 2));

        if (distance <= RADIUS + (dot_radius * 2)) {//ensure it doesnt appear on the dot
            createNewPowerUps(dot_x, dot_y, dot_x_2, dot_y_2);
            return;
        }

        powerup_y_list.add(new_powerup_y);
        powerup_x_list.add(new_powerup_x);

        GameConstants.setPowerUpListXandY(powerup_x_list, powerup_y_list);
    }

    public void createNewObstacle(double dot_x, double dot_y,  double dot_x_2, double dot_y_2) {

        //small chance that powerups will appear on obstacles


        ArrayList<Double> powerup_x_list = GameConstants.getPowerUpListX();
        ArrayList<Double> powerup_y_list = GameConstants.getPowerUpListY();

        ArrayList<Double> obstacles_x_list = GameConstants.getObstacleListX();
        ArrayList<Double> obstacles_y_list =  GameConstants.getObstacleListY();

        double area_x = RIGHT / DIAMETER;
        double area_y = BOTTOM / DIAMETER;

        double new_obstacle_x = random.nextInt((int) area_x);
        double new_obstacle_y = random.nextInt((int) area_y);

        new_obstacle_x = new_obstacle_x * DIAMETER;
        new_obstacle_y = new_obstacle_y * DIAMETER;

        new_obstacle_x = new_obstacle_x + RADIUS;
        new_obstacle_y = new_obstacle_y + RADIUS;

        double list_size = powerup_y_list.size();

        for (int list_num = 0; list_num < list_size; list_num++) {

            double powerups_y = powerup_y_list.get(list_num);
            double powerups_x = powerup_x_list.get(list_num);

            if (powerups_x == new_obstacle_x && powerups_y == new_obstacle_y) {
                createNewObstacle(dot_x, dot_y, dot_x_2, dot_y_2);
                return;
            }
        }

        list_size = obstacles_y_list.size();

        for (int list_num = 0; list_num < list_size; list_num++) {
            double obstacle_y = obstacles_y_list.get(list_num);
            double obstacle_x = obstacles_x_list.get(list_num);

            if (obstacle_x == new_obstacle_x && obstacle_y == new_obstacle_y) {
                createNewObstacle(dot_x, dot_y, dot_x_2, dot_y_2);
                return;
            }
        }


        float dot_radius = (float)(DOT_RADIUS * PowerUpVariables.getDotSize());

        if (PowerUpVariables.getDotShield() == true) {
            dot_radius = (float)(dot_radius * 1.5);
        }

        double distance = Math.sqrt(Math.pow((new_obstacle_x - dot_x), 2) + Math.pow((new_obstacle_y - dot_y), 2));

        if (distance <= RADIUS + (dot_radius * 2)) {//ensure it doesnt appear on the dot
            createNewObstacle(dot_x, dot_y, dot_x_2, dot_y_2);
            return;
        }

        distance = Math.sqrt(Math.pow((new_obstacle_x - dot_x_2), 2) + Math.pow((new_obstacle_y - dot_y_2), 2));

        if (distance <= RADIUS + (dot_radius * 2)) { //ensure it doesnt appear on the dot
            createNewObstacle(dot_x, dot_y, dot_x_2, dot_y_2);
            return;
        }

        obstacles_y_list.add(new_obstacle_y);
        obstacles_x_list.add(new_obstacle_x);

        GameConstants.setObstacleListXandY(obstacles_x_list, obstacles_y_list);
    }

    public void activatePowerUp() {

        int power_up_num = random.nextInt(16);

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

        }else if (power_up_num == 14)   {
            PowerUpVariables.setTripleBalls(true);
        }else if (power_up_num == 15)   {
            PowerUpVariables.setObstacleDrop(true);
        }
    }
}

