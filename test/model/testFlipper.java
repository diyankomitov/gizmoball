package model;

import model.GizmoballModel;
import model.board.BoardObjectType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class testFlipper {

    private GizmoballModel gizmoballModel;

    @Before
    public void setUp() {
        gizmoballModel = new GizmoballModel();
    }

    @Test
    public void testAddLeftFlipper() {
        assertTrue(gizmoballModel.addGizmo(5, 5, "", BoardObjectType.LEFT_FLIPPER));
    }

    @Test
    public void testAddRightFlipper() {
        assertTrue(gizmoballModel.addGizmo(5, 5, "", BoardObjectType.RIGHT_FLIPPER));
    }

    @Test
    public void testAddFlipperOutside() {
        assertFalse(gizmoballModel.addGizmo(20, 20, "", BoardObjectType.RIGHT_FLIPPER));
    }

    @Test
    public void testAddFlipperInFlipper() {
        assertTrue(gizmoballModel.addGizmo(5, 5, "", BoardObjectType.RIGHT_FLIPPER));
        assertFalse(gizmoballModel.addGizmo(4, 5, "", BoardObjectType.RIGHT_FLIPPER));

    }
}
