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

    @Test
    public void checkUAndV() {

    }

    @Test
    public void f() {

        //Example from Script p. 160

//        assertThat(group.f(BigInteger.valueOf(13)), is(BigInteger.ONE));
//        assertThat(group.f(BigInteger.ZERO), is(BigInteger.ONE));
    }


    @Test
    public void examplePowFromSlides() {
        //Slides p.251
        EllipticCurveActualElement q = group.getElement(4, 1);

        EllipticCurveGroupElement r = q.multiply(q);
        assertThat(r, is(group.getElement(10, 5)));


    }

    @Test
    public void testThatMultiplicationWithInverseYieldsNeutralElement() {

        EllipticCurveActualElement r = group.getElement(10, 5);
        EllipticCurveActualElement invR = group.getElement(10, 12);

        assertThat(r.multiply(invR), is(group.NEUTRAL_ELEMENT));
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

    @Test
    public void multiply() {
    }
}