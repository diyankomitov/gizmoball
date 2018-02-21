package test;

import model.GizmoType;
import model.GizmoballModel;
import model.TriangleGizmo;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class testRotateGizmo {

    private GizmoballModel gizmoballModel;
    @Before
    public void setUp() {
        gizmoballModel = new GizmoballModel();
    }

    @Test
    public void testRotate90(){
        gizmoballModel.addGizmo(1, 1, GizmoType.TRIANGLE, "T");
        gizmoballModel.rotateGizmo("T");
        assertEquals(((TriangleGizmo)gizmoballModel.getGizmoByName("T")).getAngle(), 180.0, 0);
    }

    @Test
    public void testRotate360(){
        gizmoballModel.addGizmo(1, 1, GizmoType.TRIANGLE, "T");
        gizmoballModel.rotateGizmo("T");
        gizmoballModel.rotateGizmo("T");
        gizmoballModel.rotateGizmo("T");
        gizmoballModel.rotateGizmo("T");
        assertEquals(((TriangleGizmo)gizmoballModel.getGizmoByName("T")).getAngle(), 90, 0);
    }

    @Test
    public void testRotateOver360(){
        gizmoballModel.addGizmo(1, 1, GizmoType.TRIANGLE, "T");
        gizmoballModel.rotateGizmo("T");
        gizmoballModel.rotateGizmo("T");
        gizmoballModel.rotateGizmo("T");
        gizmoballModel.rotateGizmo("T");
        gizmoballModel.rotateGizmo("T");
        gizmoballModel.rotateGizmo("T");
        gizmoballModel.rotateGizmo("T");
        assertEquals(((TriangleGizmo)gizmoballModel.getGizmoByName("T")).getAngle(), 0, 0);
    }
}
