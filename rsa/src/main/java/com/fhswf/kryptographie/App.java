package com.fhswf.kryptographie;

import sun.security.rsa.RSAPublicKeyImpl;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.interfaces.RSAPublicKey;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InvalidKeyException
    {
        RSAPublicKey arthursKey = new RSAPublicKeyImpl(BigInteger.valueOf(221), BigInteger.valueOf(55));
        RSAPublicKey fordsKey = new RSAPublicKeyImpl(BigInteger.valueOf(391), BigInteger.valueOf(3));

        BigInteger commonP = getCommonPrime(arthursKey, fordsKey);

        System.out.println( "Hello World!");
    }

    private static BigInteger getCommonPrime(RSAPublicKey arthursKey, RSAPublicKey fordsKey) {
        return EuklideanAlgorithm.ggT(arthursKey.getModulus(), fordsKey.getModulus());
    }
}
