package com.example.ryan3971.battledots_multiplayer;

/**
 * Created by ryan3971 on 2/7/2016.
 */
public class Constants {


    public final int PLAYER_TAG_HOST = 1;
    public final int PLAYER_TAG_CLIENT = 2;

    public static double OTHER_DOT_X = 0;
    public static double OTHER_DOT_Y = 0;

    public final double START_GAME_TRUE_CONSTANT = 1.0;


    public final int WINNER = 0;
    public final int LOSSER = 1;


    public final int START_GAME_CONSTANT = 10;

    public int OTHER_DOT_X_AND_Y_CONSTANT = 11;

    public final int OBSTACLES_CONSTANT = 12;
    public final int POWERUPS_CONSTANT = 13;

    public final int NEW_POWERUPS_CONSTANT = 14;
    public final int NEW_OBSTACLES_CONSTANT = 15;

    public final int DOT_SIZE = 16;

    public final int DOT_INVISIBLE = 17;
    public final double DOT_IS_INVISIBLE = 2.0;
    public final double DOT_IS_VISIBLE = 3.0;

    public final int DOT_SHIELD = 18;
    public final double DOT_IS_SHIELD = 4.0;
    public final double DOT_IS_NOT_SHIELD = 5.0;

    public final int LASER = 19;

    public final int BALL = 20;

    public final int MULTI_LASER = 21;

    public final int TARGET_BALL = 22;

    public final int POWER_WAVE = 23;

    public final int RAPID_FIRE = 24;

    public final int SENTRY_GUN = 25;

    public final int SENTRY_GUN_LASER = 26;


    public final int END_GAME = 30;

    public static final int FIRE_TRIPLE_BALLS = 1;
    public static final int FIRE_OBSTACLE_DROP = 1;


    GameConstants gameConstants;

    public static boolean initiate_once = false;

    public Constants() {

        if (initiate_once == false) {

            initiate_once = true;

            gameConstants = new GameConstants();

            OTHER_DOT_Y = (gameConstants.BOTTOM / 2);
            OTHER_DOT_X = (gameConstants.RIGHT / 2);
        }
    }

    public static void setOtherDotX(double x)  {
        OTHER_DOT_X = x;
    }
    public static double getOtherDotX()  {
        return OTHER_DOT_X;
    }
    public static void setOtherDotY(double y)  {
        OTHER_DOT_Y = y;
    }
    public static double getOtherDotY()  {
        return OTHER_DOT_Y;
    }


}
