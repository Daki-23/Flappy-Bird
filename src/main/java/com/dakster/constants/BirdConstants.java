package com.dakster.constants;

public class BirdConstants {
    public static final double RADIUS = 35;
    public static final double GRAVITY = 0.5;
    public static final double JUMP = 8;
    public static final double VIEW_ORDER = 2;

    // Since the bird image is not exactly an ellipse, we need these
    // constants to improve the collision detection.
    // I'm sure there's a better way to do this but I have no idea
    // what that is.
    public static final double X_COLLISION_FINE_TUNING = 15;
    public static final double Y_COLLISION_FINE_TUNING = 10;

    public static final String BIRD_STRAIGHT_FILENAME = "/flappy-bird.png";
    public static final String BIRD_UP_FILENAME = "/flappy-bird-up.png";
    public static final String BIRD_DOWN_FILENAME = "/flappy-bird-down.png";

    public static final String JUMP_SOUND_FILENAME = "/jump.wav";
    public static final String HIT_SOUND_FILENAME = "/hit.wav";
    public static final String POINT_SOUND_FILENAME = "/point.wav";
    public static final String DIE_SOUND_FILENAME = "/die.wav";
}
