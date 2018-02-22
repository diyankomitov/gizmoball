package test;
import model.*;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;
public class testMoveGizmo {

    private GizmoballModel gizmoballModel;
    @Before
    public void setUp() {
        gizmoballModel = new GizmoballModel();
        gizmoballModel.addGizmo(4.0, 20.0, GizmoType.CIRCLE, "C420");
    }

    @Test
    public void testMove1Gizmo() {
        gizmoballModel.moveGizmo("C420", 1.0, 1.0);
        assertTrue(gizmoballModel.getGizmoByName("C420").getXCoord()==1.0);
        assertTrue(gizmoballModel.getGizmoByName("C420").getYCoord()== 1.0);
    }

    @Test
    public void testMoveWrongGizmo() {
        assertFalse(gizmoballModel.moveGizmo("T", 2.0, 2.0));
    }

    @Test
    public void testMoveTwoGizmos() {
        gizmoballModel.addGizmo(3.0, 5.0, GizmoType.CIRCLE, "C35");
        assertTrue(gizmoballModel.moveGizmo("C420", 1, 3));
        assertTrue(gizmoballModel.moveGizmo("C35", 4, 5));
    }

    @Test
    public void testMoveGizmoToOccupiedLocation() {
        gizmoballModel.addGizmo(3.0, 5.0, GizmoType.CIRCLE, "C35");
        assertFalse(gizmoballModel.moveGizmo("C420", 3, 5));

    }

    @Test
    public void testMoveToFlipperLocation() {
        gizmoballModel.addGizmo(2, 2, GizmoType.LEFT_FLIPPER, "LF22");
        assertFalse(gizmoballModel.moveGizmo("C420", 2, 2));
        assertFalse(gizmoballModel.moveGizmo("C420", 2, 3));
    }

    @Test
    public void testMoveToAbsorberLocation() {
        gizmoballModel.addAbsorber(1, 19, 19, 20, "A1");
        assertFalse(gizmoballModel.moveGizmo("C420", 1, 19));
        assertFalse(gizmoballModel.moveGizmo("C420", 2, 19));
        assertFalse(gizmoballModel.moveGizmo("C420", 19, 20));
        assertFalse(gizmoballModel.moveGizmo("C420", 6, 20));
    }

    @Test
    public void testMoveToAbsorberX() {
        gizmoballModel.addAbsorber(1, 19, 19, 20, "A1");
        assertFalse(gizmoballModel.moveGizmo("C420", 1, 19));

    }
}
