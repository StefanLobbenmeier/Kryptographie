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
        RSAPublicKey arthursKey = new ShortRSAKey(BigInteger.valueOf(221), BigInteger.valueOf(55));
        RSAPublicKey fordsKey = new ShortRSAKey(BigInteger.valueOf(391), BigInteger.valueOf(3));

        BigInteger commonP = getCommonPrime(arthursKey, fordsKey);
        BigInteger arthursQ = arthursKey.getModulus().divide(commonP);
        BigInteger fordsQ = fordsKey.getModulus().divide(commonP);

        System.out.println(commonP);

        BigInteger arthursD = getD(commonP, arthursQ, arthursKey.getPublicExponent());
        BigInteger fordsD = getD(commonP, fordsQ, fordsKey.getPublicExponent());

        System.out.println(arthursD);
        System.out.println(fordsD);




    }

    private static BigInteger getCommonPrime(RSAPublicKey arthursKey, RSAPublicKey fordsKey) {
        return EuklideanAlgorithm.ggT(arthursKey.getModulus(), fordsKey.getModulus());
    }

    private static BigInteger getD(BigInteger p, BigInteger q, BigInteger e) {
        BigInteger a = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger b = e;
        ExtendedEuklidianResult result = EuklideanAlgorithm.extendedGgT(a, b);

        System.out.println(result);

        BigInteger d = result.getS().multiply(a).add(BigInteger.ONE).divide(e);

        return d;

    }
}
