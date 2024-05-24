package com.szalay.thinktools.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Contains tests for the factor class.
 */
public class FactorTest {
    @Test
    public void testIsInRange() {
        final Factor factor = new Factor("factor", 0, 2);
        assertTrue(factor.isInRange(1));
    }
    
    @Test
    public void testIsInRange2() {
        final Factor factor = new Factor("factor", 0, 2);
        assertTrue(factor.isInRange(0));
    }
    
    @Test
    public void testIsInRange3() {
        final Factor factor = new Factor("factor", 0, 2);
        assertTrue(factor.isInRange(2));
    }
    
    @Test
    public void testIsInRange4() {
        final Factor factor = new Factor("factor", 0, 2);
        assertFalse(factor.isInRange(3));
    }
}
