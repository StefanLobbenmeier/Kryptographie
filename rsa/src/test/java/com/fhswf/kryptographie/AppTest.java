package com.fhswf.kryptographie;

import org.junit.Test;

import java.math.BigInteger;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    @Test
    public void testRsa() {
        BigInteger p = new BigInteger("13");
        BigInteger q = new BigInteger("17");
        BigInteger e = new BigInteger("7");

        testGetD(BigInteger.valueOf(13), BigInteger.valueOf(17), BigInteger.valueOf(7));
        testGetD(BigInteger.valueOf(13), BigInteger.valueOf(17), BigInteger.valueOf(5));
        testGetD(BigInteger.valueOf(13), BigInteger.valueOf(17), BigInteger.valueOf(11));
    }

    private void testGetD(BigInteger p, BigInteger q, BigInteger e) {
        BigInteger n = p.multiply(q);

        BigInteger m = new BigInteger("5");

        BigInteger d = App.getD(p, q, e);

        BigInteger c = m.modPow(e, n);
        System.out.printf("c = m ^ e = %s ^ %s = %s%n", m, e, c);
        System.out.printf("m = c ^ d = %s ^ %s = %s%n", c, d, c.modPow(d, n));

        assertThat(c.modPow(d, n), is(m));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCommonFactorYieldsException() {
        BigInteger e = new BigInteger("7");

        BigInteger p = e.add(BigInteger.ONE);
        BigInteger q = new BigInteger("17");

        App.getD(p, q, e);
    }
}
