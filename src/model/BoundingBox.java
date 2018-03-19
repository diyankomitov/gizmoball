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
//        System.out.println("this x1:" + this.x1 + " other x1:" + other.x1);
//        System.out.println("this x2:" + this.x2 + " other x2:" + other.x2);
//        System.out.println("this y1:" + this.y1 + " other y1:" + other.y1);
//        System.out.println("this y2:" + this.y2 + " other y2:" + other.y2);
//        System.out.println("");

        return this.x1 < other.x2
                && other.x1 < this.x2
                && this.y1 < other.y2
                && other.y1 < this.y2;

//        if (this.x1 > other.x2
//                && this.x2 < other.x1
//                && this.y1 > other.y2
//                && this.y2 < other.y1) {
//            return true;
//        }

//        if (this.x1 > other.x2
//                && this.x2 < other.x1
//                && this.y1 > other.y2
//                && this.y2 < other.y1) {
//            return true;
//
//        }

    }

    public boolean isOutside(){
        if(this.x1 > 20 || this.x2 > 20 || this.y1 > 20 || this.y2 > 20 || this.x1 < 0 || this.x2 < 0 || this.y1 < 0 || this.y2 < 0){
            return true;
        }
        return false;
    }

}
