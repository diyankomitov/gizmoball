package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PlayController {

    public Button switchButton;

    public PlayController() {

    }

    @FXML
    public void initialize() {

    }

    public void setSwitchHandler(SwitchModeHandler switchToBuild) {
        switchButton.setOnAction(switchToBuild);
    }
}
