package controller.handlers.generalhandlers;

import controller.BoardController;
import controller.handlers.boardhandlers.AddAbsorberHandler;
import controller.handlers.boardhandlers.AddBallHandler;
import controller.handlers.boardhandlers.AddHandler;
import controller.handlers.boardhandlers.ClearBoardHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.board.Ball;
import model.board.BoardObjectType;
import model.GizmoballModel;
import model.board.gizmos.Gizmo;
import view.gizmoviews.TriangleGizmoView;

import java.io.*;

public class LoadHandler implements EventHandler<ActionEvent> {
    private Stage stage;
    private GizmoballModel model; //Alistair thinks it might needs passed
    private BoardController boardController;
    private ActionEvent event;

    public LoadHandler(Stage stage, BoardController boardController, GizmoballModel model) {
        this.boardController = boardController;

        this.stage = stage;
        this.model = model;
    }

    //TODO add key connects
    //TODO add default options if input is incorrect ie missing params or wrong name/type
    //TODO add gravity and friction???
    @Override
    public void handle(ActionEvent event) {
        this.event = event;
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter eFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(eFilter);
        fileChooser.setSelectedExtensionFilter(eFilter);

        fileChooser.setTitle("Load Board");
        File file = fileChooser.showOpenDialog(stage);

        if(file!=null) {
            loadGame(file);
        }
    }

    public void clearBoard() {
        new ClearBoardHandler(boardController, model).handle(event);
    }

    public void loadGame(File file) {
        try {
            System.out.println("At top of loadGame");

            clearBoard();

            FileReader fileReader  = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int j = 0;

            String stringLine;
            String boardDetailStr[];

            while((stringLine = bufferedReader.readLine())!=null) {
                if(!stringLine.equals("")) {
                    boardDetailStr = stringLine.split(" ");
                    for(int i=0; i<boardDetailStr.length; i++){
                        System.out.println("string array at position: "+ i +" contains: "+boardDetailStr[i]);
                    }

                    checkAction(boardDetailStr);

                }
            }

            for (Gizmo gizmo : model.getGizmos()) {
                if (gizmo.getType() == BoardObjectType.ABSORBER) {
                    new AddAbsorberHandler(model, boardController).handle(gizmo.getName());
                }
                else {
                    new AddHandler(model, boardController, gizmo.getType()).handle(gizmo.getName());
                }
            }
            for (Ball ball : model.getBalls()) {
                if (ball != null) {
                    new AddBallHandler(model, boardController).handle(ball.getName());
                }
            }


        } catch (FileNotFoundException e) {
            System.out.println("Error when trying to load the game. :(");
        } catch(IOException e ) {
            System.out.println("Error when trying to load the game. :(");
        }
    }

    public void checkAction(String[] string){
        double x;
        double y;
        double x2;
        double y2;
        switch(string[0]) {
            case "Triangle":
                try {
                x = Double.parseDouble(string[2]);
                y = Double.parseDouble(string[3]);
                model.addGizmo(x, y, string[1], BoardObjectType.TRIANGLE);
                } catch(Exception e){
                    System.out.println("Something has gone wrong...");
                }
                break;
            case "Square":
                try {
                x = Double.parseDouble(string[2]);
                y = Double.parseDouble(string[3]);
                model.addGizmo(x, y, string[1], BoardObjectType.SQUARE);
                } catch(Exception e){
                    System.out.println("Something has gone wrong...");
                }
                break;
            case "Circle":
                try {
                    x = Double.parseDouble(string[2]);
                    y = Double.parseDouble(string[3]);
                    model.addGizmo(x, y, string[1], BoardObjectType.CIRCLE);
                } catch(Exception e){
                    System.out.println("Something has gone wrong...");
                }
                break;
            case "LeftFlipper":
                try {
                    x = Double.parseDouble(string[2]);
                    y = Double.parseDouble(string[3]);
                    model.addGizmo(x, y, string[1], BoardObjectType.LEFT_FLIPPER);
                } catch(Exception e){
                    System.out.println("Something has gone wrong...");
                }
                break;
            case "RightFlipper" :
                try {
                    x = Double.parseDouble(string[2]);
                    y = Double.parseDouble(string[3]);
                    model.addGizmo(x, y, string[1], BoardObjectType.RIGHT_FLIPPER);
                } catch(Exception e){
                    System.out.println("Something has gone wrong...");
                }
                break;
            case "Absorber" :
                try {
                    x = Double.parseDouble(string[2]);
                    y = Double.parseDouble(string[3]);
                    x2 = Double.parseDouble(string[4]);
                    y2 = Double.parseDouble(string[5]);
                    model.addAbsorber(x, y, x2, y2, string[1]);
                } catch(Exception e){
                    System.out.println("Something has gone wrong...");
                }
                break;
            case "Ball":
                try {
                    x = Double.parseDouble(string[2]);
                    y = Double.parseDouble(string[3]);
                    x2 = Double.parseDouble(string[4]);
                    y2 = Double.parseDouble(string[5]);
                    model.addBall(x, y, x2, y2, string[1]);
                } catch(Exception e){
                    System.out.println("Something has gone wrong...");
                }
                break;
            case "Delete":
                if(string[1].charAt(0)=='B') {
                    model.removeBall(string[1]);
                }
                model.removeGizmo(string[1]);

                break;
            case "Rotate":
                try{
                    model.rotateGizmo(string[1]);
                } catch(Exception e){
                    System.out.println("Something has gone wrong...");
                }

                break;
            case "KeyConnect":
                try{

                } catch(Exception e){
                    System.out.println("Something has gone wrong...");
                }

                break;
            case "Connect":
                try{


                } catch(Exception e) {
                    System.out.println("Something has gone wrong...");
                }
                break;

            //TODO add key connects
            default:
                //todo add proper default
                break;
        }

        System.out.println(model.getGizmos().size());
    }
}
