package controller.handlers.generalhandlers;

import controller.BuildController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import util.BoardState;

import java.io.File;
import java.util.Optional;

public class ExitHandler implements EventHandler<ActionEvent> {
    private Stage stage;
    private BuildController controller;
    private SaveHandler saveHandler;
    public ExitHandler(BuildController controller) {
        saveHandler = new SaveHandler(stage, controller);
    }

    @Override
    public void handle(ActionEvent event) {
        if (BoardState.getSavedBoard()) {
            System.exit(0);
        } else {
            ButtonType yes = new ButtonType("Yes");
            ButtonType no = new ButtonType("No");
            ButtonType cancel = new ButtonType("Cancel");

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you wish to save the layout before exiting?", yes, no, cancel);
            alert.setTitle("Board Layout Not Saved");
            alert.setHeaderText("Board layout has not been saved.");
            Window window = alert.getDialogPane().getScene().getWindow();
            window.setOnCloseRequest(event1 -> window.hide());
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent()){
                if (result.get() == yes) {
                    saveHandler.handle(event);
                    System.exit(0);
                }
                else if (result.get() == no) {
                    System.exit(0);
                }
                else {
                    event.consume();
                }

            }

        }
    }
}
