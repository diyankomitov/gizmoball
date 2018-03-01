package test;
import model.*;

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
        assertTrue(gizmoballModel.getBall("B1").getX()== 5);
        assertTrue(gizmoballModel.getBall("B1").getY()== 5);

    }

    @Test
    public void testRemoveBall() {
        assertTrue(gizmoballModel.removeBall("B1" ));
    }
}
