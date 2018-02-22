package util;

public class Constants {
    public static final double ONE_L = 1.0;
    public static final double ONE_L_IN_PIXELS = ONE_L * 40;
    public static final double FRAMERATE = 60;
    public static final double FRAMERATE_IN_MILLIS = FRAMERATE * 1000;
    public static final double SECONDS_PER_FRAME = 1/FRAMERATE;
    public static final double MILLIS_PER_FRAME = 1000/FRAMERATE;
    public static final double FLIPPER_ANGULAR_VELOCITY = 1080 * ONE_L;
    public static final double DELTA_ANGLE = FLIPPER_ANGULAR_VELOCITY/FRAMERATE;
    public static final double GRAVITY = 25 * ONE_L;
    public static final double FRICTION_MU = 0.025;
    public static final double FRICTION_MU_2 = 0.025 * ONE_L;
    public static final double BOARD_WIDTH = 20;

}
