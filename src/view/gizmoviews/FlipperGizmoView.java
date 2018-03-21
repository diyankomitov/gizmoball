package view.gizmoviews;

import javafx.beans.NamedArg;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import model.board.BoardObjectType;
import model.board.gizmos.FlipperGizmo;
import model.board.gizmos.Gizmo;

import static util.Constants.ONE_L_IN_PIXELS;

public class FlipperGizmoView extends GizmoViewContainer {

    private Circle pivotCircle;
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
        rectangle.setMouseTransparent(true);


        if (flipperGizmo.getType() == BoardObjectType.RIGHT_FLIPPER) {
            rectangle.setX(length - width);
        }

        this.getChildren().add(rectangle);
        this.setPrefSize(length, length);

        flip = new Rotate(0, pivotX, pivotY);
        rectangle.getTransforms().add(flip);
        pivotCircle = new Circle(pivotX, pivotY, 0.66 * width/2);
        pivotCircle.getStyleClass().add("flipperPivot");
        pivotCircle.setMouseTransparent(true);
        pivotCircle.getTransforms().add(flip);
        this.getChildren().add(pivotCircle);
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

        Circle pivotCircle = new Circle(rectangle.getX() + width/2, rectangle.getY() + width/2, 0.66 * width/2);
        pivotCircle.getStyleClass().add("flipperPivot");
        pivotCircle.setMouseTransparent(true);
        this.getChildren().add(pivotCircle);
    }

    @Override
    public void update() {
        super.update();
        if (rectangle != null) {
            FlipperGizmo flipper = (FlipperGizmo) gizmo;
            rectangle.setX((flipper.getxWithOffset() - flipper.getX()) * ONE_L_IN_PIXELS);
            rectangle.setY((flipper.getyWithOffset() - flipper.getY()) * ONE_L_IN_PIXELS);
            pivotCircle.setCenterX((flipper.getxWithOffset() - flipper.getX()) * ONE_L_IN_PIXELS + ONE_L_IN_PIXELS / 4);
            pivotCircle.setCenterY((flipper.getyWithOffset() - flipper.getY()) * ONE_L_IN_PIXELS + ONE_L_IN_PIXELS / 4);
        }
        if (flip != null) {
            setPivots();
            flip.setPivotX(pivotX);
            flip.setPivotY(pivotY);
            flip.setAngle(((FlipperGizmo) gizmo).getOldAngle());
        }
    }
}
