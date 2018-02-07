package main;

public class Constants {

    public static final double ONE_L = 1.0;
    public static final double ONE_L_IN_PIXELS = ONE_L * 50;
    public static final double FRAMERATE = 20;
    public static final double FRAMERATE_IN_MILLIS = FRAMERATE * 1000;
    public static final double SECONDS_PER_FRAME = 1/FRAMERATE;
    public static final double MILLIS_PER_FRAME = 1000/FRAMERATE;
    public static final double FLIPPER_ANGULAR_VELOCITY = 1080 * ONE_L;
    public static final double DELTA_ANGLE = FLIPPER_ANGULAR_VELOCITY/FRAMERATE;
}
