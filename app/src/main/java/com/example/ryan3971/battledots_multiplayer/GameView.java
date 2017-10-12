package com.example.ryan3971.battledots_multiplayer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by ryan3971 on 2/4/2016.
 */
public class GameView extends View {

    Dot dot;
    Boundaries boundaries;
    TouchPad touchPad;
    ObstaclesAndPowerUps obstaclesAndPowerUps;
    // PowerUp_Images powerUp_Images;
    ManageObjects manageObjects_Laser;
    ManageObjects manageObjects_Ball;
    ManageObjects manageObjects_Multi_Laser;
    ManageObjects manageObjects_Target_Ball;
    ManageObjects manageObjects_Power_Wave;
    ManageObjects manageObjects_Rapid_Fire;
    ManageObjects manageObjects_Sentry_Gun;
    ManageObjects manageObjects_Triple_Balls;
    ManageObjects manageObjects_Obstacle_Drop;

    ManageObjects manageObjects;


    OtherManageObjects manageObjects_Laser_2;
    OtherManageObjects manageObjects_Ball_2;
    OtherManageObjects manageObjects_Multi_Laser_2;
    OtherManageObjects manageObjects_Target_Ball_2;
    OtherManageObjects manageObjects_Power_Wave_2;
    OtherManageObjects manageObjects_Rapid_Fire_2;
    OtherManageObjects manageObjects_Sentry_Gun_2;

    PowerUp_Images powerUp_Images;

    speedBoost speedBoost;
    Lives lives;

    Context context;

    boolean isTouchPad;

    //  public static boolean START_GAME;
    Other_Dot otherDot;
    OtherObstaclesAndPowerUps otherObstaclesAndPowerups;
    sendData sendData;
    GameConstants gameConstants;

    MainActivity mainActivity;

    Constants CONSTANTS;

    FireButton fireButton;
    Handler handler;

    boolean fireButtonOff;


    /**
     *The constructor for this class. It is called in MainActivity.OnCreate to initialise a
     *
     *
     * @param c - Provide Context - Don't know what Context really is but its used alot nd helpful to have
     * @param attrs - Don't know what this is used for
     */
    public GameView(Context c, AttributeSet attrs)   {
        super(c, attrs);

        context = c;
        mainActivity = new MainActivity();
        CONSTANTS = new Constants();
        handler = new Handler();
    }



    public void initilizer()    {

        gameConstants = new GameConstants();
        otherDot = new Other_Dot();
        sendData = new sendData();
        PowerUpVariables powerUpVariables = new PowerUpVariables();

        powerUp_Images = new PowerUp_Images(context);
        PowerUpVariables.powerUp_Images = powerUp_Images;

        boundaries = new Boundaries();

        if (gameConstants.PLAYER_TAG == 1) {
            obstaclesAndPowerUps = new ObstaclesAndPowerUps(context);
            obstaclesAndPowerUps.createObstaclesAndPowerUps(boundaries.getDotX(), boundaries.getDotY());
        }else {
            otherObstaclesAndPowerups = new OtherObstaclesAndPowerUps();
        }

        isTouchPad = false;

        dot = new Dot();
        touchPad = new TouchPad();

        //   powerUp_Images = new PowerUp_Images();
        manageObjects_Laser = new ManageObjects();
        manageObjects_Ball = new ManageObjects();
        manageObjects_Multi_Laser = new ManageObjects();
        manageObjects_Power_Wave = new ManageObjects();
        manageObjects_Target_Ball = new ManageObjects();
        manageObjects_Rapid_Fire = new ManageObjects();
        manageObjects_Sentry_Gun = new ManageObjects();
        manageObjects_Triple_Balls = new ManageObjects();
        manageObjects_Obstacle_Drop = new ManageObjects();

        manageObjects = new ManageObjects();


        manageObjects_Laser_2 = new OtherManageObjects();
        manageObjects_Ball_2 = new OtherManageObjects();
        manageObjects_Multi_Laser_2 = new OtherManageObjects();
        manageObjects_Target_Ball_2 = new OtherManageObjects();
        manageObjects_Power_Wave_2 = new OtherManageObjects();
        manageObjects_Rapid_Fire_2 = new OtherManageObjects();
        manageObjects_Sentry_Gun_2 = new OtherManageObjects();

        speedBoost = new speedBoost();
        lives = new Lives(context);
        fireButton = new FireButton();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        return;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        setBackgroundColor(Color.rgb(255, 255, 255));

        otherDot.drawDot(canvas, boundaries.getDotX(), boundaries.getDotY());   //put here so you cant see them when invisible
        dot.drawDot(canvas);

        if (GameConstants.PLAYER_TAG == 1) {
            obstaclesAndPowerUps.updateAndDrawObstacles(canvas, boundaries.getObstaclesAndPowerUpsMovement_Y(), boundaries.getObstaclesAndPowerUpsMovement_X(), boundaries.getDotX(), boundaries.getDotY());
            obstaclesAndPowerUps.updateAndDrawPowerUps(canvas, boundaries.getObstaclesAndPowerUpsMovement_Y(), boundaries.getObstaclesAndPowerUpsMovement_X(), boundaries.getDotX(), boundaries.getDotY());

            obstaclesAndPowerUps.checkPowerUpCollisions(boundaries.getObstaclesAndPowerUpsMovement_Y(), boundaries.getObstaclesAndPowerUpsMovement_X(), boundaries.getDotX(), boundaries.getDotY());
            obstaclesAndPowerUps.checkObstacleCollisions(boundaries.getObstaclesAndPowerUpsMovement_Y(), boundaries.getObstaclesAndPowerUpsMovement_X(), boundaries.getDotX(), boundaries.getDotY());

        } else if (GameConstants.PLAYER_TAG == 2) {
            otherObstaclesAndPowerups.updateAndDrawObstacles(canvas, boundaries.getObstaclesAndPowerUpsMovement_Y(), boundaries.getObstaclesAndPowerUpsMovement_X());
            otherObstaclesAndPowerups.updateAndDrawPowerUps(canvas, boundaries.getObstaclesAndPowerUpsMovement_Y(), boundaries.getObstaclesAndPowerUpsMovement_X());

            otherObstaclesAndPowerups.checkPowerUpCollisions(boundaries.getObstaclesAndPowerUpsMovement_Y(), boundaries.getObstaclesAndPowerUpsMovement_X());
            otherObstaclesAndPowerups.checkObstacleCollisions(boundaries.getObstaclesAndPowerUpsMovement_Y(), boundaries.getObstaclesAndPowerUpsMovement_X());

        }

        manageObjects.manageLaser(boundaries.getDotX(), boundaries.getDotY(), boundaries.getInstantaneousMovementX(), boundaries.getInstantaneousMovementY(), canvas);
        manageObjects.manageBall(boundaries.getDotX(), boundaries.getDotY(), boundaries.getInstantaneousMovementX(), boundaries.getInstantaneousMovementY(), canvas);
        manageObjects.manageMultiLaser(boundaries.getDotX(), boundaries.getDotY(), boundaries.getInstantaneousMovementX(), boundaries.getInstantaneousMovementY(), canvas);
        manageObjects.manageTargetBall(boundaries.getDotX(), boundaries.getDotY(), boundaries.getInstantaneousMovementX(), boundaries.getInstantaneousMovementY(), canvas);
        manageObjects.managePowerWave(boundaries.getDotX(), boundaries.getDotY(), boundaries.getInstantaneousMovementX(), boundaries.getInstantaneousMovementY(), canvas);
        manageObjects.manageRapidFire(boundaries.getDotX(), boundaries.getDotY(), boundaries.getInstantaneousMovementX(), boundaries.getInstantaneousMovementY(), canvas);
        manageObjects.manageSentryGun(boundaries.getDotX(), boundaries.getDotY(), boundaries.getInstantaneousMovementX(), boundaries.getInstantaneousMovementY(), canvas);
        manageObjects.manageTripleBouncingBalls(boundaries.getDotX(), boundaries.getDotY(), boundaries.getInstantaneousMovementX(), boundaries.getInstantaneousMovementY(), touchPad, canvas);
        manageObjects.manageObstacleDrop(boundaries.getDotX(), boundaries.getDotY(), boundaries.getInstantaneousMovementX(), boundaries.getInstantaneousMovementY(), touchPad, canvas);

        manageObjects_Laser_2.manageLaser(boundaries.getDotX(), boundaries.getDotY(), boundaries.getInstantaneousMovementX(), boundaries.getInstantaneousMovementY(), canvas);
        manageObjects_Ball_2.manageBall(boundaries.getDotX(), boundaries.getDotY(), boundaries.getInstantaneousMovementX(), boundaries.getInstantaneousMovementY(), canvas);
        manageObjects_Multi_Laser_2.manageMultiLaser(boundaries.getDotX(), boundaries.getDotY(), boundaries.getInstantaneousMovementX(), boundaries.getInstantaneousMovementY(), canvas);
        manageObjects_Target_Ball_2.manageTargetBall(boundaries.getDotX(), boundaries.getDotY(), boundaries.getInstantaneousMovementX(), boundaries.getInstantaneousMovementY(), canvas);
        manageObjects_Power_Wave_2.managePowerWave(boundaries.getDotX(), boundaries.getDotY(), boundaries.getInstantaneousMovementX(), boundaries.getInstantaneousMovementY(), canvas);
        manageObjects_Rapid_Fire_2.manageRapidFire(boundaries.getDotX(), boundaries.getDotY(), boundaries.getInstantaneousMovementX(), boundaries.getInstantaneousMovementY(), canvas);
        manageObjects_Sentry_Gun_2.manageSentryGun(boundaries.getDotX(), boundaries.getDotY(), boundaries.getInstantaneousMovementX(), boundaries.getInstantaneousMovementY(), canvas);

        lives.drawLives(canvas);
        powerUp_Images.drawPowerUpImages(canvas);

        boundaries.updateBoundaries(touchPad.getLargePadX(), touchPad.getLargePadY(), touchPad.getSmallPadX(), touchPad.getSmallPadY(), isTouchPad);
        boundaries.drawBoundaries(canvas); //placed here so manageObject_objects will go under it

        lives.drawLives(canvas);
        speedBoost.drawSpeedBoostButton(canvas);
        fireButton.drawFireButton(canvas);

        if (isTouchPad == true) {
            touchPad.drawTouchPad(canvas);
        }

    //    sendData.sendXandY(boundaries.getDotX(), boundaries.getDotY());


        if (Lives.getLives() == 0)  {
            sendData.sendEndGame();
            mainActivity.endGame(CONSTANTS.LOSSER);
        }

        invalidate();


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                isTouchPad = true;
                touchPad.setLargePadX(x);
                touchPad.setLargePadY(y);
                touchPad.setSmallPadX(x);
                touchPad.setSmallPadY(y);

                break;
            case MotionEvent.ACTION_MOVE:

                double distance = Math.hypot((x - touchPad.getLargePadX()), (y - touchPad.getLargePadY()));

                if (distance <= touchPad.LARGE_PAD_RADIUS) {
                    touchPad.setSmallPadX(x);
                    touchPad.setSmallPadY(y);
                } else {

                    double angle = Math.atan2(x - touchPad.getLargePadX(), y - touchPad.getLargePadY());

                    double angleX = Math.sin(angle);    //Having x as sin and y as cos makes it work. Not sure why since  = x and sin = y
                    double angleY = Math.cos(angle);

                    angleX = touchPad.getLargePadX() + (angleX * touchPad.LARGE_PAD_RADIUS);
                    angleY = touchPad.getLargePadY() + (angleY * touchPad.LARGE_PAD_RADIUS);

                    touchPad.setSmallPadX((float) angleX);
                    touchPad.setSmallPadY((float) angleY);
                }

                break;
            case MotionEvent.ACTION_UP:
                isTouchPad = false;

                touchPad.setLargePadX(0);
                touchPad.setLargePadY(0);
                touchPad.setSmallPadX(0);
                touchPad.setSmallPadY(0);

                break;
        }

        if (event.getPointerCount() >= 2) {

            double second_x = event.getX(1);
            double second_y = event.getY(1);

            double button_x = speedBoost.BUTTON_X;
            double button_y = speedBoost.BUTTON_Y;
            double button_radius = speedBoost.BUTTON_RADIUS;

            double in_button = Math.hypot((second_x - button_x), (second_y - button_y));

            if (in_button <= button_radius) {
                PowerUpVariables.setIsSpeedBoost(true);
            }

            double fire_x = fireButton.BUTTON_X;
            double fire_y = fireButton.BUTTON_Y;
            double fire_radius = fireButton.BUTTON_RADIUS;

            double in_fire = Math.hypot((second_x - fire_x), (second_y - fire_y));

            if (in_fire <= fire_radius && fireButtonOff == false) {
                fireButtonOff = true;
                PowerUpVariables.doFireButton(touchPad);
                handler.postDelayed(fireButtonCooldown, 500);
            }
        }
        return true;
    }

    public Runnable fireButtonCooldown = new Runnable() {
        @Override
        public void run() {
            fireButtonOff = false;
        }
    };


}
