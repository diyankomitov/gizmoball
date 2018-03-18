package test;


import model.*;
import model.board.BoardObjectType;
import model.board.gizmos.Gizmo;
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
        gizmoballModel.addGizmo(4.0, 20.0, "", BoardObjectType.CIRCLE);
        assertEquals(gizmoballModel.getGizmos().size(), 1);

    }

    @Test
    public void testAddSGizmo() {
        gizmoballModel.addGizmo(4.0, 20.0, "", BoardObjectType.SQUARE);


    }

    @Test
    public void testAddTGizmo() {
        gizmoballModel.addGizmo(4.0, 20.0, "", BoardObjectType.TRIANGLE);

    }


    @Test
    public void testAddGizmoz() {
        gizmoballModel.addGizmo(2, 2, "C22", BoardObjectType.CIRCLE);
        gizmoballModel.addGizmo(20, 20, "C2020", BoardObjectType.CIRCLE);
        assertEquals(gizmoballModel.getGizmos().get(0).getName(), "C22");
        assertEquals(gizmoballModel.getGizmos().get(1).getName(), "C2020");
    }

    @Test
    public void testTwoGizmoSameLocation () {
        gizmoballModel.addGizmo(1, 1, "S11", BoardObjectType.SQUARE);
        gizmoballModel.addGizmo(1,1, "C11", BoardObjectType.CIRCLE);
        assertFalse("Should not have added gizmo in same location.",gizmoballModel.addGizmo(1,1, "", BoardObjectType.CIRCLE));
    }

    @Test
    public void testAddGizmoNaming() {
        gizmoballModel.clearBoard();
        gizmoballModel.addGizmo(2.0, 2.0, "", BoardObjectType.CIRCLE);
        List<Gizmo> gl = gizmoballModel.getGizmos();
        assertTrue(gizmoballModel.getGizmo("C0").equals(gl.get(0)));

    }

    @Test
    public void testGizmoType () {
        gizmoballModel.addGizmo(1, 1, "S11", BoardObjectType.SQUARE);
        assertEquals(gizmoballModel.getGizmo("S11").getType(), BoardObjectType.SQUARE);
    }

    @Test
    public void testRemoveGizmo () {
        gizmoballModel.addGizmo(1, 1, "S11", BoardObjectType.SQUARE);
        System.out.println("Name is : "+gizmoballModel.getGizmo(1, 1).getName());
       assertTrue(gizmoballModel.removeGizmo("S11"));

    }
    @Test
    public void testRemoveGizmoNotExists () {
       assertFalse(gizmoballModel.removeGizmo("S11"));
    }

    @Test
    public void testRemoveGizmoCoords() {
        gizmoballModel.addGizmo(5, 5, "T2", BoardObjectType.TRIANGLE);
        assertTrue(gizmoballModel.removeGizmo(5,5));
    }
    @Test
    public void testRemoveNoGizmoCoords() {
        assertFalse(gizmoballModel.removeGizmo(5,5));
    }

    @Test
    public void testGetCoordGizmo () {
        gizmoballModel.addGizmo(1, 1, "", BoardObjectType.SQUARE);
        assertEquals(gizmoballModel.getGizmo(1, 1), gizmoballModel.getGizmos().get(0));
    }

    @Test
    public void testAddGizmoInFlipperXY() {
        gizmoballModel.addGizmo(1,1, "LF11", BoardObjectType.LEFT_FLIPPER);
        assertFalse(gizmoballModel.addGizmo(1,1, "S11", BoardObjectType.SQUARE));
    }

    @Test
    public void testAddGizmoInFlipperY2() {
        gizmoballModel.addGizmo(1,1, "LF11", BoardObjectType.LEFT_FLIPPER);
        assertFalse(gizmoballModel.addGizmo(1,2, "C12", BoardObjectType.CIRCLE));
//        assertFalse(gizmoballModel.addGizmo(2,2, GizmoType.TRIANGLE, "T22"));
    }
    @Test
    public void testAddGizmoInFlipperXY2() {
        gizmoballModel.addGizmo(1,1, "LF11", BoardObjectType.LEFT_FLIPPER);
        assertFalse(gizmoballModel.addGizmo(2,2, "T22", BoardObjectType.TRIANGLE));
    }

    @Test
    public void testAddGizmoSameName() {
        gizmoballModel.addGizmo(1, 1, "S11", BoardObjectType.SQUARE);
        assertFalse(gizmoballModel.addGizmo(2,3, "S11", BoardObjectType.SQUARE));
    }

    @Test
    public void testAddGizmoInAbsorber() {
        gizmoballModel.addAbsorber(0,16,19, 19, "A1");
        assertFalse(gizmoballModel.addGizmo(4,17, "C417", BoardObjectType.CIRCLE));
    }
}
