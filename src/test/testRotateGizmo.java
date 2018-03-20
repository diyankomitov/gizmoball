package test;

import model.GizmoballModel;
import model.board.BoardObjectType;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


import static org.junit.Assert.assertEquals;

public class testRotateGizmo {

    private GizmoballModel gizmoballModel;
    @Before
    public void setUp() {
        gizmoballModel = new GizmoballModel();
    }

    @Test
    public void testRotate90(){
        gizmoballModel.addGizmo(1, 1, "T", BoardObjectType.TRIANGLE);
        gizmoballModel.rotateGizmo("T");
        assertEquals((gizmoballModel.getGizmo("T")).getAngle(), 90.0, 0);
    }

    @Test
    public void testRotate360(){
        gizmoballModel.addGizmo(1, 1, "T", BoardObjectType.TRIANGLE);
        gizmoballModel.rotateGizmo("T");
        gizmoballModel.rotateGizmo("T");
        gizmoballModel.rotateGizmo("T");
        gizmoballModel.rotateGizmo("T");
        assertEquals((gizmoballModel.getGizmo("T")).getAngle(), 0, 0);
    }

    @Test
    public void testRotateOver360(){
        gizmoballModel.addGizmo(1, 1, "T", BoardObjectType.TRIANGLE);
        gizmoballModel.rotateGizmo("T");
        gizmoballModel.rotateGizmo("T");
        gizmoballModel.rotateGizmo("T");
        gizmoballModel.rotateGizmo("T");
        gizmoballModel.rotateGizmo("T");
        gizmoballModel.rotateGizmo("T");
        gizmoballModel.rotateGizmo("T");
        assertEquals((gizmoballModel.getGizmo("T")).getAngle(), 270, 0);
    }

    @Test
    public void testRotateAbsorber(){
        gizmoballModel.addAbsorber(1.0, 20.0, 3.0, 17.0, "A1");
        assertFalse(gizmoballModel.rotateGizmo("A1"));
    }

    @Test
    public void testRotateNoGizmoLocation(){
        assertFalse(gizmoballModel.rotateGizmo(0, 0));
    }



}
