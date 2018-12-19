package com.fhswf.kryptographie;

import org.junit.Test;

import java.math.BigInteger;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TestDiffieHellmanGroup {
    @Test
    public void testTask1Result()
    {
        DiffieHellmanGroup group = new DiffieHellmanGroup(BigInteger.valueOf(131));

        DiffieHellmanGroupElement base = group.getElement(BigInteger.valueOf(2));
        DiffieHellmanGroupElement exponent = group.getElement(BigInteger.valueOf(8));

        assertThat(base.pow(exponent).getValue(), is(BigInteger.valueOf(125)));
    }

    @Test
    public void testModulusWorks()
    {
        DiffieHellmanGroup group = new DiffieHellmanGroup(BigInteger.valueOf(5));
        DiffieHellmanGroupElement two = group.getElement(BigInteger.valueOf(2));
        DiffieHellmanGroupElement four = group.getElement(BigInteger.valueOf(4));

        assertThat(four.pow(two).getValue(), is(BigInteger.ONE));
        assertThat(two.pow(four).getValue(), is(BigInteger.ONE));
    }
}
