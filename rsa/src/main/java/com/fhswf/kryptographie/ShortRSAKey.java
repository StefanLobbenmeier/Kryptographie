package com.fhswf.kryptographie;

import sun.security.rsa.RSAPublicKeyImpl;
import sun.security.x509.X509Key;

import java.math.BigInteger;
import java.security.interfaces.RSAPublicKey;

public class ShortRSAKey  extends X509Key implements RSAPublicKey {
    private final BigInteger modulus;
    private final BigInteger exponent;

    public ShortRSAKey(BigInteger modulus, BigInteger exponent) {
        this.modulus = modulus;
        this.exponent = exponent;
    }

    @Override
    public BigInteger getPublicExponent() {
        return exponent;
    }

    @Override
    public String getAlgorithm() {
        return "RSA";
    }

    @Override
    public byte[] getEncoded() {
        return new byte[0];
    }

    @Override
    public BigInteger getModulus() {
        return modulus;
    }
}
