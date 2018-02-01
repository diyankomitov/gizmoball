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
        gizmoballModel.addGizmo(4.0, 20.0, GizmoType.CIRCLE);
    }

    @Test
    public void testAddGizmo() {

        assertEquals(gizmoballModel.getCCounter(), 0);

    }

    @After
    public void tearDown() {

    }

}
