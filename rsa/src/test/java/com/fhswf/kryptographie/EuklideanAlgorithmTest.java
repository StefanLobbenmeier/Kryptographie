package com.fhswf.kryptographie;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class EuklideanAlgorithmTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGgt() {
        testGgt(7, 3, 1);
        testGgt(1, 1, 1);
        testGgt(3, 3, 3);
        testGgt(15, 20, 5);
    }

    private void testGgt(int a, int b, int ggt) {
        assertThat(EuklideanAlgorithm.ggT(a, b), is(ggt));
        assertThat(EuklideanAlgorithm.ggT(b, a), is(ggt));
    }



    @Test
    public void testExtendedGgt() {
        testExtendedGgt(21, 14, 7);
        testExtendedGgt(200, 100, 100);
        testExtendedGgt(3, 3, 3);
        testExtendedGgt(20, 15, 5);
    }

    private void testExtendedGgt(int a, int b, int ggt) {
        ExtendedEuklidianResult result = EuklideanAlgorithm.extendedGgT(a, b);

        assertThat(
                String.format("%d * %d + %d * %d is not %d", result.getS(), a, result.getT(), b, ggt),
                        result.getS() * a + result.getT() * b, is(ggt));
    }
}