package com.fhswf.kryptographie;

import java.math.BigInteger;

public class EuklideanAlgorithm {
    public static int ggT(int a, int b) {
        return extendedGgT(a, b).getRest();
    }

    public static ExtendedEuklidianResult extendedGgT(int a, int b) {
        int s0 = 0;
        int t0 = 1;
        int r0 = b;

        int q1 = a / b;
        int r1 = a % b;
        int s1 = 1;
        int t1 = -q1;

        while (r1 != 0) {
            a = b;
            b = r1;
            q1 = a / b;
            r0 = r1;
            r1 = a % b;

            int s2 = s0 - q1 * s1;
            s0 = s1;
            s1 = s2;

            int t2 = t0 - q1 * t1;
            t0 = t1;
            t1 = t2;
        }

        return new ExtendedEuklidianResult(r0,s0,t0);
    }

    public static BigInteger ggT(BigInteger modulus, BigInteger modulus1) {
        return BigInteger.ZERO;
    }
}
