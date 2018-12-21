package com.fhswf.kryptographie;

import java.math.BigInteger;

public class ZModZPStarElement implements GroupElement<ZModZPStarElement> {

    private final ZModZPStarGroup zModZPStarGroup;
    private BigInteger value;

    public ZModZPStarElement(ZModZPStarGroup zModZPStarGroup, BigInteger value) {
        this.zModZPStarGroup = zModZPStarGroup;
        this.value = value;
    }

    public BigInteger getValue() {
        return value;
    }

    @Override
    public ZModZPStarElement pow(BigInteger e) {
        // Since this uses modPow anyway, we dont need group.getElement
        BigInteger value = getValue().modPow(e, zModZPStarGroup.getModulus());
        return new ZModZPStarElement(zModZPStarGroup, value);
    }

    public ZModZPStarElement multiply(ZModZPStarElement factorB) {
        return zModZPStarGroup.getElement(getValue().multiply(factorB.getValue()));
    }

    public ZModZPStarElement divide(ZModZPStarElement dividend) {
        return this.multiply(dividend.getValue().modInverse(zModZPStarGroup.getModulus()));
    }

    private ZModZPStarElement multiply(BigInteger factorB) {
        return this.multiply(zModZPStarGroup.getElement(factorB));
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public int hashCode() {
        int result = zModZPStarGroup.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ZModZPStarElement that = (ZModZPStarElement) o;

        if (!zModZPStarGroup.equals(that.zModZPStarGroup)) return false;
        return value.equals(that.value);
    }

    public ZModZPStarElement subtract(ZModZPStarElement b) {
        return zModZPStarGroup.getElement(value.subtract(b.value));
    }

    public ZModZPStarElement negate() {
        return zModZPStarGroup.getElement(value.negate());
    }

    public ZModZPStarElement add(ZModZPStarElement b) {
        return zModZPStarGroup.getElement(value.add(b.value));
    }
}
