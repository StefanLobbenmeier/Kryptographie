package com.fhswf.kryptographie;

import java.math.BigInteger;

public class ZModZPStarElement implements GroupElement<ZModZPStarElement> {

    private final ZModZPStarGroup diffieHellmanGroup;
    private BigInteger value;

    public ZModZPStarElement(ZModZPStarGroup diffieHellmanGroup, BigInteger value) {
        this.diffieHellmanGroup = diffieHellmanGroup;
        this.value = value;
    }

    public BigInteger getValue() {
        return value;
    }

    @Override
    public ZModZPStarElement pow(BigInteger e) {
        return diffieHellmanGroup.pow(this, e);
    }

    public ZModZPStarElement multiply(ZModZPStarElement factorB) {
        return diffieHellmanGroup.multiply(this, factorB);
    }

    public ZModZPStarElement divide(ZModZPStarElement divident) {
        return this.multiply(divident.getValue().modInverse(diffieHellmanGroup.getModulus()));
    }

    private ZModZPStarElement multiply(BigInteger factorB) {
        return this.multiply(diffieHellmanGroup.getElement(factorB));
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public int hashCode() {
        int result = diffieHellmanGroup.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ZModZPStarElement that = (ZModZPStarElement) o;

        if (!diffieHellmanGroup.equals(that.diffieHellmanGroup)) return false;
        return value.equals(that.value);
    }

    public ZModZPStarElement subtract(ZModZPStarElement b) {
        return diffieHellmanGroup.getElement(value.subtract(b.value));
    }

    public ZModZPStarElement negate() {
        return diffieHellmanGroup.getElement(value.negate());
    }

    public ZModZPStarElement add(ZModZPStarElement b) {
        return diffieHellmanGroup.getElement(value.add(b.value));
    }
}
