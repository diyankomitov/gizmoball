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

    public void loadGame(File file) {
        try {
            System.out.println("At top of loadGame");
            model = new GizmoballModel();
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

        switch(string[0]) {
            case "Triangle":
                x = Double.parseDouble(string[2]);
                y = Double.parseDouble(string[3]);
                model.addGizmo(x, y, GizmoType.TRIANGLE, string[1]);
                break;
            case "Square":
                x = Double.parseDouble(string[2]);
                y = Double.parseDouble(string[3]);
                model.addGizmo(x, y, GizmoType.SQUARE, string[1]);
                break;
            case "Circle":
                x = Double.parseDouble(string[2]);
                y = Double.parseDouble(string[3]);
                model.addGizmo(x, y, GizmoType.CIRCLE, string[1]);
                break;
            case "LeftFlipper":
                x = Double.parseDouble(string[2]);
                y = Double.parseDouble(string[3]);
                model.addGizmo(x, y, GizmoType.LEFT_FLIPPER, string[1]);
                break;
            case "RightFlipper" :
                x = Double.parseDouble(string[2]);
                y = Double.parseDouble(string[3]);
                model.addGizmo(x, y, GizmoType.RIGHT_FLIPPER, string[1]);
                break;
            case "Absorber" :
                x = Double.parseDouble(string[2]);
                y = Double.parseDouble(string[3]);
                double x2 = Double.parseDouble(string[4]);
                double y2 = Double.parseDouble(string[5]);
                model.addAbsorber(x, y, x2, y2, string[1]);

            default:
                break;
        }

        System.out.println(model.getGizmos().size());
    }
}
