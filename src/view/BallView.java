package view;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Group;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.board.Ball;
import util.Observer;

import static util.Constants.ONE_L_IN_PIXELS;


public class BallView extends Group implements Observer, Toggle{

    private Circle ball;
    private double x;
    private double y;
    private final Ball ballModel;
    private double hue;
    private BooleanProperty selected;
    private ObjectProperty<ToggleGroup> toggleGroup;
    private boolean partyTime;

    public BallView(Ball ballModel) {
        super();
        this.x = ballModel.getX() * ONE_L_IN_PIXELS;
        this.y = ballModel.getY() * ONE_L_IN_PIXELS;
        this.ballModel = ballModel;
        double radius = ONE_L_IN_PIXELS/4;
        ball = new Circle(radius);
        this.getChildren().add(ball);
        ball.getStyleClass().add("ball");
        this.setTranslateX(x);
        this.setTranslateY(y);
        ballModel.subscribe(this);
        this.getStyleClass().add("ballContainer");

        selected = new SimpleBooleanProperty(false);
        toggleGroup = new SimpleObjectProperty<>(null);
    }

    public BallView() {
        this.x = 0;
        this.y = 0;
        this.ballModel = null;
        double radius = ONE_L_IN_PIXELS/4;
        ball = new Circle(radius);
        this.getChildren().add(ball);
        ball.getStyleClass().add("ball");
        this.getStyleClass().add("ballContainer");
        selected = new SimpleBooleanProperty(false);
        toggleGroup = new SimpleObjectProperty<>(null);
    }

    @Override
    public void update() {
        this.setTranslateX(ballModel.getX() * ONE_L_IN_PIXELS);
        this.setTranslateY(ballModel.getY() * ONE_L_IN_PIXELS);
        if (hue == 360) {
            hue = 0;
        }
        if (partyTime) {
            ball.setFill(Color.hsb(hue,1.0,1.0));
        }
        hue++;
        this.toBack();
    }

    public void setPartyTime(boolean partyTime) {
        this.partyTime = partyTime;
    }

//    public void setSelected(boolean selected) {
//        if (selected) {
//            this.getStyleClass().add("selected");
//        }
//        else {
//            this.getStyleClass().remove("selected");
//        }
//    }

    @Override
    public ToggleGroup getToggleGroup() {
        return toggleGroup.getValue();
    }

    @Override
    public void setToggleGroup(ToggleGroup toggleGroup) {
        this.toggleGroup.setValue(toggleGroup);
    }

    @Override
    public ObjectProperty<ToggleGroup> toggleGroupProperty() {
        return toggleGroup;
    }

    @Override
    public boolean isSelected() {
        return selected.get();
    }

    @Override
    public void setSelected(boolean selected) {
        System.out.println("setSelected");
        if (selected) {
            System.out.println("selected");
            this.getStyleClass().add("selected");
        }
        else {
            System.out.println("not Selected");
            this.getStyleClass().remove("selected");
        }
        this.selected.setValue(selected);
    }

    @Override
    public BooleanProperty selectedProperty() {
        return selected;
    }
}
