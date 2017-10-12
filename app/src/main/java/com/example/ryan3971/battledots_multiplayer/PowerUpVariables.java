package com.example.ryan3971.battledots_multiplayer;

/**
 * Created by ryan3971 on 2/4/2016.
 */
public class PowerUpVariables {

    static boolean DOT_SPEED_FAST;
    static boolean DOT_SPEED_SLOW;
    static double DOT_SPEED;

    static boolean DOT_SIZE_LARGE;
    static boolean DOT_SIZE_SMALL;
    static double DOT_SIZE;

    static boolean DOT_OPPOSITE_DIRECTION;
    static boolean DOT_VISIBLE;
    static boolean DOT_SHIELD;

    static boolean IS_SPEED_BOOST;
    static int SPEED_BOOST_STAGE;
    static double SPEED_BOOST;

    static boolean LASER;
    static boolean BALL;
    static boolean MULTI_LASER;
    static boolean NEW_POWER_WAVE;
    static boolean POWER_WAVE;
    static boolean TARGET_BALL;
    static boolean RAPID_FIRE;
    static boolean SENTRY_GUN;
    static boolean TRIPLE_BALLS;
    static boolean OBSTACLE_DROP;


    static PowerUp_Images powerUp_Images;

    static sendData sendData;
    static Constants CONSTANTS;
    static GameConstants gameConstants;

    static double WIDTH;

    public PowerUpVariables()  {

        gameConstants = new GameConstants();
        WIDTH = gameConstants.WIDTH;

        DOT_SPEED = 6;
        DOT_SIZE = 1;

        DOT_SPEED_FAST = false;
        DOT_SPEED_SLOW = false;
        DOT_SIZE_LARGE = false;
        DOT_SIZE_SMALL = false;
        DOT_OPPOSITE_DIRECTION = false;
        DOT_VISIBLE = false;
        DOT_SHIELD = false;
        LASER = false;
        BALL = false;
        MULTI_LASER = false;
        NEW_POWER_WAVE = false;
        POWER_WAVE = false;
        TARGET_BALL = false;
        RAPID_FIRE = false;
        SENTRY_GUN = false;
        TRIPLE_BALLS = false;


        IS_SPEED_BOOST = false;
        SPEED_BOOST_STAGE = 1;
        SPEED_BOOST = 1.0;

        sendData = new sendData();
        CONSTANTS = new Constants();

    }

    public static void setDotSpeedFast(boolean newSpeed) {
        DOT_SPEED_FAST = newSpeed;
        powerUp_Images.showSpeedFastImage(newSpeed);
        setDotSpeed();
    }
    public static void setDotSpeedSlow(boolean newSpeed) {
        DOT_SPEED_SLOW = newSpeed;
        powerUp_Images.showSpeedSlowImage(newSpeed);
        setDotSpeed();
    }
    public static void setDotSpeed() {

        if (DOT_SPEED_FAST == true) {
            DOT_SPEED = 8;
        } else if (DOT_SPEED_SLOW == true) {
            DOT_SPEED = 4;
        } else {
            DOT_SPEED = 6;
        }
    }
    public static boolean getDotSpeedFast() {
        return DOT_SPEED_FAST;
    }
    public static boolean getDotSpeedSlow() {
        return DOT_SPEED_SLOW;
    }

    public static double getDotSpeed() {
        return DOT_SPEED;
    }

    public static void setDotSizeLarge(boolean newSize) {
        DOT_SIZE_LARGE = newSize;
        powerUp_Images.showSizeLargeImage(newSize);
        setDotSize();
    }
    public static void setDotSizeSmall(boolean newSize) {
        DOT_SIZE_SMALL = newSize;
        powerUp_Images.showSizeSmallImage(newSize);
        setDotSize();
    }
    public static void setDotSize() {

        if (DOT_SIZE_LARGE == true) {
            DOT_SIZE = 3;
        } else if (DOT_SIZE_SMALL == true) {
            DOT_SIZE = 0.25;
        } else {
            DOT_SIZE = 1;
        }

        sendData.sendSize(DOT_SIZE);

    }
    public static boolean getDotSizeLarge() {
        return DOT_SIZE_LARGE;
    }
    public static boolean getDotSizeSmall() {
        return DOT_SIZE_SMALL;
    }
    public static double getDotSize() {
        return DOT_SIZE;
    }

    public static void setDotOppositeDirection(boolean opposite_direction) {
        DOT_OPPOSITE_DIRECTION = opposite_direction;
        powerUp_Images.showOppositeDirectionImage(opposite_direction);
    }
    public static boolean getDotOppositeDirecton() {
        return DOT_OPPOSITE_DIRECTION;
    }

    public static void setDotInvisible(boolean invisible) {
        DOT_VISIBLE = invisible;
        powerUp_Images.showInvisibleImage(invisible);

        if (invisible)
            sendData.sendInvisible(CONSTANTS.DOT_IS_INVISIBLE);
        else
            sendData.sendInvisible(CONSTANTS.DOT_IS_VISIBLE);


    }

    public static boolean getDotInvisible() {
        return DOT_VISIBLE;
    }

    public static void setLaser(boolean newLaser) {
        LASER = newLaser;

    }
    public static boolean getLaser() {
        return LASER;
    }

    public static void showLaserImage(boolean showImage) {
        powerUp_Images.showLaserImage(showImage);
    }


    public static void setBall(boolean newBall) {
        BALL = newBall;
    }
    public static boolean getBall() {
        return BALL;
    }
    public static void showBallImage(boolean showImage) {
        powerUp_Images.showBallImage(showImage);
    }


    public static void setDotShield(boolean isShield) {
        DOT_SHIELD = isShield;
        powerUp_Images.showShieldImage(isShield);

        if (isShield)
            sendData.sendShield(CONSTANTS.DOT_IS_SHIELD);
        else
            sendData.sendShield(CONSTANTS.DOT_IS_NOT_SHIELD);

    }
    public static boolean getDotShield() {
        return DOT_SHIELD;
    }



    public static void setMultiLaser(boolean isMultiLaser) {
        MULTI_LASER = isMultiLaser;
    }
    public static boolean getMultiLaser() {
        return MULTI_LASER;
    }
    public static void showMultiLaserImage(boolean showImage) {
        powerUp_Images.showMultiLaserImage(showImage);
    }

    public static void setTargetBall(boolean newTargetBall) {
        TARGET_BALL = newTargetBall;
    }
    public static boolean getTargetBall() {
        return TARGET_BALL;
    }
    public static void showTargetBallImage(boolean showImage) {
        powerUp_Images.showTargetBallImage(showImage);
    }

    public static void setNewPowerWave(boolean newWave) {
        NEW_POWER_WAVE = newWave;
    }
    public static boolean getNewPowerWave() {
        return NEW_POWER_WAVE;
    }
    public static void setPowerWave(boolean isPowerWave) {
        POWER_WAVE = isPowerWave;
        setNewPowerWave(isPowerWave);
    }
    public static boolean getPowerWave() {
        return POWER_WAVE;
    }

    public static void showPowerWaveImage(boolean showImage) {
        powerUp_Images.showPowerWaveImage(showImage);
    }


    public static void setRapidFire(boolean isRapidFire) {
        RAPID_FIRE = isRapidFire;
    }
    public static boolean getRapidFire() {
        return RAPID_FIRE;
    }
    public static void showRapidFireImage(boolean showImage) {
        powerUp_Images.showRapidFireImage(showImage);
    }


    public static void setSentryGun(boolean isSentryGun) {
        SENTRY_GUN = isSentryGun;
    }
    public static boolean getSentryGun() {
        return SENTRY_GUN;
    }
    public static void showSentryGunImage(boolean showImage) {
        powerUp_Images.showSentryGunImage(showImage);
    }

    public static void speedBoost() {

        SPEED_BOOST_STAGE += 1;
        int speed_boost_stage = SPEED_BOOST_STAGE;

        if (speed_boost_stage == 1) {
            SPEED_BOOST = 1.5;
        } else if (speed_boost_stage == 2) {
            SPEED_BOOST = 2.0;
        } else if (speed_boost_stage == 3) {
            SPEED_BOOST = 2.5;
        } else if (speed_boost_stage == 4) {
            SPEED_BOOST = 3.0;
        } else if (speed_boost_stage == 5) {
            SPEED_BOOST = 3.5;
        } else if (speed_boost_stage == 6) {
            SPEED_BOOST = 3.0;
        } else if (speed_boost_stage == 7) {
            SPEED_BOOST = 2.5;
        } else if (speed_boost_stage == 8) {
            SPEED_BOOST = 2.0;
        } else if (speed_boost_stage == 9) {
            SPEED_BOOST = 1.5;
        } else if (speed_boost_stage == 10) {
            SPEED_BOOST = 1.0;
            SPEED_BOOST_STAGE = 0;
            IS_SPEED_BOOST = false;
        }
    }

    public static void setIsSpeedBoost(boolean isSpeedBoost) {
        IS_SPEED_BOOST = isSpeedBoost;
    }
    public static boolean IsSpeedBoost() {
        return IS_SPEED_BOOST;
    }
    public static double getSpeedBoost() {
        return SPEED_BOOST;
    }

    public static void setTripleBalls(boolean isTripleBalls)  {
        TRIPLE_BALLS = isTripleBalls;
        whichFirePowerUp(CONSTANTS.FIRE_TRIPLE_BALLS);
    }
    public static boolean getTripleBalls()  {
        return TRIPLE_BALLS;
    }

    public static void showTripleBallImage(boolean showImage) {
        powerUp_Images.showTripleBallsImage(showImage);
    }


    public static void setObstacleDrop(boolean isObstacleDrop)  {
        OBSTACLE_DROP = isObstacleDrop;
        whichFirePowerUp(CONSTANTS.FIRE_OBSTACLE_DROP);
    }
    public static boolean getObstacleDrop()  {
        return OBSTACLE_DROP;
    }

    public static void showObstacleDropImage(boolean showImage) {
        powerUp_Images.showObstacleDropImage(showImage);
    }



    public static void whichFirePowerUp(int whichPowerNum)     {

        if (whichPowerNum == CONSTANTS.FIRE_TRIPLE_BALLS) {
            GameConstants.FIRE_TRIPLE_BALLS = true;
        }else if (whichPowerNum == CONSTANTS.FIRE_OBSTACLE_DROP) {
            GameConstants.FIRE_OBSTACLE_DROP = true;
        }
    }


    public static void doFireButton(TouchPad touchPad)   {

        if (GameConstants.FIRE_TRIPLE_BALLS && GameConstants.DO_FIRE_TRIPLE_BALLS == false)    {
            double angle = Math.atan2(touchPad.getSmallPadY() - touchPad.getLargePadY(), touchPad.getSmallPadX() - touchPad.getLargePadX());
            GameConstants.TRIPLE_BALLS_FIRE_ANGLE = angle;   //negative angle so everything is not opposite
            GameConstants.DO_FIRE_TRIPLE_BALLS = true;
        }

        else if (GameConstants.FIRE_OBSTACLE_DROP && GameConstants.DO_FIRE_OBSTACLE_DROP == false)    {
            GameConstants.DO_FIRE_OBSTACLE_DROP = true;
        }
    }
}
