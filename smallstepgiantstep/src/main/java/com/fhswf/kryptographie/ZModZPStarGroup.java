package com.fhswf.kryptographie;

import java.math.BigInteger;

public class ZModZPStarGroup implements Group<ZModZPStarElement> {
    private BigInteger modulus;

    ZModZPStarGroup(BigInteger modulus) {
        this.modulus = modulus;
    }

    ZModZPStarGroup(int modulus) {
        this(BigInteger.valueOf(modulus));
    }

    public ZModZPStarElement getElement(BigInteger value) {
        return new ZModZPStarElement(this, value.mod(modulus));
    }

    public ZModZPStarElement getElement(int value) {
        return getElement(BigInteger.valueOf(value));
    }

    public ZModZPStarElement pow(ZModZPStarElement b, BigInteger e) {
        return getDiffieHellmanGroupElement(b, e);
    }

    public ZModZPStarElement multiply(ZModZPStarElement factorA, ZModZPStarElement factorB) {
        return getElement(factorA.getValue().multiply(factorB.getValue()).mod(modulus));
    }

    public ZModZPStarElement getDiffieHellmanGroupElement(ZModZPStarElement b, BigInteger bigInteger) {
        BigInteger value = b.getValue().modPow(bigInteger, modulus);
        return new ZModZPStarElement(this, value);
    }

    public BigInteger getModulus() {
        return modulus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ZModZPStarGroup that = (ZModZPStarGroup) o;

        return modulus.equals(that.modulus);
    }

    @Override
    public int hashCode() {
        return modulus.hashCode();
    }
}
