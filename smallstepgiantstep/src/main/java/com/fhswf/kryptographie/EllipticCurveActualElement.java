package com.fhswf.kryptographie;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.Objects;

import static com.fhswf.kryptographie.EllipticCurveGroup.NEUTRAL_ELEMENT;

public class EllipticCurveActualElement implements EllipticCurveGroupElement {

    private final EllipticCurveGroup group;
    private final ZModZPStarElement x;
    private final ZModZPStarElement y;

    public EllipticCurveActualElement(EllipticCurveGroup group, ZModZPStarElement x, ZModZPStarElement y) {
        this.group = group;
        this.x = x;
        this.y = y;
    }

    public ZModZPStarElement x() {
        return x;
    }

    public ZModZPStarElement y() {
        return y;
    }

    @Override
    public EllipticCurveGroupElement pow(BigInteger e) {
        if (e.signum() == -1 )
            throw new IllegalArgumentException("Exponent is expected to be positive.");
        if (e.signum() == 0)
            return group.NEUTRAL_ELEMENT;

        int n = e.bitLength();
        EllipticCurveGroupElement s = this;
        for (int i = n - 2; i >= 0; i--) {
            s = s.multiply(s);
            if (e.testBit(i))
                s = s.multiply(this);
        }

        return s;
    }

    public EllipticCurveGroupElement multiply(EllipticCurveGroupElement other) {
        EllipticCurveActualElement a = this;
        if (NEUTRAL_ELEMENT.equals(other)) {
            return this;
        } else if (other instanceof EllipticCurveActualElement) {
            EllipticCurveActualElement b = (EllipticCurveActualElement) other;
            if (a.equals(other)) {
                return square();
            } else if (a.x().equals(b.x())) {
                return NEUTRAL_ELEMENT;
            } else {
                return actualMultiply(a, b);
            }
        } else throw new UnsupportedOperationException("This type is not supported " + other);
    }

    private EllipticCurveGroupElement actualMultiply(EllipticCurveActualElement a, EllipticCurveActualElement b) {
        ZModZPStarElement xc;
        ZModZPStarElement yc;
        ZModZPStarElement m = mForMultiplication(a, b);
        xc = m.multiply(m).subtract(a.x()).subtract(b.x());
        yc = calculateYc(a, xc, m);
        return new EllipticCurveActualElement(group, xc, yc);
    }

    private EllipticCurveGroupElement square() {
        ZModZPStarElement m = mForSquaring();
        ZModZPStarElement m2 = m.multiply(m);
        ZModZPStarElement _2xa = group.getFactor(2).multiply(x);
        ZModZPStarElement xc = m2.subtract(_2xa);
        ZModZPStarElement yc = calculateYc(this, xc, m);
        return new EllipticCurveActualElement(group, xc, yc);
    }

    private ZModZPStarElement mForSquaring() {
        ZModZPStarElement _3xa2 = group.getFactor(3).multiply(x.multiply(x));
        ZModZPStarElement _2ya = group.getFactor(2).multiply(y);
        return _3xa2.add(group.getU()).divide(_2ya);
    }

    private ZModZPStarElement mForMultiplication(EllipticCurveActualElement a, EllipticCurveActualElement b) {
        ZModZPStarElement yd = b.y.subtract(a.y);
        ZModZPStarElement xd = b.x.subtract(a.x);
        return yd.divide(xd);
    }

    private ZModZPStarElement calculateYc(EllipticCurveActualElement a, ZModZPStarElement xc, ZModZPStarElement m) {
        return a.y().negate().subtract(m.multiply(xc.subtract(a.x())));
    }

    @Override
    public String toString() {
        return String.format("P(%s, %s, %s)", x, y, group.getUnderlyingGroup());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EllipticCurveActualElement that = (EllipticCurveActualElement) o;
        return group.equals(that.group) &&
                x.equals(that.x) &&
                y.equals(that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, x, y);
    }

    public BigInteger getOrder() {
        EllipticCurveGroupElement element = this;
        BigInteger order;
        for(order = BigInteger.ONE; element != NEUTRAL_ELEMENT; order = order.add(BigInteger.ONE)) {
            element = element.multiply(this);
        }
        return order;
    }

    public BigInteger getCofactor() {
        return group.getK().divide(getOrder());
    }

    public EllipticCurveActualElement negate() {
        return group.getElement(x, y.negate());
    }
}
