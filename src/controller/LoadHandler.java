package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Flipper;
import view.FlipperDirection;
import model.GizmoType;
import model.GizmoballModel;
import view.BoardView;

import java.io.*;

public class LoadHandler implements EventHandler<ActionEvent> {
    private Stage stage;
    private GizmoballModel model; //Alistair thinks it might needs passed
    private BoardView board;
    public LoadHandler(Stage stage, BoardView board) {
        this.board = board;

        this.stage = stage;
    }

    //TODO add key connects
    //TODO add default options if input is incorrect ie missing params or wrong name/type
    //TODO add gravity and friction???
    @Override
    public void handle(ActionEvent event) {
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
        board.clearBoard();
        model.clearGizmos();

    }

    public void loadGame(File file) {
        try {
            System.out.println("At top of loadGame");

            model = new GizmoballModel();

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

            model.getGizmos().forEach(gizmo -> {
//                System.out.println(i);
                        System.out.println(gizmo);
                        board.addGizmo(gizmo);

                    }



            );
            if(model.getBall()!=null){
                board.addBall(model.getBall());
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
                    model.addGizmo(x, y, GizmoType.TRIANGLE, string[1]);
                } catch(Exception e){
                    System.out.println("Something has gone wrong...");
                }
                break;
            case "Square":
                try{
                    x = Double.parseDouble(string[2]);
                    y = Double.parseDouble(string[3]);
                    model.addGizmo(x, y, GizmoType.SQUARE, string[1]);
                } catch(Exception e){
                    System.out.println("Something has gone wrong...");
                }
                break;
            case "Circle":
                try{
                    x = Double.parseDouble(string[2]);
                    y = Double.parseDouble(string[3]);
                    model.addGizmo(x, y, GizmoType.CIRCLE, string[1]);
                } catch(Exception e){
                    System.out.println("Something has gone wrong...");

                }
                break;
            case "LeftFlipper":
                try{
                    x = Double.parseDouble(string[2]);
                    y = Double.parseDouble(string[3]);
                    model.addGizmo(x, y, GizmoType.LEFT_FLIPPER, string[1]);
                } catch(Exception e){
                    System.out.println("Something has gone wrong...");
                }
                break;
            case "RightFlipper" :
                try{
                    x = Double.parseDouble(string[2]);
                    y = Double.parseDouble(string[3]);
                    model.addGizmo(x, y, GizmoType.RIGHT_FLIPPER, string[1]);
                } catch(Exception e){
                    System.out.println("Something has gone wrong...");
                }
                break;
            case "Absorber" :
                try{
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
                try{
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
                try{
                    if(model.getBall()!=null && model.getBall().getName().equals(string[1])) {
                        model.removeBall();
                        board.removeBall();
                    }
                    model.removeGizmo(string[1]);
                } catch(Exception e){
                    System.out.println("Something has gone wrong...");
                }
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
