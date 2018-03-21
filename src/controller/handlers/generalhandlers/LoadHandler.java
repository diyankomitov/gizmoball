package controller.handlers.generalhandlers;

import controller.BoardController;
import controller.BuildController;
import controller.handlers.boardhandlers.AddAbsorberHandler;
import controller.handlers.boardhandlers.AddBallHandler;
import controller.handlers.boardhandlers.AddHandler;
import controller.handlers.boardhandlers.ClearBoardHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import model.GizmoballModel;
import model.board.Ball;
import model.board.BoardObjectType;
import model.board.gizmos.Gizmo;
import physics.Vect;
import util.BoardState;
import util.KeyPress;
import util.Triggers;

import java.io.*;
import java.util.Optional;

public class LoadHandler implements EventHandler<ActionEvent> {
    private final Stage stage;
    private final GizmoballModel model;
    private final BoardController boardController;
    private final BuildController buildController;
    private final SaveHandler saveHandler;
    private String errorMessages = "";
    private int errorCount = 0;
    private final Label infoLabel;

    private ActionEvent event;

    public LoadHandler(Stage stage, BoardController boardController, BuildController buildController, GizmoballModel model, Label infoLabel) {
        this.boardController = boardController;
        this.buildController = buildController;
        this.infoLabel = infoLabel;
        this.stage = stage;
        this.model = model;
        saveHandler = new SaveHandler(stage, buildController);
    }

    @Override
    public void handle(ActionEvent event) {

        this.event = event;
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter eFilter = new FileChooser.ExtensionFilter("Gizmoball files (*.gzb)", "*.gzb");
        FileChooser.ExtensionFilter eFilter2 = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");

        fileChooser.getExtensionFilters().add(eFilter);
        fileChooser.getExtensionFilters().add(eFilter2);
        fileChooser.setSelectedExtensionFilter(eFilter);
        if (BoardState.getSavedBoard()) {
            fileChooser.setTitle("Load Board");
            File file = fileChooser.showOpenDialog(stage);

            if(file!=null) {
                loadGame(file);
            }
        } else {
            ButtonType yes = new ButtonType("Yes");
            ButtonType no = new ButtonType("No");
            ButtonType cancel = new ButtonType("Cancel");

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you wish to save the layout before loading a new one?", yes, no, cancel);
            alert.setTitle("Board Layout Not Saved");
            alert.setHeaderText("Board layout has not been saved.");
            alert.initStyle(StageStyle.TRANSPARENT);
            stage.getScene().getRoot().setEffect(new GaussianBlur(50));
            alert.getDialogPane().getScene().getStylesheets().add("view/css/styles.css");

            Window window = alert.getDialogPane().getScene().getWindow();
            window.setOnCloseRequest(event1 -> window.hide());
            Optional<ButtonType> result = alert.showAndWait();
            result.ifPresent(buttonType -> {
                if (buttonType == yes) {
                    saveHandler.handle(event);
                    fileChooser.setTitle("Load Board");
                    File file = fileChooser.showOpenDialog(stage);

                    if (file != null) {
                        loadGame(file);
                    }
                } else if (buttonType == no) {
                    fileChooser.setTitle("Load Board");
                    File file = fileChooser.showOpenDialog(stage);

                    if (file != null) {
                        loadGame(file);
                    }
                } else {
                    event.consume();
                }
            });

            stage.getScene().getRoot().setEffect(null);
        }

    }

    private void clearBoard() {
        new ClearBoardHandler(boardController, model, infoLabel).handle(event);
    }

    public void loadGame(File file) {
        try {
            clearBoard();

            errorCount = 0;
            errorMessages = "";

            FileReader fileReader  = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int j = 0;

            String stringLine;
            String boardDetailStr[];

            while((stringLine = bufferedReader.readLine())!=null) {
                if(!stringLine.equals("")) {
                    boardDetailStr = stringLine.split(" ");

                    checkAction(boardDetailStr);

                }
            }

            for (Gizmo gizmo : model.getGizmos()) {
                if (gizmo.getType() == BoardObjectType.ABSORBER) {
                    new AddAbsorberHandler(model, boardController, buildController, infoLabel).handle(gizmo.getName());
                }
                else {
                    new AddHandler(model, boardController, buildController, gizmo.getType(), infoLabel).handle(gizmo.getName());
                }
            }
            for (Ball ball : model.getBalls()) {
                if (ball != null) {
                    new AddBallHandler(model, boardController, buildController, infoLabel).handle(ball.getName());
                }
            }
            if(errorMessages.equals("")) {
                infoLabel.setText("Board loaded successfully.");
                BoardState.setSavedBoard(true);
            }
            else {
                infoLabel.setText(errorMessages);
                BoardState.setSavedBoard(true);
            }
        } catch (FileNotFoundException e) {
            infoLabel.setText("Error: No file found.");
            System.err.println("Error: No file found." + e);
        } catch(IOException e ) {
            infoLabel.setText("Error: Wrong file format.");
            System.err.println("Error: Wrong file format.");
        }
    }

    private void checkAction(String[] string){

        double x;
        double y;
        double x2;
        double y2;
        switch(string[0]) {
            case "Triangle":
                try {
                    x = Double.parseDouble(string[2]);
                    y = Double.parseDouble(string[3]);
                    if(!model.addGizmo(x, y, string[1], BoardObjectType.TRIANGLE)){
                        errorHandler(String.join(" ", string));
                    }
                } catch(Exception e){
                    System.err.println("Something has gone wrong...");
                    errorHandler(String.join(" ", string));
                }
                break;
            case "Square":
                try {
                    x = Double.parseDouble(string[2]);
                    y = Double.parseDouble(string[3]);
                    if(!model.addGizmo(x, y, string[1], BoardObjectType.SQUARE)){
                        errorHandler(String.join(" ", string));
                    }

                } catch(Exception e){
                    System.err.println("Something has gone wrong...");
                    errorHandler(String.join(" ", string));
                }
                break;
            case "Circle":
                try {
                    x = Double.parseDouble(string[2]);
                    y = Double.parseDouble(string[3]);
                    if(!model.addGizmo(x, y, string[1], BoardObjectType.CIRCLE)){
                        errorHandler(String.join(" ", string));
                    }
                } catch(Exception e){
                    System.err.println("Something has gone wrong...");
                    errorHandler(String.join(" ", string));
                }
                break;
            case "LeftFlipper":
                try {
                    x = Double.parseDouble(string[2]);
                    y = Double.parseDouble(string[3]);
                    if(!model.addGizmo(x, y, string[1], BoardObjectType.LEFT_FLIPPER)){
                        errorHandler(String.join(" ", string));
                    }
                } catch(Exception e){
                    System.err.println("Something has gone wrong...");
                    errorHandler(String.join(" ", string));
                }
                break;
            case "RightFlipper" :
                try {
                    x = Double.parseDouble(string[2]);
                    y = Double.parseDouble(string[3]);
                    if(!model.addGizmo(x, y, string[1], BoardObjectType.RIGHT_FLIPPER)){
                        errorHandler(String.join(" ", string));
                    }
                } catch(Exception e){
                    System.err.println("Something has gone wrong...");
                    errorHandler(String.join(" ", string));
                }
                break;
            case "Absorber" :
                try {
                    x = Double.parseDouble(string[2]);
                    y = Double.parseDouble(string[3]);
                    x2 = Double.parseDouble(string[4]);
                    y2 = Double.parseDouble(string[5]);
                    if(!model.addAbsorber(x, y, x2, y2, string[1])){
                        errorHandler(String.join(" ", string));
                    }
                } catch(Exception e){
                    System.err.println("Something has gone wrong...");
                    errorHandler(String.join(" ", string));
                }
                break;
            case "Ball":
                try {
                    x = Double.parseDouble(string[2]);
                    y = Double.parseDouble(string[3]);
                    x2 = Double.parseDouble(string[4]);
                    y2 = Double.parseDouble(string[5]);
                    if(!model.addBall(x, y, x2, y2, string[1])){
                        errorHandler(String.join(" ", string));
                    }
                } catch(Exception e){
                    System.err.println("Something has gone wrong...");
                    errorHandler(String.join(" ", string));
                }
                break;
            case "Delete":
                if(string[1].charAt(0)=='B') {
                    if(!model.removeBall(string[1])){
                        errorHandler(String.join(" ", string));
                    }
                }
                if(!model.removeGizmo(string[1])){
                    errorHandler(String.join(" ", string));
                }

                break;
            case "Rotate":
                try{
                    if(!model.rotateGizmo(string[1])){
                        errorHandler(String.join(" ", string));
                    }

                } catch(Exception e){
                    System.err.println("Something has gone wrong...");
                    errorHandler(String.join(" ", string));
                }

                break;
            case "Move":
                try {
                    x = Double.parseDouble(string[2]);
                    y = Double.parseDouble(string[3]);

                    if (!model.moveBall(string[1], x, y)) {
                        if (!model.moveGizmo(string[1], x , y)) {
                            errorHandler(String.join(" ", string));
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Something has gone wrong...");
                    errorHandler(String.join(" ", string));
                }
                break;
            case "KeyConnect":
                try{

                    if (!string[1].equals("key")) {
                        errorHandler(String.join(" ", string));
                        break;
                    }

                    String toMatch = String.join(" ", string);
                    String keyName = toMatch.split("\"")[1];
                    int keyLength = keyName.split(" ").length;

                    KeyCode keyCode = KeyCode.getKeyCode(keyName);

                    if (keyCode == null) {
                        errorHandler(String.join(" ", string));
                        break;
                    }

                    String modifier = string[2+keyLength];
                    EventType<KeyEvent> eventType;


                    if (modifier.equals("up")) {
                        eventType = KeyEvent.KEY_RELEASED;
                    }
                    else if (modifier.equals("down")) {
                        eventType = KeyEvent.KEY_PRESSED;
                    }
                    else {
                        errorHandler(String.join(" ", string));
                        break;
                    }

                    Gizmo gizmo = model.getGizmo(string[3+keyLength]);

                    if (gizmo == null) {
                        errorHandler(String.join(" ", string));
                        break;
                    }

                    Triggers.addTrigger(new KeyPress(keyCode, eventType), gizmo);
                } catch(Exception e){
                    System.err.println("Something has gone wrong...");
                    errorHandler(String.join(" ", string));
                }
                break;
            case "Connect":
                try{
                    Triggers.addTrigger(model.getGizmo(string[1]), model.getGizmo(string[2]));
                } catch(Exception e) {
                    System.err.println("Something has gone wrong...");
                    errorHandler(String.join(" ", string));
                }
                break;
            case "Gravity":
                try {
                    double g = Double.parseDouble(string[1]);
                    model.setGravity(g);

                } catch (Exception e) {
                    System.err.println("Something has gone wrong...");
                    errorHandler(String.join(" ", string));
                }
                break;
            case "Friction":
                try {
                    double mu = Double.parseDouble(string[1]);
                    double mu2 = Double.parseDouble(string[2]);
                    model.setFriction(mu, mu2);

                } catch (Exception e) {
                    System.err.println("Something has gone wrong...");
                    errorHandler(String.join(" ", string));
                }
                break;
            case "Velocity":
                try {
                    String name = string[1];
                    double vx = Double.parseDouble(string[2]);
                    double vy = Double.parseDouble(string[3]);
                    boolean global = false;

                    if (string.length > 4) {
                        if (!string[4].equals("global")) {
                            errorHandler(String.join(" ", string));
                            break;
                        }
                        else {
                            global = true;
                        }
                    }

                    model.getBall(name).setGlobalVelocity(global);
                    model.getBall(name).setVelocity(new Vect(vx, vy));

                } catch (Exception e) {
                    System.err.println("Something has gone wrong... " + e);
                    e.printStackTrace();
                    errorHandler(String.join(" ", string));
                }
                break;
            default:
                errorHandler(String.join(" ", string));
                break;
        }
    }

    private void errorHandler(String s){
        errorMessages = "Something in the file was not in the recognised format at: (" + s + ")";
        if(errorCount == 1){
            errorMessages += "+ 1 other issue";
        } else if (errorCount > 1){
            errorMessages += " + " + errorCount + " other issues";
        }
        errorCount++;
    }
}
