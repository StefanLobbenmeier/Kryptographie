package com.fhswf.kryptographie;

import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

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


        BigInteger sa = result.getS().multiply(BigInteger.valueOf(a));
        BigInteger tb = result.getT().multiply(BigInteger.valueOf(b));

        assertThat(
                String.format("%s * %s + %s * %s is not %s", result.getS(), a, result.getT(), b, ggt),
                        sa.add(tb), is(BigInteger.valueOf(ggt)));


    }
}