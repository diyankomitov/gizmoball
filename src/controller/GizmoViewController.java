package controller;

import javafx.scene.input.MouseEvent;
import view.GizmoView;

public class GizmoViewController{
    private int mode;
    private GizmoView gizmoView;
    private BuildModeController buildModeController;
    private double offsetX;
    private double offsetY;

    public GizmoViewController(BuildModeController buildModeController) {
        this.buildModeController = buildModeController;
        mode = buildModeController.getBuildMode();
    }

    public void setGizmoView(GizmoView gizmoView) {
        this.gizmoView = gizmoView;
    }

    public void onMousePressed() {
        if (mode == 1) {
                offsetX = gizmoView.getTranslateX();
                offsetY = gizmoView.getTranslateY();
                gizmoView.setMouseTransparent(true);
            }
            if (mode == 2) {
                gizmoView.setRotate(gizmoView.getRotate()+90);
            }
            if (mode == 3) {
                buildModeController.removeGizmo(gizmoView);
            }
    }

    public void onDragDetected() {
        if (mode == 1) {
            gizmoView.startFullDrag();
        }
    }

    public void onMouseDragged(MouseEvent mouseEvent) {
        if (mode == 1) {
            gizmoView.setTranslateX(mouseEvent.getSceneX());
            gizmoView.setTranslateY(mouseEvent.getSceneY());
        }
    }

    public void onMouseReleased() {
        if (mode == 1) {
                buildModeController.moveGizmo(gizmoView);
                gizmoView.setMouseTransparent(false);
            }
    }

//    private void setBumperMovement(Node newBumper) {
//        final double[] offsetX = {0};
//        final double[] offsetY = {0};
//
//        newBumper.setOnMousePressed(event -> {
//            if (mode == 1) {
//                gizmoButton = newBumper;
//                offsetX[0] = getBoundsInScene(newBumper).getMinX();
//                offsetY[0] = getBoundsInScene(newBumper).getMinY();
//                System.out.println(offsetX[0] + " " + offsetY[0]);
//                newBumper.setMouseTransparent(true);
//            }
//            if (mode == 2) {
//                newBumper.setRotate(newBumper.getRotate()+90);
//            }
//            if (mode == 3) {
//                board.getChildren().remove(newBumper);
//            }
//        });
//        newBumper.setOnDragDetected(event -> {
//            if (mode == 1) {
//                onDragDetected(event);
//            }
//        });
//        newBumper.setOnMouseDragged(event -> {
//            if (mode == 1) {
//                translateBumper(event.getSceneX()-offsetX[0], event.getSceneY()-offsetY[0]);
//            }
//        });
//        newBumper.setOnMouseReleased(event -> {
//            if (mode == 1) {
//                onBumperReleased(event);
//                newBumper.setMouseTransparent(false);
//            }
//        });
//
//    }

}
