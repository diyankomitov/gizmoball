package view;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import model.Flipper;
import physics.LineSegment;
import util.Observer;

import java.util.ArrayList;
import java.util.List;

import static util.Constants.ONE_L_IN_PIXELS;

public class FlipperView extends Group implements Observer{

    private double x;
    private double y;
    private final Flipper flipper;
    private final Rotate rotate;
    private Circle point;
    private Circle point2;
    private Circle point3;
    private Circle point4;
    private double angle;
    private Pane root;



    public FlipperView(Flipper flipper, Pane root) {
        this.root = root;
        this.x = flipper.getXpos() * ONE_L_IN_PIXELS;
        this.y = flipper.getYpos() * ONE_L_IN_PIXELS;
        this.flipper = flipper;
        Rectangle rectangle = new Rectangle(ONE_L_IN_PIXELS/2, ONE_L_IN_PIXELS*2);
        rectangle.setArcWidth(ONE_L_IN_PIXELS/2);
        rectangle.setArcHeight(ONE_L_IN_PIXELS/2);
        this.getChildren().add(rectangle);
        rectangle.setFill(Color.ORANGE);

        this.setTranslateX(x);
        this.setTranslateY(y);

        rotate = new Rotate(0, ONE_L_IN_PIXELS/4, ONE_L_IN_PIXELS/4);
        this.getTransforms().add(rotate);


        LineSegment line1 = flipper.getLines().get(0);
        LineSegment line2 = flipper.getLines().get(1);
        point = new Circle(line1.p1().x() * ONE_L_IN_PIXELS, line1.p1().y() * ONE_L_IN_PIXELS, 2, Color.NAVY);
        point2 = new Circle(line1.p2().x() * ONE_L_IN_PIXELS, line1.p2().y() * ONE_L_IN_PIXELS, 2, Color.PINK);
        point3 = new Circle(line2.p1().x() * ONE_L_IN_PIXELS, line2.p1().y() * ONE_L_IN_PIXELS, 2, Color.RED);
        point4 = new Circle(line2.p2().x() * ONE_L_IN_PIXELS, line2.p2().y() * ONE_L_IN_PIXELS, 2, Color.GREEN);

        root.getChildren().addAll(point, point2, point3, point4);

        flipper.subscribe(this);
    }
    public FlipperView(Flipper flipper) {

        this.x = flipper.getXpos() * ONE_L_IN_PIXELS;
        this.y = flipper.getYpos() * ONE_L_IN_PIXELS;
        this.flipper = flipper;
        Rectangle rectangle = new Rectangle(ONE_L_IN_PIXELS/2, ONE_L_IN_PIXELS*2);
        rectangle.setArcWidth(ONE_L_IN_PIXELS/2);
        rectangle.setArcHeight(ONE_L_IN_PIXELS/2);
        this.getChildren().add(rectangle);
        rectangle.setFill(Color.ORANGE);

        rotate = new Rotate(0, ONE_L_IN_PIXELS/4, ONE_L_IN_PIXELS/4);
        this.getTransforms().add(rotate);


        flipper.subscribe(this);
    }

    public void setX(){

        this.setTranslateX((flipper.getOffset()*ONE_L_IN_PIXELS)-ONE_L_IN_PIXELS);

    }


    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public Circle getPoint() {
        return point;
    }

    public Circle getPoint2() {
        return point2;
    }

    public Circle getPoint3() {
        return point3;
    }

    public Circle getPoint4() {
        return point4;
    }

    public List<Circle> getPoints() {
        ArrayList<Circle> list = new ArrayList<>();
        list.add(point);
        list.add(point2);
        list.add(point3);
        list.add(point4);

        return list;
    }

    public FlipperView() {
        this.x = 0;
        this.y = 0;
        this.flipper = null;
        Rectangle rectangle = new Rectangle(ONE_L_IN_PIXELS/2, ONE_L_IN_PIXELS*2);
        rectangle.setArcWidth(ONE_L_IN_PIXELS/2);
        rectangle.setArcHeight(ONE_L_IN_PIXELS/2);
        this.getChildren().add(rectangle);
        rectangle.setFill(Color.ORANGE);

        rotate = null;
    }

    @Override
    public void update() {
        angle = flipper.getAngle();
//        System.out.println(angle);
        rotate.setAngle(angle);
        LineSegment line1 = flipper.getLines().get(0);
        LineSegment line2 = flipper.getLines().get(1);
//        System.out.println("linex: " + line1.p1().x());
//        System.out.println(line1);
//        System.out.println(line2);

        root.getChildren().removeAll(point, point2, point3, point4);
        point = new Circle(line1.p1().x() * ONE_L_IN_PIXELS, line1.p1().y() * ONE_L_IN_PIXELS, 2, Color.NAVY);
        point2 = new Circle(line1.p2().x() * ONE_L_IN_PIXELS, line1.p2().y() * ONE_L_IN_PIXELS, 2, Color.PINK);
        point3 = new Circle(line2.p1().x() * ONE_L_IN_PIXELS, line2.p1().y() * ONE_L_IN_PIXELS, 2, Color.RED);
        point4 = new Circle(line2.p2().x() * ONE_L_IN_PIXELS, line2.p2().y() * ONE_L_IN_PIXELS, 2, Color.GREEN);
        root.getChildren().addAll(point, point2, point3, point4);

    }
}
