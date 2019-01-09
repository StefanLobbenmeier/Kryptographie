package com.fhswf.kryptographie;

import org.apache.commons.lang3.tuple.Pair;

import java.math.BigInteger;
import java.util.Optional;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

public class TonelliShanksAlgorithm {

    public static final int SQAURE = 1;
    public static final BigInteger TWO = BigInteger.valueOf(2);

    private static BigInteger p;
    private static BigInteger n;
    private static BigInteger q;
    private static BigInteger s;
    private static BigInteger r;
    private static BigInteger t;
    private static BigInteger m;
    private static BigInteger c;

    /**
     * https://rosettacode.org/wiki/Tonelli-Shanks_algorithm
     *
     * @param p A prime
     * @param n an element of (Z/pZ) such that solutions to the congruence r2 = n exist;
     * @return r in (Z/pZ) such that r^2 = n
     */
    public static Optional<BigInteger> root(BigInteger p, BigInteger n) {
        TonelliShanksAlgorithm.p = p;
        TonelliShanksAlgorithm.n = n;
        // Step 0. Check that n is indeed a square  : (n | p) must be ≡ 1
        if (legendreSymbol(n) == SQAURE) {

            //Step 1. [Factors out powers of 2 from p-1] Define q -odd- and s such as p-1 = q * 2^s
            factorOutTwos(p.subtract(ONE));

            if (ONE.equals(s))
                return specialCaseR();

            BigInteger z = selectNonSquare();
            c = z.modPow(q, p);

            r = n.modPow(q.add(ONE).divide(TWO), p);
            t = n.modPow(q, p);
            m = s;

            return loop();

        }
        return Optional.empty();
    }

    private static Optional<BigInteger> loop() {
        while (true) {
            if (ONE.equals(t))
                return Optional.of(r);


            boolean found = false;
            BigInteger i;
            for(i = ONE; i.compareTo(m) == -1; i = i.add(ONE)) {
                if(ONE.equals(t.modPow(TWO.modPow(i, p), p))) {
                    found = true;
                    break;
                }
            }
            if(found) {
                BigInteger b = c.modPow(TWO.modPow(m.subtract(i).subtract(ONE), p), p);
                r = r.multiply(b).mod(p);
                t = t.multiply(b.pow(2)).mod(p);
                c = b.pow(2).mod(p);
                m = i;
            } else {
                throw new IllegalArgumentException("Should not happen, but apparently this doesnt work");
            }
        }

    }

    private static BigInteger selectNonSquare() {
        BigInteger z = TWO;
        while (true) {
            int legendreSymbol = legendreSymbol(z);
            if (SQAURE == legendreSymbol || 0 == legendreSymbol)
                z = z.add(ONE);
            else break;
        }
        return z;
    }

    private static Optional<BigInteger> specialCaseR() {
        return Optional.of(n.modPow(p.add(ONE).divide(BigInteger.valueOf(4)), n));
    }

    private static void factorOutTwos(BigInteger p) {
        q = p;
        s = ZERO;
        while (BigIntegerUtil.isEven(q)) {
            s = s.add(ONE);
            q = q.shiftRight(1);
        }
    }

    /**
     * @return SQAURE if a is a square, 0 if a is 0 and NOT_SQUARE if a is not a square
     */
    static int legendreSymbol(BigInteger a) {
        return a.modPow(p.subtract(ONE).divide(BigInteger.valueOf(2)), p).intValue();
    }


}
