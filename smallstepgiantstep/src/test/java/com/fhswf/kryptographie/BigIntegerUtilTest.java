package com.fhswf.kryptographie;

import org.junit.Test;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.valueOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BigIntegerUtilTest {

    @Test
    public void bigIntSqRootCeil() {
        assertBigIntSqRootCeil(0, 0);
        assertBigIntSqRootCeil(1, 1);

        assertBigIntSqRootCeil(2, 2);
        assertBigIntSqRootCeil(3, 2);
        assertBigIntSqRootCeil(4, 2);

        //make sure that even calculated ones are identified
        BigInteger two = valueOf(2);
        assertThat(BigIntegerUtil.bigIntSqRootCeil(two.divide(two)), is(ONE));

        BigInteger largeNumber = new BigInteger("123456789");
        assertBigIntSqRootCeil(largeNumber.pow(2), largeNumber);
        assertBigIntSqRootCeil(largeNumber.pow(2).add(ONE), largeNumber.add(ONE));
        assertBigIntSqRootCeil(largeNumber.pow(2).subtract(ONE), largeNumber);
    }

    private void assertBigIntSqRootCeil(int value, int root) {
        assertBigIntSqRootCeil(valueOf(value), valueOf(root));
    }

    private void assertBigIntSqRootCeil(BigInteger value, BigInteger root) {
        assertThat(BigIntegerUtil.bigIntSqRootCeil(value), is(root));
    }


    @Test
    public void testIsEven() {
        assertBigIntEven(0, true);
        assertBigIntEven(2, true);
        assertBigIntEven(-2, true);

        assertBigIntEven(-1, false);
        assertBigIntEven(1, false);
    }

    private void assertBigIntEven(int i, boolean b) {
        assertBigIntEven(BigInteger.valueOf(i), b);
    }

    private void assertBigIntEven(BigInteger i, boolean b) {
        assertThat(BigIntegerUtil.isEven(i), is(b));
    }


}