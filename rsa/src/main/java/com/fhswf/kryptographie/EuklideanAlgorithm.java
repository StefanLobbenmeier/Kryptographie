package com.fhswf.kryptographie;

import java.math.BigInteger;

public class EuklideanAlgorithm {
    public static int ggT(int a, int b) {
        return extendedGgT(a, b).getRest().intValue();
    }
    public static BigInteger ggT(BigInteger a, BigInteger b) {
        return extendedGgT(a, b).getRest();
    }

    public static ExtendedEuklidianResult extendedGgT(BigInteger a, BigInteger b) {
        BigInteger s0 = BigInteger.ZERO;
        BigInteger t0 = BigInteger.ONE;
        BigInteger r0 = b;

        BigInteger q1 = a.divide(b);
        BigInteger r1 = a.remainder(b);
        BigInteger s1 = BigInteger.ONE;
        BigInteger t1 = q1.negate();

        while (r1.compareTo(BigInteger.ZERO) != 0) {
            a = b;
            b = r1;
            q1 = a.divide(b);
            r0 = r1;
            r1 = a.remainder(b);

            BigInteger s2 = s0.subtract(q1.multiply(s1));
            s0 = s1;
            s1 = s2;

            BigInteger t2 = t0.subtract(q1.multiply(t1));
            t0 = t1;
            t1 = t2;
        }

        return new ExtendedEuklidianResult(r0,s0,t0);
    }

    public static ExtendedEuklidianResult extendedGgT(int a, int b) {
        return extendedGgT(BigInteger.valueOf(a), BigInteger.valueOf(b));
    }
}
