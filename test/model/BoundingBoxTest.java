package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoundingBoxTest {

    private BoundingBox bound1;

    @Before
    public void setUp() {
        bound1 = new BoundingBox(1,1,2,2);

    }

    @Test
    public void testIsIntersectingWithSameBoundingBoxInSameLocation() {
        BoundingBox bound2 = new BoundingBox(1,1,2,2);
        boolean isIntersecting = bound2.isIntersecting(bound1);
        assertTrue(isIntersecting);
    }

    @Test
    public void testIsNotIntersectingWithSameBoundingBoxInDifferentLocation() {
        BoundingBox bound2 = new BoundingBox(5,5,6,6);
        boolean isIntersecting = bound2.isIntersecting(bound1);
        assertFalse(isIntersecting);
    }

    @Test
    public void testIsNotIntersectingRight() {
        BoundingBox bound2 = new BoundingBox(2,1,3,2);
        boolean isIntersecting = bound2.isIntersecting(bound1);
        assertFalse(isIntersecting);
    }

    @Test
    public void testIsNotIntersectingLeft() {
        BoundingBox bound2 = new BoundingBox(0,1,1,2);
        boolean isIntersecting = bound2.isIntersecting(bound1);
        assertFalse(isIntersecting);
    }

    @Test
    public void testIsNotIntersectingTop() {
        BoundingBox bound2 = new BoundingBox(1,0,2,1);
        boolean isIntersecting = bound2.isIntersecting(bound1);
        assertFalse(isIntersecting);
    }

    @Test
    public void testIsNotIntersectingBottom() {
        BoundingBox bound2 = new BoundingBox(1,2,2,3);
        boolean isIntersecting = bound2.isIntersecting(bound1);
        assertFalse(isIntersecting);
    }

    @Test
    public void testIsNotIntersectingTopRight() {
        BoundingBox bound2 = new BoundingBox(2,0,3,1);
        boolean isIntersecting = bound2.isIntersecting(bound1);
        assertFalse(isIntersecting);
    }

    @Test
    public void testIsNotIntersectingTopLeft() {
        BoundingBox bound2 = new BoundingBox(0,0,1,1);
        boolean isIntersecting = bound2.isIntersecting(bound1);
        assertFalse(isIntersecting);
    }

    @Test
    public void testIsNotIntersectingBottomRight() {
        BoundingBox bound2 = new BoundingBox(2,2,3,3);
        boolean isIntersecting = bound2.isIntersecting(bound1);
        assertFalse(isIntersecting);
    }

    @Test
    public void testIsNotIntersectingBottomLeft() {
        BoundingBox bound2 = new BoundingBox(0,2,1,3);
        boolean isIntersecting = bound2.isIntersecting(bound1);
        assertFalse(isIntersecting);
    }

    @Test
    public void testIsIntersectingTopLeftCorner() {
        BoundingBox bound2 = new BoundingBox(1.5,1.5,2.5,2.5);
        boolean isIntersecting = bound2.isIntersecting(bound1);
        assertTrue(isIntersecting);
    }

    @Test
    public void testIsIntersectingTopRightCorner() {
        BoundingBox bound2 = new BoundingBox(0.5,1.5,1.5,2.5);
        boolean isIntersecting = bound2.isIntersecting(bound1);
        assertTrue(isIntersecting);
    }

    @Test
    public void testIsIntersectingBottomLeftCorner() {
        BoundingBox bound2 = new BoundingBox(1.5,0.5,2.5,1.5);
        boolean isIntersecting = bound2.isIntersecting(bound1);
        assertTrue(isIntersecting);
    }

    @Test
    public void testIsIntersectingBottomRightCorner() {
        BoundingBox bound2 = new BoundingBox(0.5,0.5,1.5,1.5);
        boolean isIntersecting = bound2.isIntersecting(bound1);
        assertTrue(isIntersecting);
    }

    @Test
    public void testIsIntersectingSmallInsideBig() {
        BoundingBox bound2 = new BoundingBox(1.25,1.25,1.75,1.75);
        boolean isIntersecting = bound2.isIntersecting(bound1);
        assertTrue(isIntersecting);
    }

    @Test
    public void testIsIntersectingBigOverTopOfSmall() {
        BoundingBox bound2 = new BoundingBox(1.25,1.25,1.75,1.75);
        boolean isIntersecting = bound1.isIntersecting(bound2);
        assertTrue(isIntersecting);
    }
}