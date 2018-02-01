package test;


import controller.GizmoballController;
import model.GizmoType;
import model.GizmoballModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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

    @After
    public void tearDown() {

    }

}
