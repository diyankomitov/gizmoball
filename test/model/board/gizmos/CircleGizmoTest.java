package model.board.gizmos;

import model.board.BoardObjectType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import java.util.List;

import static org.junit.Assert.*;

public class CircleGizmoTest {

    private CircleGizmo circleGizmo;

    @Before
    public void setUp() throws Exception {
        circleGizmo = new CircleGizmo(1,1,1,"C");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testTypeIsCorrect() {
        BoardObjectType type = circleGizmo.getType();
        assertEquals(BoardObjectType.CIRCLE, type);
    }

    @Test
    public void testNameIsCorrect() {
        String name = circleGizmo.getName();
        assertEquals("C", name);
    }

    @Test
    public void testGetX() {
        double x = circleGizmo.getX();
        assertEquals(1, x, 0);
    }

    @Test
    public void testGetXAfterSettingNewCoordinates() {
        circleGizmo.setCoordinates(2,0);
        assertEquals(2, circleGizmo.getX(), 0);
    }

    @Test
    public void testGetY() {
        double y = circleGizmo.getY();
        assertEquals(1, y, 0);
    }

    @Test
    public void testGetYAfterSettingNewCoordinates() {
        circleGizmo.setCoordinates(0,2);
        assertEquals(2, circleGizmo.getY(), 0);
    }

    @Test
    public void testNoLinesExist() {
        List<LineSegment> lines = circleGizmo.getLines();

        assertEquals(0, lines.size());
    }

    @Test
    public void testOnlyOneCircle() {
        List<Circle> circles = circleGizmo.getCircles();
        assertEquals(1, circles.size());
    }

    @Test
    public void testCircleIsInCorrectPosition() {
        List<Circle> circles = circleGizmo.getCircles();
        Circle circle = circles.get(0);
        assertEquals(new Circle(1.5,1.5,0.5), circle);
    }

    @Test
    public void testGetCenter() {
        Vect center = circleGizmo.getCenter();
        assertEquals(new Vect(1.5, 1.5), center);
    }

    @Test
    public void testGetRCoefficient() {
        double rCoefficient = circleGizmo.getRCoefficient();
        assertEquals(1, rCoefficient, 0);
    }

    @Test
    public void testAngleIs0() {
        double angle = circleGizmo.getAngle();
        assertEquals(0, angle, 0);
    }

    @Test
    public void testAngleIsStill0AfterRotate() {
        circleGizmo.rotate();
        double angle = circleGizmo.getAngle();
        assertEquals(0, angle, 0);
    }
}