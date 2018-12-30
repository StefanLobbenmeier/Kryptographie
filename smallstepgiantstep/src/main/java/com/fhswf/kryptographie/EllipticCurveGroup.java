package com.fhswf.kryptographie;

import org.apache.commons.lang3.Range;

import java.math.BigInteger;

public class EllipticCurveGroup implements Group<EllipticCurveGroupElement> {
    static final EllipticCurveGroupElement NEUTRAL_ELEMENT = EllipticCurveNeutralElement.getNeutralElement();
    private final ZModZPStarElement u;
    private final ZModZPStarElement v;
    private final ZModZPStarGroup group;

    EllipticCurveGroup(int u, int v, int k) {
        this(BigInteger.valueOf(u), BigInteger.valueOf(v), BigInteger.valueOf(k));
    }

    EllipticCurveGroup(BigInteger u, BigInteger v, BigInteger modulus) {
        checkUAndV(u, v);

        group = new ZModZPStarGroup(modulus);
        this.u = group.getElement(u);
        this.v = group.getElement(u);
    }

    /**
     * @throws IllegalArgumentException if 4*u^3 + 27*v^2 == 0
     */
    static void checkUAndV(BigInteger u, BigInteger v) {
        BigInteger _4u3 = BigInteger.valueOf(4).multiply(u.pow(3));
        BigInteger _27v2 = BigInteger.valueOf(27).multiply(v.pow(2));

        if (_4u3.add(_27v2).equals(BigInteger.ZERO)) {
            throw new IllegalArgumentException("Criteria 4*u^3 + 27*v^2 == 0 is not fulfilled");
        }
    }

    /**
     * @return (x ^ 3 + u * x + v) mod modulus
     */
    private boolean liesOnCurve(ZModZPStarElement x, ZModZPStarElement y) {
        ZModZPStarElement leftSide = y.pow(2);

        ZModZPStarElement rightSide = x.pow(3)
                .add(u.multiply(x))
                .add(v);

        return leftSide.equals(rightSide);
    }

    public boolean liesOnCurve(int x, int y) {
        return liesOnCurve(getUnderlyingGroup().getElement(x), getUnderlyingGroup().getElement(y));
    }

    private EllipticCurveActualElement getElement(ZModZPStarElement x, ZModZPStarElement y) {
        if (!liesOnCurve(x, y))
            throw new IllegalArgumentException(String.format("The Point P(%s, %s) does not lie on the curve)", x, y));
        return new EllipticCurveActualElement(this, x, y);
    }

    public EllipticCurveActualElement getElement(int x, int y) {
        return getElement(group.getElement(x), group.getElement(y));
    }

    public ZModZPStarElement getU() {
        return u;
    }

    public ZModZPStarGroup getUnderlyingGroup() {
        return group;
    }

    public ZModZPStarElement getFactor(int i) {
        return group.getElement(i);
    }

    public BigInteger getK() {
        return getUnderlyingGroup().getModulus();
    }

    public Range<BigInteger> hasseInterval() {
        BigInteger p = getK();
        BigInteger _2TimesRootP = BigInteger.valueOf(2).multiply(BigIntegerUtil.bigIntSqRootCeil(p));
        BigInteger pPlus1 = p.add(BigInteger.ONE);
        BigInteger lBound = pPlus1.subtract(_2TimesRootP);
        BigInteger uBound = pPlus1.add(_2TimesRootP);
        return Range.between(lBound, uBound);
    }
}
