package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import model.Model;

/**
 * @author Diyan's Implementation of Murray's Physics demo in JavaFX
 */

public class FXRunListener {

    private final Timeline timeline;
    private Model model;

    public FXRunListener(Model m) {
        model = m;

        //one way to do the gameloop. Timeline allows for fixed framerate, just like the Swing Timer
        timeline = new Timeline(
                new KeyFrame(   //keyframes allow for something to happen at a given time
                        Duration.ZERO,  //keyframe that has zero duration, or it happens immediately
                        actionEvent -> model.moveBall() //moves the ball
                ),
                new KeyFrame(
                        Duration.millis(50) //then waits 50 miliseconds before running again
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE); //keeps running until stop is called
    }

    public void onStart() {
        timeline.play();    //starts the game loop
    }

    public void onStop() {
        timeline.stop();    //stops the game loop
    }

    public void onTick() {
        model.moveBall();   //does one tick same as murrays
    }

    public void onExit() {
        System.exit(0); //exits the app
    }
}
