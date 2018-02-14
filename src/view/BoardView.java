package view;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import model.Ball;
import model.Flipper;
import model.Gizmo;
import model.GizmoballModel;
import util.Constants;
import util.Observer;

import java.util.Arrays;

public class BoardView extends GridPane implements Observer{
    private int curCol;
    private int curRow;
    private Pane[][] cells;
    private BallView ballView;
    private GizmoballModel model;
    private double cellSize;
    private boolean isGridOn;


    public BoardView() {    //TODO: Refactor
        this.cellSize = Constants.ONE_L_IN_PIXELS;
        this.setPrefSize(cellSize*20, cellSize*20);
        this.setMinSize(cellSize*20, cellSize*20);
        this.setMaxSize(cellSize*20, cellSize*20);
//        this.setStyle("-fx-background-color: white, gray; -fx-background-insets: 0, 1 1 0 0 ; -fx-padding: 1;");

        cells = new Pane[20][20];

        for (int y = 0; y<20; y++) {
            for (int x = 0; x<20; x++) {
                Pane cell = new Pane();
                cell.getStyleClass().addAll("boardCell","grid");
//                cell.setStyle("-fx-border-color: white; -fx-background-insets: 0, 0 0 1 1 ;");
                cell.setPrefSize(cellSize, cellSize);
                cell.setOnMouseDragReleased(event -> {
                    curCol = getColumnIndex(cell);
                    curRow = getRowIndex(cell);
                });
                cells[y][x] = cell;
                this.add(cell, x, y);
            }
        }
        isGridOn = true;

    }

    public void toggleGrid() {
        for (Pane[] cell : cells) {
            for (Pane pane : cell) {
                pane.getStyleClass().set(1, isGridOn ? "noGrid" : "grid");
            }
        }
        isGridOn = !isGridOn;
    }

    public void addBall(Ball ball) {
       int x =(int) ball.getX();
        int y = (int) ball.getY();
        this.add(new BallView(), x, y);
    }

    public void addGizmo(Gizmo gizmo){
        int x = (int) gizmo.getXCoord();
        int y = (int) gizmo.getYCoord();

            switch(gizmo.getType()){
                case CIRCLE:
                   this.add(new CircularBumperView(), x, y);
                   break;
                case TRIANGLE:
                    this.add(new TriangularBumperView(), x, y);
                    break;
                case SQUARE:
                    this.add(new SquareBumperView(), x, y);
                    break;
                case LEFT_FLIPPER:
                    FlipperView flipperLeft = new FlipperView((Flipper)gizmo);
                    this.add(flipperLeft, x, y, 1, 2);

                    break;
                case RIGHT_FLIPPER:
                    FlipperView flipperRight = new FlipperView((Flipper)gizmo);
                    this.add(flipperRight, x, y, 1, 2);
                    flipperRight.setX();

            }
    }

    public void removeGizmo() {
        //todo remove gizmo from view
    }

    public void clearBoard() {
        this.getChildren().clear();
        cells = new Pane[20][20];

        for (int y = 0; y<20; y++) {
            for (int x = 0; x<20; x++) {
                Pane cell = new Pane();
                cell.getStyleClass().addAll("boardCell","grid");
//                cell.setStyle("-fx-border-color: white; -fx-background-insets: 0, 0 0 1 1 ;");
                cell.setPrefSize(cellSize, cellSize);
                cell.setOnMouseDragReleased(event -> {
                    curCol = getColumnIndex(cell);
                    curRow = getRowIndex(cell);
                });
                cells[y][x] = cell;
                this.add(cell, x, y);
            }
        }
    }

    public int getCurRow() {
        return curRow;
    }

    public int getCurCol() {
        return curCol;
    }

//    public void setModel(GizmoballModel model) {
//        this.model = model;
//        model.subscribe(this);
//        ballView.setTranslateX(model.getBall().getX() * cellSize);
//        ballView.setTranslateY(model.getBall().getY() * cellSize);
//    }

    @Override
    public void update() {
        ballView.setTranslateX(model.getBall().getX() * cellSize);
        ballView.setTranslateY(model.getBall().getY() * cellSize);

    }
}
