package controller.handlers.boardhandlers;

import controller.BoardController;
import controller.BuildController;
import javafx.event.Event;
import javafx.scene.input.MouseEvent;
import model.GizmoballModel;
import model.board.BoardObjectType;
import model.board.gizmos.*;
import view.gizmoviews.*;

import static util.Constants.ONE_L_IN_PIXELS;

public class AddHandler implements BoardHandler {
    protected final GizmoballModel model;
    protected final BoardController boardController;
    protected final BuildController buildController;
    protected final BoardObjectType type;

    public AddHandler(GizmoballModel model, BoardController boardController, BuildController buildController, BoardObjectType type) {
        this.model = model;
        this.boardController = boardController;
        this.buildController = buildController;
        this.type = type;
    }

    @Override
    public void handle(Event event) {

        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            MouseEvent mouseEvent = (MouseEvent) event;

            double x = (int)(mouseEvent.getX()/ONE_L_IN_PIXELS);
            double y = (int)(mouseEvent.getY()/ONE_L_IN_PIXELS);

            if (model.addGizmo(x,y,"", type)) {
                Gizmo gizmo = model.getGizmo(x,y);
                System.out.println(gizmo);
                GizmoView gizmoView;
                switch(type){
                    case CIRCLE:
                        gizmoView = new CircleGizmoView(gizmo);
                        break;
                    case TRIANGLE:
                        gizmoView = new TriangleGizmoView(gizmo);
                        break;
                    case SQUARE:
                        gizmoView = new SquareGizmoView(gizmo);
                        break;
                    case LEFT_FLIPPER:
                        gizmoView = new FlipperGizmoView(gizmo);
                        break;
                    case RIGHT_FLIPPER:
                        gizmoView = new FlipperGizmoView(gizmo);
                        break;
                    default:
                        gizmoView = null;
                        break;
                }


                boardController.addToBoardView(gizmoView);
            }
            if(!model.getErrorMessage().equals("")){
                buildController.setInformation(model.getErrorMessage());
                model.setMessage("");
            } else {
                buildController.setInformation(" ");
            }
        }
    }

    public void handle(String name) {
        Gizmo gizmo = model.getGizmo(name);
        System.out.println(gizmo);
        GizmoView gizmoView;
        switch(type){
            case CIRCLE:
                gizmoView = new CircleGizmoView(gizmo);
                break;
            case TRIANGLE:
                gizmoView = new TriangleGizmoView(gizmo);
                break;
            case SQUARE:
                gizmoView = new SquareGizmoView(gizmo);
                break;
            case LEFT_FLIPPER:
                gizmoView = new FlipperGizmoView(gizmo);
                break;
            case RIGHT_FLIPPER:
                gizmoView = new FlipperGizmoView(gizmo);
                break;
            default:
                gizmoView = null;
                break;
        }

        boardController.addToBoardView(gizmoView);
        if(!model.getErrorMessage().equals("")){
            buildController.setInformation(model.getErrorMessage());
            model.setMessage("");
        } else {
            buildController.setInformation(" ");
        }

    }
}
