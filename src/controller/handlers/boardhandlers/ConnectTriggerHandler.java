package controller.handlers.boardhandlers;

import controller.BoardController;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.GizmoballModel;
import model.board.gizmos.Gizmo;
import util.KeyPress;
import util.Triggers;
import view.gizmoviews.GizmoView;

import java.util.Optional;

import static util.Constants.ONE_L_IN_PIXELS;

public class ConnectTriggerHandler implements BoardHandler {
    private final GizmoballModel model;
    private final ToggleButton connectButton;
    private final BoardController boardController;
    private final Stage stage;
    private boolean triggeredSelected;
    private Gizmo triggeredGizmo;
    private final Label infoLabel;
    private GizmoView gizmoView;

    public ConnectTriggerHandler(GizmoballModel model, BoardController boardController, Stage stage, Label infoLabel, ToggleButton connectButton) {
        this.model = model;
        this.connectButton = connectButton;
        triggeredSelected = false;
        this.boardController = boardController;
        this.stage = stage;
        this.infoLabel = infoLabel;
        connectButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (gizmoView != null) {
                    gizmoView.setSelected(false);
                }
            }
        });
    }

    @Override
    public void handle(Event event) {

        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            Node clicked = ((Node)event.getTarget());
            double nodeX = Math.floor(clicked.getTranslateX()/ONE_L_IN_PIXELS);
            double nodeY = Math.floor(clicked.getTranslateY()/ONE_L_IN_PIXELS);

            Gizmo gizmo = model.getGizmo(nodeX, nodeY);

            if (!triggeredSelected) {
                if (gizmo != null) {
                    triggeredSelected = true;
                    triggeredGizmo = gizmo;
                    boardController.getBoardView().requestFocus();
                    infoLabel.setText(triggeredGizmo.getName() + " selected. Please select another gizmo or press a key to connect to.");
                    gizmoView = (GizmoView) clicked;
                    gizmoView.setSelected(true);
                }
            }
            else {
                if (gizmo != null) {
                    triggeredSelected = false;
                    Triggers.addTrigger(gizmo, triggeredGizmo);
                    infoLabel.setText(triggeredGizmo.getName() + " and " + gizmo.getName() + " have been connected.");
                    gizmoView.setSelected(false);
                    gizmoView = null;
                    connectButton.requestFocus();
                }
            }
        }
        else if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            KeyEvent keyEvent = (KeyEvent) event;
            KeyCode keyCode = keyEvent.getCode();

            infoLabel.setText("");

            if (keyCode != KeyCode.ESCAPE) {
                if (triggeredSelected){

                    ButtonType down = new ButtonType("Press key");
                    ButtonType up = new ButtonType("Release key");

                    Alert keyTypeAlert = new Alert(Alert.AlertType.NONE, "Please select if you want the action to be triggered when you press the key or release the key!", down, up);
                    keyTypeAlert.setTitle("Select when trigger should occur");
                    keyTypeAlert.initStyle(StageStyle.TRANSPARENT);

                    stage.getScene().getRoot().setEffect(new GaussianBlur(50));

                    keyTypeAlert.getDialogPane().getScene().getStylesheets().add("view/css/styles.css");

                    Optional<ButtonType> result = keyTypeAlert.showAndWait();
                    result.ifPresent(buttonType -> {
                        if (buttonType == down) {
                            Triggers.addTrigger(new KeyPress(keyCode, KeyEvent.KEY_PRESSED), triggeredGizmo);
                        }

                        Triggers.addTrigger(new KeyPress(keyCode, KeyEvent.KEY_RELEASED), triggeredGizmo);
                    });

                    stage.getScene().getRoot().setEffect(null);

                    infoLabel.setText(keyCode + " and " + triggeredGizmo.getName() + " have been connected.");
                }
            }
            triggeredSelected = false;
            gizmoView.setSelected(false);
            gizmoView = null;
            connectButton.requestFocus();
        }
    }
}
