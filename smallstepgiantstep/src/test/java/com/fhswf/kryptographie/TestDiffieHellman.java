package com.fhswf.kryptographie;

import org.junit.Test;

import java.math.BigInteger;
import java.security.SecureRandom;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Unit test for simple App.
 */
public class TestDiffieHellman
{
    @Test
    public void testDiffieHellman() {
        SecureRandom random = new SecureRandom();
        ZModZPStarGroup group = new ZModZPStarGroup(BigInteger.probablePrime(100, random));

        ZModZPStarElement g = group.getElement(BigInteger.probablePrime(50, random));
        ZModZPStarElement alice = group.getElement(BigInteger.probablePrime(50, random));
        ZModZPStarElement bob = group.getElement(BigInteger.probablePrime(50, random));

        ZModZPStarElement bigA = g.pow(alice.getValue());
        ZModZPStarElement bigB = g.pow(bob.getValue());


        assertThat(bigA.pow(bob.getValue()).getValue(), is(bigB.pow(alice.getValue()).getValue()));
    }
}
