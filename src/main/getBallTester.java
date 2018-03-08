package main;

import model.GizmoballModel;

public class getBallTester {

    public static void main(String[] args) {

        GizmoballModel gizmoballModel = new GizmoballModel();
        System.out.println("HELLO");
        gizmoballModel.addBall(5, 5, 10, 10, "B1");
        gizmoballModel.getBall("B1");

    }

}
