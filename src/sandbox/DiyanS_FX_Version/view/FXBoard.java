package view;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.Ball;
import model.Model;
import model.VerticalLine;
import sandbox.DiyanS_FX_Version.model.LFlipper;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Diyan's Implementation of Murray's Physics demo in JavaFX
 */

//FXBoard is a pane
public class FXBoard extends Pane implements Observer {

    private static final long serialVersionUID = 1L;

    //LH flippers
    private LFlipper LFlip;

    private Ball b;
    private Circle circle;
    protected int width;
    protected int height;
    protected Model gm;

    public FXBoard(int w, int h, Model m) {
        // Observe changes in Model
        m.addObserver(this);
        width = w;
        height = h;
        gm = m;
        this.setStyle("-fx-border-color: black"); //set borders to black
        this.setPrefSize(width, height);    //set size to widht, height
        this.setMinSize(width, height);
        this.setMaxSize(width, height);

        for (VerticalLine vl : gm.getLines()) {
            //Create rectangle with given parameters, shows as a line
            Rectangle rectangle = new Rectangle(vl.getX(), vl.getY(), vl.getWidth(), 1);
            //add the rectangle to this, aka to the board
            this.getChildren().add(rectangle);
        }

        //get model ball
        b = gm.getBall();
        if (b != null) {
            //get x, y and radius for model ball
            double x = b.getExactX();
            double y = b.getExactY();
            double radius = b.getRadius();
            //make new circle with parameters given in model
            circle = new Circle(x, y, radius, Color.BLUE);
            //add circle to this pane
            this.getChildren().add(circle);
        }

        //LH get model flipper


    }

    @Override
    public void update(Observable arg0, Object arg1) {
        //when the model is updated set x and y coordinates of circle to x and y coordinates of model ball
        circle.setCenterX(b.getExactX());
        circle.setCenterY(b.getExactY());
    }

}
