package com.fhswf.kryptographie;

import org.junit.Test;

import java.math.BigInteger;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TestZModuloPZStarElements {
    @Test
    public void testTask1Result()
    {
        ZModZPStarGroup group = new ZModZPStarGroup(131);
        ZModZPStarElement base = group.getElement(2);

        assertThat(base.pow(8).getValue(), is(BigInteger.valueOf(125)));
    }

    @Test
    public void testThatGetElementReturnsMembersOfGroup() {
        ZModZPStarGroup group = new ZModZPStarGroup(5);

        assertThat(group.getElement(5).getValue(), is(BigInteger.ZERO));
        assertThat(group.getElement(6).getValue(), is(BigInteger.ONE));

        assertThat(group.getElement(0).getValue(), is(BigInteger.ZERO));
        assertThat(group.getElement(-1).getValue(), is(BigInteger.valueOf(4)));
    }



    @Test
    public void testMultiplyWorks()
    {
        ZModZPStarGroup group = new ZModZPStarGroup(5);
        ZModZPStarElement two = group.getElement(2);
        ZModZPStarElement four = group.getElement(4);

        assertThat(four.multiply(two).getValue(), is(BigInteger.valueOf(3)));
        assertThat(two.multiply(four).getValue(), is(BigInteger.valueOf(3)));
    }

    @Test
    public void testModulusWorks()
    {
        ZModZPStarGroup group = new ZModZPStarGroup(5);
        ZModZPStarElement two = group.getElement(2);
        ZModZPStarElement four = group.getElement(4);

        assertThat(four.pow(2).getValue(), is(BigInteger.ONE));
        assertThat(two.pow(4).getValue(), is(BigInteger.ONE));
    }
}
