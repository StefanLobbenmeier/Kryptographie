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
        DiffieHellmanGroup group = new DiffieHellmanGroup(BigInteger.probablePrime(100, random));

        DiffieHellmanGroupElement g = group.getElement(BigInteger.probablePrime(50, random));
        DiffieHellmanGroupElement alice = group.getElement(BigInteger.probablePrime(50, random));
        DiffieHellmanGroupElement bob = group.getElement(BigInteger.probablePrime(50, random));

        DiffieHellmanGroupElement bigA = g.pow(alice);
        DiffieHellmanGroupElement bigB = g.pow(bob);


        assertThat(bigA.pow(bob).getValue(), is(bigB.pow(alice).getValue()));
    }
}
