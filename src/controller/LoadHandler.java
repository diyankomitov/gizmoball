package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Absorber;
import model.BoardObjectType;
import model.GizmoballModel;
import view.BoardView;

import java.io.*;

public class LoadHandler implements EventHandler<ActionEvent> {
    private Stage stage;
    private GizmoballModel model; //Alistair thinks it might needs passed
    private BoardView board;
    public LoadHandler(Stage stage, BoardView board, GizmoballModel model) {
        this.board = board;

        this.stage = stage;
        this.model = model;
    }


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
            model.getBalls().forEach(ball -> {
                if(ball!=null){
                    board.addBall(ball);
                }
            });




            Absorber absorber = (Absorber) model.getGizmoByName("A");
            board.getScene().setOnKeyPressed(keyEvent -> {
                switch (keyEvent.getCode()) {
                    case G:
                        absorber.shootBall();
                        break;
                }
            });

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
                x = Double.parseDouble(string[2]);
                y = Double.parseDouble(string[3]);
                model.addGizmo(x, y, BoardObjectType.TRIANGLE, string[1]);
                break;
            case "Square":
                x = Double.parseDouble(string[2]);
                y = Double.parseDouble(string[3]);
                model.addGizmo(x, y, BoardObjectType.SQUARE, string[1]);
                break;
            case "Circle":
                x = Double.parseDouble(string[2]);
                y = Double.parseDouble(string[3]);
                model.addGizmo(x, y, BoardObjectType.CIRCLE, string[1]);
                break;
            case "LeftFlipper":
                x = Double.parseDouble(string[2]);
                y = Double.parseDouble(string[3]);
                model.addGizmo(x, y, BoardObjectType.LEFT_FLIPPER, string[1]);
                break;
            case "RightFlipper" :
                x = Double.parseDouble(string[2]);
                y = Double.parseDouble(string[3]);
                model.addGizmo(x, y, BoardObjectType.RIGHT_FLIPPER, string[1]);
                break;
            case "Absorber" :
                x = Double.parseDouble(string[2]);
                y = Double.parseDouble(string[3]);
                x2 = Double.parseDouble(string[4]);
                y2 = Double.parseDouble(string[5]);
                model.addAbsorber(x, y, x2, y2, string[1]);
                break;
            case "Ball":
                x = Double.parseDouble(string[2]);
                y = Double.parseDouble(string[3]);
                x2 = Double.parseDouble(string[4]);
                y2 = Double.parseDouble(string[5]);
                model.addBall(x, y, x2, y2, string[1]);
                break;
            case "Delete":
                if(string[1].charAt(0)=='B') {
                    model.removeBall();
                    board.removeBall();
                }
                model.removeGizmo(string[1]);

                break;
            case "Rotate":
                //TODO call rotate gizmo on string[2]
                model.rotateGizmo(string[1]);

            default:
                break;
        }

        System.out.println(model.getGizmos().size());
    }
}
