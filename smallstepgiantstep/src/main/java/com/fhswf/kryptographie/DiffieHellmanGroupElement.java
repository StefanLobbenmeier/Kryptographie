package com.fhswf.kryptographie;

import java.math.BigInteger;

public class DiffieHellmanGroupElement implements GroupElement<DiffieHellmanGroupElement> {

    private final DiffieHellmanGroup diffieHellmanGroup;
    private BigInteger value;

    public DiffieHellmanGroupElement(DiffieHellmanGroup diffieHellmanGroup, BigInteger value) {
        this.diffieHellmanGroup = diffieHellmanGroup;
        this.value = value;
    }

    public BigInteger getValue() {
        return value;
    }

    @Override
    public DiffieHellmanGroupElement pow(DiffieHellmanGroupElement e) {
        return diffieHellmanGroup.pow(this, e);
    }

    public DiffieHellmanGroupElement pow(int e) {
        return diffieHellmanGroup.pow(this, e);
    }

    public DiffieHellmanGroupElement multiply(DiffieHellmanGroupElement factorB) {
        return diffieHellmanGroup.multiply(this, factorB);
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

        DiffieHellmanGroupElement that = (DiffieHellmanGroupElement) o;

        if (!diffieHellmanGroup.equals(that.diffieHellmanGroup)) return false;
        return value.equals(that.value);
    }
}
