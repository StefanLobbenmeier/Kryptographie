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

    @Override
    public String toString() {
        return String.format("Z/%sZ", modulus);
    }
}
