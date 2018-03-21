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
        return this.x1 < other.x2
                && other.x1 < this.x2
                && this.y1 < other.y2
                && other.y1 < this.y2;
    }

    public boolean isOutside(){
        if (this.x1 > 19) return true;
        if (this.y1 > 19) return true;
        if (this.x1 < 0) return true;
        if (this.y1 < 0) return true;
        return false;
    }

}
