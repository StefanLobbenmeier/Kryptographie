package com.fhswf.kryptographie;

public class ExtendedEuklidianResult {
    private final int rest;
    private final int s;
    private final int t;

    public ExtendedEuklidianResult(int rest, int s, int t) {
        this.rest = rest;
        this.s = s;
        this.t = t;
    }

    public int getRest() {
        return rest;
    }

    public int getS() {
        return s;
    }

    public int getT() {
        return t;
    }
}
