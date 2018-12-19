package com.fhswf.kryptographie;

import java.math.BigInteger;

public class DiffieHellmanGroup implements Group<DiffieHellmanGroupElement> {
    private BigInteger modulus;

    DiffieHellmanGroup(BigInteger modulus) {
        this.modulus = modulus;
    }

    @Override
    public DiffieHellmanGroupElement getElement(BigInteger value) {
        return new DiffieHellmanGroupElement(this, value);
    }

    public DiffieHellmanGroupElement pow(DiffieHellmanGroupElement b, DiffieHellmanGroupElement e) {
        return getDiffieHellmanGroupElement(b, e.getValue());
    }

    public DiffieHellmanGroupElement pow(DiffieHellmanGroupElement b, int e) {
        return getDiffieHellmanGroupElement(b, BigInteger.valueOf(e));
    }

    @Override
    public DiffieHellmanGroupElement multiply(DiffieHellmanGroupElement factorA, DiffieHellmanGroupElement factorB) {
        return getElement(factorA.getValue().multiply(factorB.getValue()).mod(modulus));
    }

    public DiffieHellmanGroupElement getDiffieHellmanGroupElement(DiffieHellmanGroupElement b, BigInteger bigInteger) {
        BigInteger value = b.getValue().modPow(bigInteger, modulus);
        return new DiffieHellmanGroupElement(this, value);
    }

    public BigInteger getModulus() {
        return modulus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DiffieHellmanGroup that = (DiffieHellmanGroup) o;

        return modulus.equals(that.modulus);
    }

    @Override
    public int hashCode() {
        return modulus.hashCode();
    }
}
