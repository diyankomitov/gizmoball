package test;


import controller.GizmoballController;
import model.GizmoType;
import model.GizmoballModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
    public void testTwoGizmoSameLocation () {
        gizmoballModel.addGizmo(1, 1, GizmoType.SQUARE );
        gizmoballModel.addGizmo(1,1, GizmoType.CIRCLE);
        assertFalse("Should not have added gizmo in same location.",gizmoballModel.addGizmo(1,1, GizmoType.CIRCLE));

    }
    @After
    public void tearDown() {

    }

}
