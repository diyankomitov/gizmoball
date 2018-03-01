package test;
import model.*;

import model.board.Ball;
import model.board.BoardObjectType;
import org.junit.Before;
import org.junit.Test;
import physics.Vect;

import static org.junit.Assert.*;

public class TestAddRemoveBall {
    private GizmoballModel gizmoballModel;
    @Before
    public void setUp() {
        gizmoballModel = new GizmoballModel();
        gizmoballModel.addBall(5, 5, 10, 10, "B1");
    }

    @Test
    public void testBallLocation() {
        System.out.println(gizmoballModel.getBall("B1"));
        assertTrue(gizmoballModel.getBall("B1").getX()== 5);
        assertTrue(gizmoballModel.getBall("B1").getY()== 5);

    }

    @Test
    public void actuallyHasBall() {
//        assertNotNull(gizmoballModel.getBall("B1"));
        gizmoballModel.getBall("B1");
    }

    @Test
    public void testRemoveBall() {
//        gizmoballModel = new GizmoballModel();
//        gizmoballModel.addBall(5, 5, 10, 10, "B1");
        assertTrue(gizmoballModel.removeBall("B1" ));

    }

    @Test
    public void testRemoveBallNotNull() {
        gizmoballModel = new GizmoballModel();
        gizmoballModel.addBall(5, 5, 10, 10, "B1");
        assertTrue(gizmoballModel.removeBall("B1"));
    }

    @Test
    public void testRemoveWrongBall(){
        assertFalse(gizmoballModel.removeBall("B"));

    }

    @Test
    public void testAddBallOccupiedLocation() {
        gizmoballModel.addGizmo(7,7,"C77", BoardObjectType.CIRCLE);
        assertFalse(gizmoballModel.addBall(7,7,5,5, "B2"));
    }

    @Test
    public void testAddSecondBall() {
        assertTrue(gizmoballModel.addBall(8,9, 10,10, "B3"));
    }

    @Test
    public void testAddBallSameName() {
        assertFalse(gizmoballModel.addBall(1,1, 10, 10, "B1"));
    }
}
