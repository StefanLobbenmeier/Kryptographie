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
}