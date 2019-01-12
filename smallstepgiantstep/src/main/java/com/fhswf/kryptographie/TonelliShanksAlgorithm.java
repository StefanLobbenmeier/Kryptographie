package com.fhswf.kryptographie;

import org.apache.commons.lang3.tuple.Pair;

import java.math.BigInteger;
import java.util.Optional;


public class TonelliShanksAlgorithm {

    public static final int SQAURE = 1;
    private ZModZPStarElement ZERO;
    private ZModZPStarElement ONE;
    private ZModZPStarElement TWO;

    private BigInteger p;
    private BigInteger q;
    private ZModZPStarElement s;
    private ZModZPStarElement r;
    private ZModZPStarElement t;
    private ZModZPStarElement m;
    private ZModZPStarElement c;
    private ZModZPStarGroup group;
    private ZModZPStarElement n;

    TonelliShanksAlgorithm(ZModZPStarGroup group) {
        this.group = group;

        p = group.getModulus();
        ZERO = group.getElement(0);
        ONE = group.getElement(1);
        TWO = group.getElement(2);
    }

    /**
     * https://rosettacode.org/wiki/Tonelli-Shanks_algorithm
     *
     * @param n an element of (Z/pZ) such that solutions to the congruence r2 = n exist;
     * @return r in (Z/pZ) such that r^2 = n
     */
        public Optional<ZModZPStarElement> root(ZModZPStarElement n) {
            this.n = n;
            // Step 0. Check that n is indeed a square  : (n | p) must be ≡ 1
            if (SQAURE == legendreSymbol(n)) {

            //Step 1. [Factors out powers of 2 from p-1] Define q -odd- and s such as p-1 = q * 2^s
            factorOutTwos(p.subtract(BigInteger.ONE));

            if (ONE.equals(s))
                return specialCaseR();

            ZModZPStarElement z = selectNonSquare();
            c = z.pow(q);

            r = n.pow(q.add(BigInteger.ONE).divide(BigInteger.valueOf(2)));
            t = n.pow(q);
            m = s;

            return loop();

        }
        return Optional.empty();
    }

    private Optional<ZModZPStarElement> loop() {
        while (true) {
            if (ONE.equals(t))
                return lowerRoot();


            boolean found = false;
            ZModZPStarElement i;
            for(i = ONE; i.compareTo(m) == -1; i = i.add(ONE)) {
                if(ONE.equals(t.pow(TWO.pow(i.getValue()).getValue()))) {
                    found = true;
                    break;
                }
            }
            if(found) {
                ZModZPStarElement b = c.pow(TWO.pow(m.subtract(i).subtract(ONE).getValue()).getValue());
                r = r.multiply(b);
                t = t.multiply(b.pow(2));
                c = b.pow(2);
                m = i;
            } else {
                throw new IllegalArgumentException("Should not happen, but apparently this doesnt work");
            }
        }

    }

    private Optional<ZModZPStarElement> lowerRoot() {
        if (r.getValue().compareTo(p.divide(BigInteger.valueOf(2))) == 1)
            r = r.negate();
        return Optional.of(r);
    }

    private ZModZPStarElement selectNonSquare() {
        ZModZPStarElement z = TWO;
        while (true) {
            int legendreSymbol = legendreSymbol(z);
            if (SQAURE == legendreSymbol || 0 == legendreSymbol)
                z = z.add(ONE);
            else break;
        }
        return z;
    }

    private Optional<ZModZPStarElement> specialCaseR() {
        return Optional.of(n.pow(p.add(BigInteger.ONE).divide(BigInteger.valueOf(4))));
    }

    private void factorOutTwos(BigInteger p) {
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
    int legendreSymbol(ZModZPStarElement a) {
        return a.pow(p.subtract(ONE.getValue()).divide(BigInteger.valueOf(2))).intValue();
    }


}
