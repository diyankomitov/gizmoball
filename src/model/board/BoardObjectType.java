package model.board;

public enum BoardObjectType {
    CIRCLE("Circle"), SQUARE("Square"), TRIANGLE("Triangle"), LEFT_FLIPPER("LeftFlipper"), RIGHT_FLIPPER("RightFlipper"), ABSORBER("Absorber"), WALLS("Walls"), BALL("Ball");

    private final String name;
    BoardObjectType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
