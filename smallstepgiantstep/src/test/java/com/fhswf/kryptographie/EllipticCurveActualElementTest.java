package com.fhswf.kryptographie;

import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EllipticCurveActualElementTest {

    private EllipticCurveGroup group;

    @Before
    public void prepare() {
        group = new EllipticCurveGroup(1, 1, 17);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatConstructionOfIllegalGroupFails() {
        new EllipticCurveGroup(BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE);
    }

    @Test
    public void f() {

        //Example from Script p. 160

        assertThat(group.liesOnCurve(13, 1), is(true));
        assertThat(group.liesOnCurve(0, 1), is(true));
    }

    @Test
    public void testGetElement() {
        assertThat(group.getElement(0).get(), is(group.getElement(0, 1)));
        assertThat(group.getElement(4).get(), is(group.getElement(4, 1)));
        assertThat(group.getElement(13).get(), is(group.getElement(13, 1)));

        assertThat(group.getElement(10).get(), is(group.getElement(10, 5)));
    }

    @Test
    public void testNegate() {
        assertThat(group.getElement(0, 1).negate(), is(group.getElement(0, 16)));
        assertThat(group.getElement(4, 1).negate(), is(group.getElement(4, 16)));
        assertThat(group.getElement(13, 1).negate(), is(group.getElement(13, 16)));

        assertThat(group.getElement(10, 5).negate(), is(group.getElement(10, 12)));
    }


    @Test
    public void exampleMultiplyFromSlides() {
        //Slides p.251
        EllipticCurveActualElement q = group.getElement(4, 1);

        EllipticCurveGroupElement r = q.multiply(q);
        assertThat(r, is(group.getElement(10, 5)));


    }

    @Test
    public void testThatMultiplicationWithInverseYieldsNeutralElement() {

        EllipticCurveActualElement r = group.getElement(10, 5);
        EllipticCurveActualElement invR = group.getElement(10, 12);

        assertThat(r.multiply(invR), is(EllipticCurveGroup.NEUTRAL_ELEMENT));
    }
    
    @Test 
    public void testOrderOfElement() {
        EllipticCurveActualElement p = group.getElement(6, 6);
        assertThat(p.getOrder(), is(BigInteger.valueOf(18)));

        EllipticCurveActualElement q = group.getElement(4, 1);
        assertThat(q.getOrder(), is(BigInteger.valueOf(6)));

        EllipticCurveActualElement r = group.getElement(10, 5);
        assertThat(r.getOrder(), is(BigInteger.valueOf(3)));
    }

    @Test
    public void examplePowFromScript() {

        //Example from Script p. 160
        EllipticCurveActualElement p = group.getElement(6, 6);

        BigInteger a = BigInteger.valueOf(8);
        BigInteger b = BigInteger.valueOf(13);

        EllipticCurveGroupElement bigA = p.pow(a);
        assertThat(bigA, is(group.getElement(13, 1)));

        EllipticCurveGroupElement bigB = p.pow(b);
        assertThat(bigB, is(group.getElement(0, 1)));


    }
}