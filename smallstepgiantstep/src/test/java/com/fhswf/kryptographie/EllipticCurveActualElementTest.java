package com.fhswf.kryptographie;

import org.junit.Test;

import java.math.BigInteger;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EllipticCurveActualElementTest {

    @Test
    public void checkUAndV() {

    }

    @Test
    public void f() {

        //Example from Script p. 160
        EllipticCurveGroup group = new EllipticCurveGroup(1, 1, 17);

//        assertThat(group.f(BigInteger.valueOf(13)), is(BigInteger.ONE));
//        assertThat(group.f(BigInteger.ZERO), is(BigInteger.ONE));
    }


    @Test
    public void examplePowFromSlides() {
        //Slides p.251
        EllipticCurveGroup group = new EllipticCurveGroup(1, 1, 17);
        EllipticCurveActualElement q = group.getElement(4, 1);

        EllipticCurveGroupElement r = q.multiply(q);
        assertThat(r, is(group.getElement(10, 5)));


    }

    @Test
    public void examplePowFromScript() {

        //Example from Script p. 160
        EllipticCurveGroup group = new EllipticCurveGroup(1, 1, 17);
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