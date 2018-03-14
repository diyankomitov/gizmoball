package view.gizmoviews;

import javafx.beans.NamedArg;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import model.board.BoardObjectType;
import model.board.gizmos.FlipperGizmo;
import model.board.gizmos.Gizmo;

import static javafx.scene.paint.Color.ORANGE;
import static util.Constants.ONE_L_IN_PIXELS;

public class FlipperGizmoView extends GizmoViewContainer{

    private double pivotX;
    private double pivotY;
    private Rotate flip;
    private Rectangle rectangle;
    private FlipperGizmo flipper;

    public FlipperGizmoView(Gizmo flipperGizmo) {
        super(flipperGizmo);

        flipper = (FlipperGizmo) flipperGizmo;
        double width = ONE_L_IN_PIXELS/2;
        double length = ONE_L_IN_PIXELS*2;

        rectangle = new Rectangle(width, length);
        rectangle.setArcWidth(width);
        rectangle.setArcHeight(width);
        rectangle.setFill(ORANGE);

        if (flipperGizmo.getType() == BoardObjectType.RIGHT_FLIPPER) {
            rectangle.setX(length-width);
        }

        this.getChildren().add(rectangle);

        setPivots();
        flip = new Rotate(0, pivotX, pivotY);
        this.getTransforms().add(flip);
    }

    private void setPivots() {
        pivotX = (flipper.getxWithOffset() - flipper.getX()) * ONE_L_IN_PIXELS + ONE_L_IN_PIXELS/4;
        pivotY = (flipper.getyWithOffset() - flipper.getY()) * ONE_L_IN_PIXELS + ONE_L_IN_PIXELS/4;
    }

    public FlipperGizmoView(@NamedArg("type") BoardObjectType type) {
        super(null);

        double width = ONE_L_IN_PIXELS/2;
        double length = ONE_L_IN_PIXELS*2;

        Rectangle rectangle = new Rectangle(width, length);
        rectangle.setArcWidth(width);
        rectangle.setArcHeight(width);
        rectangle.setFill(ORANGE);

        if (type == BoardObjectType.RIGHT_FLIPPER) {
            rectangle.setX(length-width);
        }

        this.getChildren().add(rectangle);


//        this(null);
//        square.setStyle("-fx-stroke: red; -fx-stroke-type: inside; -fx-stroke-width: 2");
    }

    @Override
    public void update() {
        super.update();
        if(rectangle != null) {
            FlipperGizmo flipper = (FlipperGizmo) gizmo;
            rectangle.setX((flipper.getxWithOffset() - flipper.getX()) *ONE_L_IN_PIXELS);
            rectangle.setY((flipper.getyWithOffset() - flipper.getY()) *ONE_L_IN_PIXELS);
        }
        if (flip != null) {
            setPivots();
            flip.setPivotX(pivotX);
            flip.setPivotY(pivotY);
            flip.setAngle(gizmo.getAngle());
        }
    }
}