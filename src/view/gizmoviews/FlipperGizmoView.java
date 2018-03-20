package view.gizmoviews;

import javafx.beans.NamedArg;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import model.board.BoardObjectType;
import model.board.gizmos.FlipperGizmo;
import model.board.gizmos.Gizmo;

import static util.Constants.ONE_L_IN_PIXELS;

public class FlipperGizmoView extends GizmoViewContainer {

    private double pivotX;
    private double pivotY;
    private Rotate flip;
    private Rectangle rectangle;
    private FlipperGizmo flipper;

    public FlipperGizmoView(Gizmo flipperGizmo) {
        super(flipperGizmo);

        flipper = (FlipperGizmo) flipperGizmo;
        double width = ONE_L_IN_PIXELS / 2;
        double length = ONE_L_IN_PIXELS * 2;

        rectangle = new Rectangle(width, length);
        rectangle.setArcWidth(width);
        rectangle.setArcHeight(width);
        rectangle.getStyleClass().add("flipperGizmo");

        if (flipperGizmo.getType() == BoardObjectType.RIGHT_FLIPPER) {
            rectangle.setX(length - width);
        }

        this.getChildren().add(rectangle);

        flip = new Rotate(0, pivotX, pivotY);
        rectangle.getTransforms().add(flip);
        update();
    }

    private void setPivots() {
        pivotX = (flipper.getxWithOffset() - flipper.getX()) * ONE_L_IN_PIXELS + ONE_L_IN_PIXELS / 4;
        pivotY = (flipper.getyWithOffset() - flipper.getY()) * ONE_L_IN_PIXELS + ONE_L_IN_PIXELS / 4;
    }

    public FlipperGizmoView(@NamedArg("type") BoardObjectType type) {
        super(null);

        double width = ONE_L_IN_PIXELS / 2;
        double length = ONE_L_IN_PIXELS * 2;

        Rectangle rectangle = new Rectangle(width, length);
        rectangle.setArcWidth(width);
        rectangle.setArcHeight(width);
        rectangle.getStyleClass().add("flipperGizmo");

        if (type == BoardObjectType.RIGHT_FLIPPER) {
            rectangle.setX(length - width);
        }

        this.getChildren().add(rectangle);
    }

    @Override
    public void update() {
        super.update();
        if (rectangle != null) {
            FlipperGizmo flipper = (FlipperGizmo) gizmo;
            rectangle.setX((flipper.getxWithOffset() - flipper.getX()) * ONE_L_IN_PIXELS);
            rectangle.setY((flipper.getyWithOffset() - flipper.getY()) * ONE_L_IN_PIXELS);
        }
        if (flip != null) {
            setPivots();
            flip.setPivotX(pivotX);
            flip.setPivotY(pivotY);
            flip.setAngle(((FlipperGizmo) gizmo).getOldAngle());
//            rectangle.setStyle(null);
//            rectangle.setStyle("-fx-fill: radial-gradient(focus-angle " +  0 + "deg, focus-distance 10%, center 0 0, radius 100%, #f9ffef, #000000);");
        }
    }


    //TODO: remove when no longer needed
//    private void visualiseLineSegments() {
//        if (flipper != null) {
//            Circle point = new Circle(flipper.getLines().get(0).p1().x() * ONE_L_IN_PIXELS, flipper.getLines().get(0).p1().y() * ONE_L_IN_PIXELS, 2, Color.NAVY);
//            Circle point2 = new Circle(flipper.getLines().get(0).p2().x() * ONE_L_IN_PIXELS, flipper.getLines().get(0).p2().y() * ONE_L_IN_PIXELS, 2, Color.PINK);
//            Circle point3 = new Circle(flipper.getLines().get(1).p1().x() * ONE_L_IN_PIXELS, flipper.getLines().get(1).p1().y() * ONE_L_IN_PIXELS, 2, Color.RED);
//            Circle point4 = new Circle(flipper.getLines().get(1).p2().x() * ONE_L_IN_PIXELS, flipper.getLines().get(1).p2().y() * ONE_L_IN_PIXELS, 2, Color.GREEN);
//            ((BoardView)this.getParent()).getChildren().removeAll(point, point2, point3, point4);
//            ((BoardView)this.getParent()).getChildren().addAll(point, point2, point3, point4);
//        }
//    }
}
