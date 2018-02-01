package test;


import controller.GizmoballController;
import model.Gizmo;
import model.GizmoType;
import model.GizmoballModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class testAddGizmo {

    private GizmoballModel gizmoballModel;


    @Before
    public void setUp() {
        gizmoballModel = new GizmoballModel("T");
    }

    @Test
    public void testAddGizmo() {
        gizmoballModel.addGizmo(4.0, 20.0, GizmoType.CIRCLE);
        assertEquals(gizmoballModel.getCCounter(), 1);

    }

    @Test
    public void testAddGizmoz() {
        gizmoballModel.addGizmo(2.0, 2.0, GizmoType.CIRCLE);
        assertEquals(gizmoballModel.getCCounter(), 1);
        gizmoballModel.addGizmo(20.0, 20.0, GizmoType.CIRCLE);
        assertEquals(gizmoballModel.getCCounter(), 2);

    }

    @Test
    public void testTwoGizmoSameLocation () {
        gizmoballModel.addGizmo(1, 1, GizmoType.SQUARE );
        gizmoballModel.addGizmo(1,1, GizmoType.CIRCLE);
        assertFalse("Should not have added gizmo in same location.",gizmoballModel.addGizmo(1,1, GizmoType.CIRCLE));
    }

    @Test
    public void testAddGizmoNaming() {
        gizmoballModel.addGizmo(2.0, 2.0, GizmoType.CIRCLE);
        List<Gizmo> gl = gizmoballModel.getGizmos();
        assertTrue(gizmoballModel.getGizmoByName("C0").equals(gl.get(0)));
        gizmoballModel.addGizmo(3.0, 2.0, GizmoType.CIRCLE);
        gl = gizmoballModel.getGizmos();
        assertTrue(gizmoballModel.getGizmoByName("C1").equals(gl.get(1)));

    }

    @Test
    public void testGizmoType () {
        gizmoballModel.addGizmo(1, 1, GizmoType.SQUARE );
        assertEquals(gizmoballModel.getGizmoByName("S0").getType(), GizmoType.SQUARE);
    }

    @After
    public void tearDown() {

    }

}
