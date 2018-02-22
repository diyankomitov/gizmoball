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

public class TriangleGizmoTest {

    private TriangleGizmo triangleGizmo;

    @Before
    public void setUp() throws Exception {
        triangleGizmo = new TriangleGizmo(1,1,1,"T");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testTypeIsCorrect() {
        BoardObjectType type = triangleGizmo.getType();
        assertEquals(BoardObjectType.TRIANGLE, type);
    }

    @Test
    public void testNameIsCorrect() {
        String name = triangleGizmo.getName();
        assertEquals("T", name);
    }

    @Test
    public void testGetX() {
        double x = triangleGizmo.getX();
        assertEquals(1, x, 0);
    }

    @Test
    public void testGetXAfterSettingNewCoordinates() {
        triangleGizmo.setCoordinates(2,2);
        assertEquals(2, triangleGizmo.getX(), 0);
    }

    @Test
    public void testGetY() {
        double y = triangleGizmo.getY();
        assertEquals(1, y, 0);
    }

    @Test
    public void testGetYAfterSettingNewCoordinates() {
        triangleGizmo.setCoordinates(2,2);
        assertEquals(2, triangleGizmo.getX(), 0);
    }

    @Test
    public void testLinesAreInCorrectPositions() {
        List<LineSegment> lines = triangleGizmo.getLines();

        LineSegment line1 = lines.get(0);
        LineSegment line2 = lines.get(1);
        LineSegment line3 = lines.get(2);

        assertEquals(1, line1.p1().x(), 0.000000000000001);
        assertEquals(1, line1.p1().y(), 0.000000000000001);
        assertEquals(2, line1.p2().x(), 0.000000000000001);
        assertEquals(1, line1.p2().y(), 0.000000000000001);

        assertEquals(2, line2.p1().x(), 0.000000000000001);
        assertEquals(1, line2.p1().y(), 0.000000000000001);
        assertEquals(1, line2.p2().x(), 0.000000000000001);
        assertEquals(2, line2.p2().y(), 0.000000000000001);

        assertEquals(1, line3.p1().x(), 0.000000000000001);
        assertEquals(2, line3.p1().y(), 0.000000000000001);
        assertEquals(1, line3.p2().x(), 0.000000000000001);
        assertEquals(1, line3.p2().y(), 0.000000000000001);
    }

    @Test
    public void testLinesAreInCorrectPositionsAfterRotate() {
        triangleGizmo.rotate();
        List<LineSegment> lines = triangleGizmo.getLines();

        LineSegment line1 = lines.get(0);
        LineSegment line2 = lines.get(1);
        LineSegment line3 = lines.get(2);

        assertEquals(2, line1.p1().x(), 0.000000000000001);
        assertEquals(1, line1.p1().y(), 0.000000000000001);
        assertEquals(2, line1.p2().x(), 0.000000000000001);
        assertEquals(2, line1.p2().y(), 0.000000000000001);

        assertEquals(2, line2.p1().x(), 0.000000000000001);
        assertEquals(2, line2.p1().y(), 0.000000000000001);
        assertEquals(1, line2.p2().x(), 0.000000000000001);
        assertEquals(1, line2.p2().y(), 0.000000000000001);

        assertEquals(1, line3.p1().x(), 0.000000000000001);
        assertEquals(1, line3.p1().y(), 0.000000000000001);
        assertEquals(2, line3.p2().x(), 0.000000000000001);
        assertEquals(1, line3.p2().y(), 0.000000000000001);
    }
    @Test
    public void testLinesAreInCorrectPositionsAfterRotateTwice() {
        triangleGizmo.rotate();
        triangleGizmo.rotate();
        List<LineSegment> lines = triangleGizmo.getLines();

        LineSegment line1 = lines.get(0);
        LineSegment line2 = lines.get(1);
        LineSegment line3 = lines.get(2);

        assertEquals(2, line1.p1().x(), 0.000000000000001);
        assertEquals(2, line1.p1().y(), 0.000000000000001);
        assertEquals(1, line1.p2().x(), 0.000000000000001);
        assertEquals(2, line1.p2().y(), 0.000000000000001);

        assertEquals(1, line2.p1().x(), 0.000000000000001);
        assertEquals(2, line2.p1().y(), 0.000000000000001);
        assertEquals(2, line2.p2().x(), 0.000000000000001);
        assertEquals(1, line2.p2().y(), 0.000000000000001);

        assertEquals(2, line3.p1().x(), 0.000000000000001);
        assertEquals(1, line3.p1().y(), 0.000000000000001);
        assertEquals(2, line3.p2().x(), 0.000000000000001);
        assertEquals(2, line3.p2().y(), 0.000000000000001);
    }
    @Test
    public void testLinesAreInCorrectPositionsAfterRotateThreeTimes() {
        triangleGizmo.rotate();
        triangleGizmo.rotate();
        triangleGizmo.rotate();
        List<LineSegment> lines = triangleGizmo.getLines();

        LineSegment line1 = lines.get(0);
        LineSegment line2 = lines.get(1);
        LineSegment line3 = lines.get(2);

        assertEquals(1, line1.p1().x(), 0.000000000000001);
        assertEquals(2, line1.p1().y(), 0.000000000000001);
        assertEquals(1, line1.p2().x(), 0.000000000000001);
        assertEquals(1, line1.p2().y(), 0.000000000000001);

        assertEquals(1, line2.p1().x(), 0.000000000000001);
        assertEquals(1, line2.p1().y(), 0.000000000000001);
        assertEquals(2, line2.p2().x(), 0.000000000000001);
        assertEquals(2, line2.p2().y(), 0.000000000000001);

        assertEquals(2, line3.p1().x(), 0.000000000000001);
        assertEquals(2, line3.p1().y(), 0.000000000000001);
        assertEquals(1, line3.p2().x(), 0.000000000000001);
        assertEquals(2, line3.p2().y(), 0.000000000000001);
    }
    @Test
    public void testLinesAreInCorrectPositionsAfterRotateFourTimes() {
        triangleGizmo.rotate();
        triangleGizmo.rotate();
        triangleGizmo.rotate();
        triangleGizmo.rotate();
        List<LineSegment> lines = triangleGizmo.getLines();

        LineSegment line1 = lines.get(0);
        LineSegment line2 = lines.get(1);
        LineSegment line3 = lines.get(2);

        assertEquals(1, line1.p1().x(), 0.000000000000001);
        assertEquals(1, line1.p1().y(), 0.000000000000001);
        assertEquals(2, line1.p2().x(), 0.000000000000001);
        assertEquals(1, line1.p2().y(), 0.000000000000001);

        assertEquals(2, line2.p1().x(), 0.000000000000001);
        assertEquals(1, line2.p1().y(), 0.000000000000001);
        assertEquals(1, line2.p2().x(), 0.000000000000001);
        assertEquals(2, line2.p2().y(), 0.000000000000001);

        assertEquals(1, line3.p1().x(), 0.000000000000001);
        assertEquals(2, line3.p1().y(), 0.000000000000001);
        assertEquals(1, line3.p2().x(), 0.000000000000001);
        assertEquals(1, line3.p2().y(), 0.000000000000001);
    }

    @Test
    public void testCirclesAreInCorrectPositions() {
        List<Circle> circles = triangleGizmo.getCircles();

        Circle circle1 = circles.get(0);
        Circle circle2 = circles.get(1);
        Circle circle3 = circles.get(2);

        assertEquals(1, circle1.getCenter().x(), 0.000000000000001);
        assertEquals(1, circle1.getCenter().y(), 0.000000000000001);

        assertEquals(2, circle2.getCenter().x(), 0.000000000000001);
        assertEquals(1, circle2.getCenter().y(), 0.000000000000001);

        assertEquals(1, circle3.getCenter().x(), 0.000000000000001);
        assertEquals(2, circle3.getCenter().y(), 0.000000000000001);
    }

    @Test
    public void testCirclesAreInCorrectPositionsAfterRotate() {
        triangleGizmo.rotate();

        List<Circle> circles = triangleGizmo.getCircles();

        Circle circle1 = circles.get(0);
        Circle circle2 = circles.get(1);
        Circle circle3 = circles.get(2);

        assertEquals(2, circle1.getCenter().x(), 0.000000000000001);
        assertEquals(1, circle1.getCenter().y(), 0.000000000000001);

        assertEquals(2, circle2.getCenter().x(), 0.000000000000001);
        assertEquals(2, circle2.getCenter().y(), 0.000000000000001);

        assertEquals(1, circle3.getCenter().x(), 0.000000000000001);
        assertEquals(1, circle3.getCenter().y(), 0.000000000000001);
    }

    @Test
    public void testCirclesAreInCorrectPositionsAfterRotateTwice() {
        triangleGizmo.rotate();
        triangleGizmo.rotate();

        List<Circle> circles = triangleGizmo.getCircles();

        Circle circle1 = circles.get(0);
        Circle circle2 = circles.get(1);
        Circle circle3 = circles.get(2);

        assertEquals(2, circle1.getCenter().x(), 0.000000000000001);
        assertEquals(2, circle1.getCenter().y(), 0.000000000000001);

        assertEquals(1, circle2.getCenter().x(), 0.000000000000001);
        assertEquals(2, circle2.getCenter().y(), 0.000000000000001);

        assertEquals(2, circle3.getCenter().x(), 0.000000000000001);
        assertEquals(1, circle3.getCenter().y(), 0.000000000000001);
    }

    @Test
    public void testCirclesAreInCorrectPositionsThreeTimes() {
        triangleGizmo.rotate();
        triangleGizmo.rotate();
        triangleGizmo.rotate();

        List<Circle> circles = triangleGizmo.getCircles();

        Circle circle1 = circles.get(0);
        Circle circle2 = circles.get(1);
        Circle circle3 = circles.get(2);

        assertEquals(1, circle1.getCenter().x(), 0.000000000000001);
        assertEquals(2, circle1.getCenter().y(), 0.000000000000001);

        assertEquals(1, circle2.getCenter().x(), 0.000000000000001);
        assertEquals(1, circle2.getCenter().y(), 0.000000000000001);


        assertEquals(2, circle3.getCenter().x(), 0.000000000000001);
        assertEquals(2, circle3.getCenter().y(), 0.000000000000001);
    }

    @Test
    public void testCirclesAreInCorrectPositionsFourTimes() {
        triangleGizmo.rotate();
        triangleGizmo.rotate();
        triangleGizmo.rotate();
        triangleGizmo.rotate();

        List<Circle> circles = triangleGizmo.getCircles();

        Circle circle1 = circles.get(0);
        Circle circle2 = circles.get(1);
        Circle circle3 = circles.get(2);

        assertEquals(1, circle1.getCenter().x(), 0.000000000000001);
        assertEquals(1, circle1.getCenter().y(), 0.000000000000001);

        assertEquals(2, circle2.getCenter().x(), 0.000000000000001);
        assertEquals(1, circle2.getCenter().y(), 0.000000000000001);

        assertEquals(1, circle3.getCenter().x(), 0.000000000000001);
        assertEquals(2, circle3.getCenter().y(), 0.000000000000001);
    }

    @Test
    public void testAngleIs0() {
        double angle = triangleGizmo.getAngle();
        assertEquals(0, angle, 0);
    }

    @Test
    public void testAngleIs90AfterRotate() {
        triangleGizmo.rotate();
        double angle = triangleGizmo.getAngle();
        assertEquals(90, angle, 0);
    }

    @Test
    public void testAngleIs180AfterRotateTwice() {
        triangleGizmo.rotate();
        triangleGizmo.rotate();
        double angle = triangleGizmo.getAngle();
        assertEquals(180, angle, 0);
    }

    @Test
    public void testAngleIs270AfterRotateThreeTimes() {
        triangleGizmo.rotate();
        triangleGizmo.rotate();
        triangleGizmo.rotate();
        double angle = triangleGizmo.getAngle();
        assertEquals(270, angle, 0);
    }

    @Test
    public void testAngleIs0AfterRotateFourTimes() {
        triangleGizmo.rotate();
        triangleGizmo.rotate();
        triangleGizmo.rotate();
        triangleGizmo.rotate();
        double angle = triangleGizmo.getAngle();
        assertEquals(0, angle, 0);
    }

    @Test
    public void testGetCenter() {
        Vect center = triangleGizmo.getCenter();
        assertEquals(new Vect(1.5, 1.5), center);
    }

    @Test
    public void testGetRCoefficient() {
        double rCoefficient = triangleGizmo.getRCoefficient();
        assertEquals(1, rCoefficient, 0);
    }
}