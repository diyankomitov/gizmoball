package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;
import model.VerticalLine;
import view.FXRunGui;

/**
 * @author Diyan's Implementation of Murray's Physics demo in JavaFX
 */

public class FXMain extends Application {   //all FX need a main to extend Application

    public static void main(String[] args) {
        launch(args);   //launch the app, method of Appliccation, calls start bellow
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Create model, same as Murray's
        Model model = new Model();

        model.setBallSpeed(200, 200);

        // Vertical line at (100,100), width 300
        model.addLine(new VerticalLine(100, 100, 300));
        model.addLine(new VerticalLine(100, 200, 300));
        model.addLine(new VerticalLine(100, 300, 300));
        model.addLine(new VerticalLine(100, 400, 300));

        //in FX stage = the app window includes title bar and the top right buttons, primaryStage is the primary window
        //set title of window
        primaryStage.setTitle("Diyans FX Implementation of Ball with Lines");

        //Runs the view
        FXRunGui fx = new FXRunGui(model);

        //in FX scene = content inside window
        //set the scene to a new scene with a root pane given fx.createRoot
        primaryStage.setScene(new Scene(fx.createRoot()));

        //display the window
        primaryStage.show();
    }
}
