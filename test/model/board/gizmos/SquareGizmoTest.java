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

public class SquareGizmoTest {

    private SquareGizmo squareGizmo;

    @Before
    public void setUp() {
        squareGizmo = new SquareGizmo(1,1,1,"S");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testTypeIsCorrect() {
        BoardObjectType type = squareGizmo.getType();
        assertEquals(BoardObjectType.SQUARE, type);
    }

    @Test
    public void testNameIsCorrect() {
        String name = squareGizmo.getName();
        assertEquals("S", name);
    }

    @Test
    public void testGetX() {
        double x = squareGizmo.getX();
        assertEquals(1, x, 0);
    }

    @Test
    public void testGetXAfterSettingNewCoordinates() {
        squareGizmo.setCoordinates(2,2);
        assertEquals(2, squareGizmo.getX(), 0);
    }

    @Test
    public void testGetY() {
        double y = squareGizmo.getY();
        assertEquals(1, y, 0);
    }

    @Test
    public void testGetYAfterSettingNewCoordinates() {
        squareGizmo.setCoordinates(2,2);
        assertEquals(2, squareGizmo.getX(), 0);
    }

    @Test
    public void testLinesAreInCorrectPositions() {
        List<LineSegment> lines = squareGizmo.getLines();

        LineSegment line1 = new LineSegment(1,1,2,1);
        LineSegment line2 = new LineSegment(2,1,2,2);
        LineSegment line3 = new LineSegment(2,2,1,2);
        LineSegment line4 = new LineSegment(1,2,1,1);

        assertTrue(lines.contains(line1));
        assertTrue(lines.contains(line2));
        assertTrue(lines.contains(line3));
        assertTrue(lines.contains(line4));
    }

    @Test
    public void testCirclesAreInCorrectPositions() {
        List<Circle> circles = squareGizmo.getCircles();

        Circle circle1 = new Circle(1,1, 0);
        Circle circle2 = new Circle(2,1,0);
        Circle circle3 = new Circle(2,2,0);
        Circle circle4 = new Circle(1,2,0);

        assertTrue(circles.contains(circle1));
        assertTrue(circles.contains(circle2));
        assertTrue(circles.contains(circle3));
        assertTrue(circles.contains(circle4));
    }

    @Test
    public void testGetCenter() {
        Vect center = squareGizmo.getCenter();
        assertEquals(new Vect(1.5, 1.5), center);
    }

    @Test
    public void testGetRCoefficient() {
        double rCoefficient = squareGizmo.getRCoefficient();
        assertEquals(1, rCoefficient, 0);
    }

    @Test
    public void testAngleIs0() {
        double angle = squareGizmo.getAngle();
        assertEquals(0, angle, 0);
    }

    @Test
    public void testAngleIsStill0AfterRotate() {
        squareGizmo.rotate();
        double angle = squareGizmo.getAngle();
        assertEquals(0, angle, 0);
    }
}