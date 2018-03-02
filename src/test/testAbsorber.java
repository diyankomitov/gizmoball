package test;

import model.GizmoballModel;
import model.board.BoardObjectType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class testAbsorber {

    private GizmoballModel gizmoballModel;

    @Before
    public void setUp() {
        gizmoballModel = new GizmoballModel();
    }

    @Test
    public void testAddAbsorber(){
        gizmoballModel.addAbsorber(1.0, 20.0, 3.0, 17.0, "");
        assertEquals(gizmoballModel.getGizmos().size(), 1);
    }

    @Test
    public void testAddMultipleAbsorbers(){
        gizmoballModel.addAbsorber(1.0, 20.0, 3.0, 17.0, "A1");
        gizmoballModel.addAbsorber(5.0, 20.0, 8.0, 17.0, "A2");
        assertEquals(gizmoballModel.getGizmos().get(0).getName(), "A1");
        assertEquals(gizmoballModel.getGizmos().get(1).getName(), "A2");
    }

    @Test
    public void testTwoAbsorbersSameLocation(){
        gizmoballModel.addAbsorber(1.0, 17.0, 3.0, 20.0, "A1");
        assertFalse(gizmoballModel.addAbsorber(1.0, 17.0, 3.0, 20.0, "A2"));
    }

    @Test
    public void testTwoAbsorbersOverLapping(){
        gizmoballModel.addAbsorber(1.0, 17.0, 3.0, 20.0, "A1");
        assertFalse(gizmoballModel.addAbsorber(2.0, 17.0, 4.0, 20.0, "A2"));
    }

    @Test
    public void testRemoveAbsorber() {
        gizmoballModel.addAbsorber(1.0, 20.0, 3.0, 17.0, "A1");
        gizmoballModel.removeGizmo("A1");
        assertNull(gizmoballModel.getGizmo("A1"));
    }

    @Test
    public void testAddAbsorberOnGizmo() {
        gizmoballModel.addGizmo(1.0, 17.0, "C120", BoardObjectType.CIRCLE);
        assertFalse(gizmoballModel.addAbsorber(1.0, 17.0, 3.0, 20.0, "A1"));
    }

    @Test
    public void testAddAbsorberOverGizmo() {
        gizmoballModel.addGizmo(2.0, 18.0, "C218", BoardObjectType.CIRCLE);
        assertFalse(gizmoballModel.addAbsorber(1.0, 17.0, 3.0, 20.0, "A1"));
    }

    @Test
    public void testAddAbsorberOverGizmos() {
        gizmoballModel.addGizmo(2.0, 18.0, "C218", BoardObjectType.CIRCLE);
        gizmoballModel.addGizmo(2.0, 19.0, "C219", BoardObjectType.CIRCLE);
        assertFalse(gizmoballModel.addAbsorber(1.0, 17.0, 3.0, 20.0, "A1"));
    }
}
