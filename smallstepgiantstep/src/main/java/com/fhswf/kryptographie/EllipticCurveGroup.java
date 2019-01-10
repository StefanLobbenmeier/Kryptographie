package com.fhswf.kryptographie;

import org.apache.commons.lang3.Range;

import java.math.BigInteger;
import java.util.Optional;


public class EllipticCurveGroup implements Group<EllipticCurveGroupElement> {
    static final EllipticCurveGroupElement NEUTRAL_ELEMENT = EllipticCurveNeutralElement.getNeutralElement();
    private final ZModZPStarElement u;
    private final ZModZPStarElement v;
    private final ZModZPStarGroup group;

    EllipticCurveGroup(int u, int v, int k) {
        this(BigInteger.valueOf(u), BigInteger.valueOf(v), BigInteger.valueOf(k));
    }

    EllipticCurveGroup(BigInteger u, BigInteger v, BigInteger modulus) {
        group = new ZModZPStarGroup(modulus);
        this.u = group.getElement(u);
        this.v = group.getElement(v);

        checkUAndV();
    }

    /**
     * @throws IllegalArgumentException if 4*u^3 + 27*v^2 == 0
     */
    void checkUAndV() {
        ZModZPStarElement _4u3 = group.getElement(4).multiply(u.pow(3));
        ZModZPStarElement _27v2 = group.getElement(27).multiply(v.pow(2));

        if (_4u3.add(_27v2).equals(group.getElement(BigInteger.ZERO))) {
            throw new IllegalArgumentException("Criteria 4*u^3 + 27*v^2 == 0 is not fulfilled");
        }
    }

    /**
     * @return y ^ 2 == (x ^ 3 + u * x + v), mod modulus
     */
    private boolean liesOnCurve(ZModZPStarElement x, ZModZPStarElement y) {
        ZModZPStarElement leftSide = y.pow(2);

        ZModZPStarElement rightSide = rightSideOfEccPointDefinition(x);

        return leftSide.equals(rightSide);
    }

    private ZModZPStarElement rightSideOfEccPointDefinition(ZModZPStarElement x) {
        return x.pow(3)
                .add(u.multiply(x))
                .add(v);
    }

    public boolean liesOnCurve(int x, int y) {
        return liesOnCurve(getUnderlyingGroup().getElement(x), getUnderlyingGroup().getElement(y));
    }

    public EllipticCurveActualElement getElement(ZModZPStarElement x, ZModZPStarElement y) {
        if (!liesOnCurve(x, y))
            throw new IllegalArgumentException(String.format("The Point P(%s, %s) does not lie on the curve)", x, y));
        return new EllipticCurveActualElement(this, x, y);
    }

    public EllipticCurveActualElement getElement(int x, int y) {
        return getElement(group.getElement(x), group.getElement(y));
    }

    public Optional<EllipticCurveActualElement> getElement(final ZModZPStarElement x) {
        return rightSideOfEccPointDefinition(x).root().map(y -> getElement(x, y));
    }

    public Optional<EllipticCurveActualElement> getElement(int x) {
        return getElement(group.getElement(x));
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

    /**
     * @return The hasse Interval: [2 * √k - (k+1); 2 * √k + (k+1)]
     */
    public Range<BigInteger> hasseInterval() {
        BigInteger k = getK();
        BigInteger _2TimesRootK = BigInteger.valueOf(2).multiply(BigIntegerUtil.bigIntSqRootCeil(k));
        BigInteger kPlus1 = k.add(BigInteger.ONE);
        BigInteger lBound = kPlus1.subtract(_2TimesRootK);
        BigInteger uBound = kPlus1.add(_2TimesRootK);
        return Range.between(lBound, uBound);
    }
}
