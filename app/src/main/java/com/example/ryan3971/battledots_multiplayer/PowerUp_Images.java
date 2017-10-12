package com.example.ryan3971.battledots_multiplayer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ryan3971 on 2/5/2016.
 */
public class PowerUp_Images {

    boolean IS_SIZE_LARGE = false;
    boolean IS_SIZE_SMALL = false;
    boolean IS_SPEED_FAST = false;
    boolean IS_SPEED_SLOW = false;
    boolean IS_OPPOSITE_DIRECTION = false;
    boolean IS_INVISIBLE = false;
    boolean IS_LASER = false;
    boolean IS_BALL = false;
    boolean IS_SHEILD = false;
    boolean IS_MULTI_LASER = false;
    boolean IS_TARGET_BALL = false;
    boolean IS_POWER_WAVE = false;
    boolean IS_RAPID_FIRE = false;
    boolean IS_SENTRY_GUN = false;
    boolean IS_TRIPLE_BALLS = false;
    boolean IS_OBSTACLE_DROP = false;

    Drawable SIZE_LARGE;
    Drawable SIZE_SMALL;
    Drawable SPEED_FAST;
    Drawable SPEED_SLOW;
    Drawable OPPOSITE_DIRECTION;
    Drawable INVISIBLE;
    Drawable LASER;
    Drawable BOUNCING_BALL;
    Drawable SHEILD;
    Drawable MULTI_LASER;
    Drawable TARGET_BALL;
    Drawable POWER_WAVE;
    Drawable RAPID_FIRE;
    Drawable SENTRY_GUN;
    Drawable TRIPLE_BALLS;
    Drawable OBSTACLE_DROP;


    GameConstants gameConstants;

    double POWER_UP_IMAGE_WIDTH;
    double POWER_UP_IMAGE_HEIGHT;

    double WIDTH, HEIGHT;

    public PowerUp_Images(Context context) {

        IS_SIZE_LARGE = false;
        IS_SIZE_SMALL = false;
        IS_SPEED_FAST = false;
        IS_SPEED_SLOW = false;
        IS_OPPOSITE_DIRECTION = false;
        IS_INVISIBLE = false;
        IS_LASER = false;
        IS_BALL = false;
        IS_SHEILD = false;
        IS_MULTI_LASER = false;
        IS_TARGET_BALL = false;
        IS_POWER_WAVE = false;
        IS_RAPID_FIRE = false;
        IS_SENTRY_GUN = false;
        IS_TRIPLE_BALLS = false;
        IS_OBSTACLE_DROP = false;


        SIZE_LARGE = ResourcesCompat.getDrawable(context.getResources(), R.drawable.blue_dot_scaled_1, null);
        SIZE_SMALL = ResourcesCompat.getDrawable(context.getResources(), R.drawable.blue_dot_scaled_1, null);
        SPEED_FAST = ResourcesCompat.getDrawable(context.getResources(), R.drawable.blue_dot_scaled_1, null);
        SPEED_SLOW = ResourcesCompat.getDrawable(context.getResources(), R.drawable.blue_dot_scaled_1, null);
        OPPOSITE_DIRECTION = ResourcesCompat.getDrawable(context.getResources(), R.drawable.blue_dot_scaled_1, null);
        INVISIBLE = ResourcesCompat.getDrawable(context.getResources(), R.drawable.blue_dot_scaled_1, null);
        LASER = ResourcesCompat.getDrawable(context.getResources(), R.drawable.blue_dot_scaled_1, null);
        BOUNCING_BALL = ResourcesCompat.getDrawable(context.getResources(), R.drawable.blue_dot_scaled_1, null);
        SHEILD = ResourcesCompat.getDrawable(context.getResources(), R.drawable.blue_dot_scaled_1, null);
        MULTI_LASER = ResourcesCompat.getDrawable(context.getResources(), R.drawable.blue_dot_scaled_1, null);
        TARGET_BALL = ResourcesCompat.getDrawable(context.getResources(), R.drawable.blue_dot_scaled_1, null);
        POWER_WAVE = ResourcesCompat.getDrawable(context.getResources(), R.drawable.blue_dot_scaled_1, null);
        RAPID_FIRE = ResourcesCompat.getDrawable(context.getResources(), R.drawable.blue_dot_scaled_1, null);
        SENTRY_GUN = ResourcesCompat.getDrawable(context.getResources(), R.drawable.blue_dot_scaled_1, null);
        TRIPLE_BALLS = ResourcesCompat.getDrawable(context.getResources(), R.drawable.blue_dot_scaled_1, null);
        OBSTACLE_DROP = ResourcesCompat.getDrawable(context.getResources(), R.drawable.blue_dot_scaled_1, null);

        gameConstants = new GameConstants();
        HEIGHT = gameConstants.getHeight();
        WIDTH = gameConstants.getWidth();

        POWER_UP_IMAGE_WIDTH = WIDTH * 0.05;
        POWER_UP_IMAGE_HEIGHT = HEIGHT * 0.1;

    }

    public void drawPowerUpImages(Canvas canvas) {

        ArrayList<Drawable> powerUp_Images_List = new ArrayList();

        if (IS_SIZE_LARGE) {
            powerUp_Images_List.add(SIZE_LARGE);
        }
        if (IS_SIZE_SMALL) {
            powerUp_Images_List.add(SIZE_SMALL);
        }
        if (IS_SPEED_FAST) {
            powerUp_Images_List.add(SPEED_FAST);
        }
        if (IS_SPEED_SLOW) {
            powerUp_Images_List.add(SPEED_SLOW);
        }
        if (IS_OPPOSITE_DIRECTION) {
            powerUp_Images_List.add(OPPOSITE_DIRECTION);
        }
        if (IS_INVISIBLE) {
            powerUp_Images_List.add(INVISIBLE);
        }
        if (IS_LASER) {

            powerUp_Images_List.add(LASER);
        }
        if (IS_BALL) {

            powerUp_Images_List.add(BOUNCING_BALL);
        }
        if (IS_SHEILD) {
            powerUp_Images_List.add(SHEILD);
        }
        if (IS_MULTI_LASER) {
            powerUp_Images_List.add(MULTI_LASER);
        }
        if (IS_TARGET_BALL) {
            powerUp_Images_List.add(TARGET_BALL);
        }
        if (IS_POWER_WAVE) {
            powerUp_Images_List.add(POWER_WAVE);
        }
        if (IS_RAPID_FIRE) {
            powerUp_Images_List.add(RAPID_FIRE);
        }
        if (IS_SENTRY_GUN) {
            powerUp_Images_List.add(SENTRY_GUN);
        }
        if (IS_TRIPLE_BALLS) {
            powerUp_Images_List.add(TRIPLE_BALLS);
        }
        if (IS_OBSTACLE_DROP) {
            powerUp_Images_List.add(OBSTACLE_DROP);
        }

        int power_up_images_list_size = powerUp_Images_List.size();

        for (int list_num = 0; list_num < power_up_images_list_size; list_num++) {
            int left = (int)(WIDTH - POWER_UP_IMAGE_WIDTH);
            int top = (int)(list_num * POWER_UP_IMAGE_HEIGHT);
            int right = (int)WIDTH;
            int bottom = (int)(top + POWER_UP_IMAGE_HEIGHT);

            powerUp_Images_List.get(list_num).setBounds(left, top, right, bottom);
            powerUp_Images_List.get(list_num).draw(canvas);

        }
    }
    public void showSizeLargeImage(boolean showImage) {
        IS_SIZE_LARGE = showImage;
    }
    public void showSizeSmallImage(boolean showImage) {
        IS_SIZE_SMALL = showImage;
    }
    public void showSpeedFastImage(boolean showImage) {
        IS_SPEED_FAST = showImage;
    }
    public void showSpeedSlowImage(boolean showImage) {
        IS_SPEED_SLOW = showImage;
    }
    public void showOppositeDirectionImage(boolean showImage) {
        IS_OPPOSITE_DIRECTION = showImage;
    }
    public void showInvisibleImage(boolean showImage) {
        IS_INVISIBLE = showImage;
    }
    public void showLaserImage(boolean showImage) {
        IS_LASER = showImage;
    }
    public void showBallImage(boolean showImage) {
        IS_BALL = showImage;
    }
    public void showShieldImage(boolean showImage) {
        IS_SHEILD = showImage;
    }
    public void showMultiLaserImage(boolean showImage) {
        IS_MULTI_LASER = showImage;
    }
    public void showTargetBallImage(boolean showImage) {
        IS_TARGET_BALL = showImage;
    }
    public void showPowerWaveImage(boolean showImage) {
        IS_POWER_WAVE = showImage;
    }
    public void showRapidFireImage(boolean showImage) {
        IS_RAPID_FIRE = showImage;
    }
    public void showSentryGunImage(boolean showImage) {
        IS_SENTRY_GUN = showImage;
    }
    public void showTripleBallsImage(boolean showImage) {
        IS_TRIPLE_BALLS = showImage;
    }
    public void showObstacleDropImage(boolean showImage) {
        IS_OBSTACLE_DROP = showImage;
    }

}
