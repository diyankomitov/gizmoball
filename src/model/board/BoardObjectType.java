package model.board;

public enum BoardObjectType {
    CIRCLE("Circle"), SQUARE("Circle"), TRIANGLE("Triangle"), LEFT_FLIPPER("Left Flipper"), RIGHT_FLIPPER("Right Flipper"), ABSORBER("Absorber"), WALLS("Walls"), BALL("Ball");

    private final String name;
    BoardObjectType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
