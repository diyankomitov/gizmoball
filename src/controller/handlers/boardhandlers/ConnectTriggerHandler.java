package controller.handlers.boardhandlers;

import controller.BoardController;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.GizmoballModel;
import model.board.gizmos.Gizmo;
import util.KeyPress;
import util.Triggers;

import java.util.Optional;

import static util.Constants.ONE_L_IN_PIXELS;

public class ConnectTriggerHandler implements BoardHandler {
    private final GizmoballModel model;
    private final BoardController boardController;
    private final Stage stage;
    private boolean triggeredSelected;
    private Gizmo triggeredGizmo;

    public ConnectTriggerHandler(GizmoballModel model, BoardController boardController, Stage stage) {
        this.model = model;
        triggeredSelected = false;
        this.boardController = boardController;
        this.stage = stage;
    }

    @Override
    public void handle(Event event) {

        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            MouseEvent mouseEvent = (MouseEvent) event;
            double x = Math.floor(mouseEvent.getX() / ONE_L_IN_PIXELS);
            double y = Math.floor(mouseEvent.getY() / ONE_L_IN_PIXELS);

            Gizmo gizmo = model.getGizmo(x, y);

            if (!triggeredSelected) {
                if (gizmo != null) {
                    triggeredSelected = true;
                    triggeredGizmo = gizmo;
                    boardController.getBoardView().requestFocus();
                }
            }
            else {
                if (gizmo != null) {
                    triggeredSelected = false;
                    Triggers.addTrigger(gizmo, triggeredGizmo);
                }
                else {
                    boardController.getBoardView().requestFocus();
                }
            }
        }
        else if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            KeyEvent keyEvent = (KeyEvent) event;
            KeyCode keyCode = keyEvent.getCode();

            if (keyCode != KeyCode.ESCAPE) {
                if (triggeredSelected){

                    ButtonType down = new ButtonType("Press key");
                    ButtonType up = new ButtonType("Release key");

                    Alert keyTypeAlert = new Alert(Alert.AlertType.NONE, "Please select if you want the action to be triggered when you press the key or release the key!", down, up);
                    keyTypeAlert.setTitle("Select when trigger should occur");

//                    EventType<KeyEvent> type = null;

                    Optional<ButtonType> result = keyTypeAlert.showAndWait();
                    if (result.isPresent()) {
                        if (result.get() == down) {
                            Triggers.addTrigger(new KeyPress(keyCode, KeyEvent.KEY_PRESSED), triggeredGizmo);
                        }

                        Triggers.addTrigger(new KeyPress(keyCode,KeyEvent.KEY_RELEASED), triggeredGizmo);
                    }

                }
            }
            triggeredSelected = false;
        }
    }
}
