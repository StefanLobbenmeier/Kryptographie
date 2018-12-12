package com.fhswf.kryptographie;

import java.math.BigInteger;

public class ShortRSAKey {
    private final BigInteger modulus;
    private final BigInteger exponent;

    public ShortRSAKey(BigInteger modulus, BigInteger exponent) {
        this.modulus = modulus;
        this.exponent = exponent;
    }

    public BigInteger getPublicExponent() {
        return exponent;
    }

    public String getAlgorithm() {
        return "RSA";
    }

    public BigInteger crypt(BigInteger data) {
        return data.modPow(exponent, modulus);
    }

    public BigInteger getModulus() {
        return modulus;
    }
}
