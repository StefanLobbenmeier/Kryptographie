package com.fhswf.kryptographie;

import java.math.BigInteger;

public class ExtendedEuklidianResult {
    private final BigInteger rest;
    private final BigInteger s;
    private final BigInteger t;

    public ExtendedEuklidianResult(BigInteger rest, BigInteger s, BigInteger t) {
        this.rest = rest;
        this.s = s;
        this.t = t;
    }

    public BigInteger getRest() {
        return rest;
    }

    public BigInteger getS() {
        return s;
    }

    public BigInteger getT() {
        return t;
    }

    @Override
    public String toString() {
        return "ExtendedEuklidianResult{" +
                "rest=" + rest +
                ", s=" + s +
                ", t=" + t +
                '}';
    }
}
