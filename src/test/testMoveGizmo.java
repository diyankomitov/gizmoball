package test;
import model.*;

import model.board.BoardObjectType;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;
public class testMoveGizmo {

    private GizmoballModel gizmoballModel;
    @Before
    public void setUp() {
        gizmoballModel = new GizmoballModel();
        gizmoballModel.addGizmo(4.0, 20.0, "C420", BoardObjectType.CIRCLE);
    }

    @Test
    public void testMove1Gizmo() {
        gizmoballModel.moveGizmo("C420", 1.0, 1.0);
        assertTrue(gizmoballModel.getGizmo("C420").getX()==1.0);
        assertTrue(gizmoballModel.getGizmo("C420").getY()== 1.0);
    }

    @Test
    public void testMoveWrongGizmo() {
        assertFalse(gizmoballModel.moveGizmo("T", 2.0, 2.0));

    }

    @Test
    public void testMoveTwoGizmos() {
        gizmoballModel.addGizmo(3.0, 5.0, "C35", BoardObjectType.CIRCLE);
        gizmoballModel.moveGizmo("C420", 1, 3);
        assertTrue(gizmoballModel.getGizmo("C420").getX()==1);
        assertTrue(gizmoballModel.getGizmo("C420").getY()==3);
        gizmoballModel.moveGizmo("C35", 5, 6);
        assertTrue(gizmoballModel.getGizmo("C35").getX()==5);
        assertTrue(gizmoballModel.getGizmo("C35").getY()==6);


    }

    @Test
    public void testMoveGizmoToOccupiedLocation() {
        gizmoballModel.addGizmo(3.0, 5.0, "C35", BoardObjectType.CIRCLE);
        assertFalse(gizmoballModel.moveGizmo("C420", 3, 5));

    }

    @Test
    public void testMoveToFlipperLocation() {
        gizmoballModel.addGizmo(2, 2, "LF22", BoardObjectType.LEFT_FLIPPER);
        assertFalse(gizmoballModel.moveGizmo("C420", 2, 2));
        assertFalse(gizmoballModel.moveGizmo("C420", 2, 3));
    }

    @Test
    public void testMoveToAbsorberLocation() {
        gizmoballModel.addAbsorber(1, 12, 20, 14, "A1");
        assertFalse(gizmoballModel.moveGizmo("C420", 1, 13));
        assertFalse(gizmoballModel.moveGizmo("C420", 2, 12));
        assertFalse(gizmoballModel.moveGizmo("C420", 10, 13));
        assertFalse(gizmoballModel.moveGizmo("C420", 19, 13));
    }

    @Test
    public void testMoveToAbsorberX() {
        gizmoballModel.addAbsorber(1, 12, 20, 14, "A1");
        assertFalse(gizmoballModel.moveGizmo("C420", 1, 12));

    }

    @Test
    public void testMoveCoords() {
        assertTrue(gizmoballModel.moveGizmo(4,20, 5,5));
    }

    @Test
    public void testMoveCoordsOccupied() {
        gizmoballModel.addGizmo(5,5, "", BoardObjectType.TRIANGLE);
        assertFalse(gizmoballModel.moveGizmo(5,5, 4,20));
    }
}
