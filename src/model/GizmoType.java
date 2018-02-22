package model;

public enum GizmoType {
    CIRCLE("Circle"), SQUARE("Square"), TRIANGLE("Triangle"), LEFT_FLIPPER("LeftFlipper"), RIGHT_FLIPPER("RightFlipper"), ABSORBER("Absorber"), WALLS("Wall");

    private final String name;

    GizmoType(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
