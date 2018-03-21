package controller.handlers.runhandlers;

import controller.handlers.generalhandlers.LoadHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.GizmoballModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ResetGameHandler implements EventHandler<ActionEvent> {
//    private final GizmoballModel model;

    public static final File SAVE_STATE = new File(System.getProperty("user.home") + File.separator + "Documents" + File.separator + "Gizmoball" + File.separator + "savestate.txt");
    private final LoadHandler loadHandler;

    public ResetGameHandler(GizmoballModel model, LoadHandler loadHandler) {
//        this.model = model;
        this.loadHandler = loadHandler;

        try {
            Files.createDirectories(SAVE_STATE.getParentFile().toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(ActionEvent event) {
        loadHandler.loadGame(SAVE_STATE);
    }
}
