package view;

import controller.FXRunListener;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.Model;

/**
 * @author Diyan's Implementation of Murray's Physics demo in JavaFX
 */

public class FXRunGui {

    private Model model;
    private FXRunListener listener;
    private FXBoard board;

    public FXRunGui(Model m) {
        //the model
        model = m;

        // tbe controller
        listener = new FXRunListener(m);
    }

    public Pane createRoot() {
        // Board is passed the Model so it can act as Observer
        board = new FXBoard(500, 500, model);

        Font gf = new Font("Arial", 12);

        //In FX pane = swing's panel
        //VBox is a pane that layouts its children vertically on top of each other
        VBox buttons = new VBox();

        //make new button
        Button button1 = new Button("Start");
        button1.setFont(gf);

        //when clicked call listener.onStart()
        button1.setOnAction(e -> listener.onStart());

        button1.setPrefSize(100,100);

        //add the button to the "buttons" vbox
        buttons.getChildren().add(button1);

        //make 3 more buttons, same thing, different names
        Button button2 = new Button("Stop");
        button2.setFont(gf);
        button2.setOnAction(e -> listener.onStop());
        button2.setPrefSize(100,100);
        buttons.getChildren().add(button2);

        Button button3 = new Button("Tick");
        button3.setFont(gf);
        button3.setOnAction(e -> listener.onTick());
        button3.setPrefSize(100,100);
        buttons.getChildren().add(button3);

        Button button4 = new Button("Quit");
        button4.setFont(gf);
        button4.setOnAction(e -> listener.onExit());
        button4.setPrefSize(100,100);
        buttons.getChildren().add(button4);

        //BorderPane is a pane that layouts its children in 5 regions: top, bottom, left, right, center
        BorderPane root = new BorderPane();

        //add buttons to left and board to center
        root.setLeft(buttons);
        root.setCenter(board);

        //return root pane to be added to the scene
        return root;
    }

}
