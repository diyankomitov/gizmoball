package model;

public class BoundingBox {

    private final double x1;
    private final double y1;
    private final double x2;
    private final double y2;

    public BoundingBox(double x1, double y1, double x2, double y2) {

        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public boolean isIntersecting(BoundingBox other) {

        if (this.x2 <= other.x1) {
            return false;
        }
        if (this.x1 >= other.x2) {
            return false;
        }
        if (this.y2 <= other.y1) {
            return false;
        }
        if (this.y1 >= other.y2) {
            return false;
        }

        return true;
    }
}
