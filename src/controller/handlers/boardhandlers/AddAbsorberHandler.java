package controller.handlers.boardhandlers;

import controller.BoardController;
import controller.BuildController;
import javafx.event.Event;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.GizmoballModel;
import model.board.BoardObjectType;
import model.board.gizmos.Gizmo;
import view.gizmoviews.AbsorberGizmoView;


import static util.Constants.ONE_L_IN_PIXELS;

public class AddAbsorberHandler extends AddHandler {

    private boolean firstClick;
    private AbsorberGizmoView absorberView;
    private double xpos;
    private double ypos;

    public AddAbsorberHandler(GizmoballModel model, BoardController boardController, BuildController buildController, Label infoLabel) {
        super(model, boardController, buildController, BoardObjectType.ABSORBER, infoLabel);
        firstClick = false;
        xpos = 0;
        ypos = 0;
    }

    @Override
    public void handle(Event event) {

        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            MouseEvent mouseEvent = (MouseEvent) event;
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();

            if (!firstClick) {
                firstClick = true;
                absorberView = new AbsorberGizmoView();

                xpos = ((int) (x / ONE_L_IN_PIXELS)) * ONE_L_IN_PIXELS;
                ypos = ((int) (y / ONE_L_IN_PIXELS)) * ONE_L_IN_PIXELS;
                absorberView.setCoordinates(xpos, ypos);
                boardController.addToBoardView(absorberView);

            } else {
                firstClick = false;

                if (x < xpos) {
                    double tempx = x;
                    x = xpos;
                    xpos = tempx;
                }

                if (y < ypos) {
                    double tempy = y;
                    y = ypos;
                    ypos = tempy;
                }

                double x1 = Math.floor(xpos / ONE_L_IN_PIXELS);
                double y1 = Math.floor(ypos / ONE_L_IN_PIXELS);
                double x2 = Math.ceil(x / ONE_L_IN_PIXELS);
                double y2 = Math.ceil(y / ONE_L_IN_PIXELS);

                boardController.removeFromBoardView(absorberView);
                if (model.addAbsorber(x1, y1, x2, y2, "")) {
                    boardController.addToBoardView(new AbsorberGizmoView(model.getGizmo(x1, y1)));
                }
            }
            if(!model.getErrorMessage().equals("")){
                buildController.setInformation(model.getErrorMessage());
                model.setMessage("");
            } else {
                buildController.setInformation(" ");
            }

        }

        if (event.getEventType() == MouseEvent.MOUSE_MOVED) {
            if (firstClick) {
                MouseEvent mouseEvent = (MouseEvent) event;
                double x = mouseEvent.getX();
                double y = mouseEvent.getY();

                if (x >= xpos) {
                    absorberView.setTranslateX(xpos);
                    double width = x - xpos;
                    absorberView.setWidth(width);
                } else {
                    absorberView.setTranslateX(x);
                    absorberView.setWidth(xpos - x);
                }

                if (y >= ypos) {
                    absorberView.setTranslateY(ypos);
                    double height = y - ypos;
                    absorberView.setHeight(height);
                } else {
                    absorberView.setTranslateY(y);
                    absorberView.setHeight(ypos - y);
                }
            }

        }

    }

    @Override
    public void handle(String name) {
        Gizmo absorber = model.getGizmo(name);
        boardController.addToBoardView(new AbsorberGizmoView(absorber));
        if(!model.getErrorMessage().equals("")){
            buildController.setInformation(model.getErrorMessage());
            model.setMessage("");
        } else {
            buildController.setInformation(" ");
        }
    }
}
