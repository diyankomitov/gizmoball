package test;


import model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class testAddGizmo {

    private GizmoballModel gizmoballModel;


    @Before
    public void setUp() {
        gizmoballModel = new GizmoballModel();
    }

    @Test
    public void testAddCGizmo() {
        gizmoballModel.addGizmo(4.0, 20.0, GizmoType.CIRCLE, "");
        assertEquals(gizmoballModel.getGizmos().size(), 1);

    }

    @Test
    public void testAddSGizmo() {
        gizmoballModel.addGizmo(4.0, 20.0, GizmoType.SQUARE, "");


    }

    @Test
    public void testAddTGizmo() {
        gizmoballModel.addGizmo(4.0, 20.0, GizmoType.TRIANGLE, "");

    }


    @Test
    public void testAddGizmoz() {
        gizmoballModel.addGizmo(2, 2, GizmoType.CIRCLE, "");
        gizmoballModel.addGizmo(20, 20, GizmoType.CIRCLE, "");
        assertEquals(gizmoballModel.getGizmos().get(0).getName(), "C22");
        assertEquals(gizmoballModel.getGizmos().get(1).getName(), "C2020");
    }

    @Test
    public void testTwoGizmoSameLocation () {
        gizmoballModel.addGizmo(1, 1, GizmoType.SQUARE, "");
        gizmoballModel.addGizmo(1,1, GizmoType.CIRCLE, "");
        assertFalse("Should not have added gizmo in same location.",gizmoballModel.addGizmo(1,1, GizmoType.CIRCLE, ""));
    }

    @Test
    public void testAddGizmoNaming() {
        gizmoballModel.addGizmo(2.0, 2.0, GizmoType.CIRCLE, "");
        List<Gizmo> gl = gizmoballModel.getGizmos();
        assertTrue(gizmoballModel.getGizmoByName("C22").equals(gl.get(0)));
        gizmoballModel.addGizmo(3.0, 2.0, GizmoType.CIRCLE, "");
        gl = gizmoballModel.getGizmos();
        assertTrue(gizmoballModel.getGizmoByName("C32").equals(gl.get(1)));
    }

    @Test
    public void testGizmoType () {
        gizmoballModel.addGizmo(1, 1, GizmoType.SQUARE,"");
        assertEquals(gizmoballModel.getGizmoByName("S11").getType(), GizmoType.SQUARE);
    }

    @Test
    public void testRemoveGizmo () {
        gizmoballModel.addGizmo(1, 1, GizmoType.SQUARE, "");
        gizmoballModel.removeGizmo("S11");
        assertEquals(gizmoballModel.getGizmoByName("S11"), null);
    }
    @Test
    public void testGetCoordGizmo () {
        gizmoballModel.addGizmo(1, 1, GizmoType.SQUARE, "");
        assertEquals(gizmoballModel.getGizmoByCoords(1, 1), gizmoballModel.getGizmos().get(0));
    }

    @Test
    public void testAddGizmoInFlipperXY() {
        gizmoballModel.addGizmo(1,1, GizmoType.LEFT_FLIPPER, "LF11");
        assertFalse(gizmoballModel.addGizmo(1,1, GizmoType.SQUARE, "S11"));
    }

    @Test
    public void testAddGizmoInFlipperY2() {
        gizmoballModel.addGizmo(1,1, GizmoType.LEFT_FLIPPER, "LF11");
        assertFalse(gizmoballModel.addGizmo(1,2, GizmoType.CIRCLE, "C12"));
//        assertFalse(gizmoballModel.addGizmo(2,2, GizmoType.TRIANGLE, "T22"));
    }
    @Test
    public void testAddGizmoInFlipperXY2() {
        gizmoballModel.addGizmo(1,1, GizmoType.LEFT_FLIPPER, "LF11");
        assertFalse(gizmoballModel.addGizmo(2,2, GizmoType.TRIANGLE, "T22"));
    }



}
