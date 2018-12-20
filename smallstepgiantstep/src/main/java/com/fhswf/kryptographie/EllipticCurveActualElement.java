package com.fhswf.kryptographie;

import java.math.BigInteger;
import java.util.Objects;

import static com.fhswf.kryptographie.EllipticCurveGroup.NEUTRAL_ELEMENT;

public class EllipticCurveActualElement implements EllipticCurveGroupElement {

    private final EllipticCurveGroup group;
    private ZModZPStarElement x;
    private ZModZPStarElement y;

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
        int factors = e.bitLength();

        BigInteger currentE = e;
        EllipticCurveGroupElement baseToTwoIModK = this;
        EllipticCurveGroupElement currentResult = NEUTRAL_ELEMENT;
        for (int i = 0; i < factors; i++) {
            EllipticCurveGroupElement factor;
            if (BigIntegerUtil.isEven(currentE))
                factor = NEUTRAL_ELEMENT;
            else
                factor = baseToTwoIModK;

            currentResult = currentResult.multiply(factor);

            baseToTwoIModK = baseToTwoIModK.multiply(baseToTwoIModK);
            currentE = currentE.shiftRight(1);
        }

        return currentResult;
    }

    public EllipticCurveGroupElement multiply(EllipticCurveGroupElement other) {
        EllipticCurveActualElement a = this;
        ZModZPStarElement xc, yc;
        if (NEUTRAL_ELEMENT.equals(other)) {
            return this;
        } else if (other instanceof EllipticCurveActualElement) {
            EllipticCurveActualElement b = (EllipticCurveActualElement) other;
            if (a.equals(other)) {
                ZModZPStarElement m = mForSquaring(a);
                ZModZPStarElement m2 = m.multiply(m);
                ZModZPStarElement _2xa = group.getElement(2).multiply(a.x);
                xc = m2.subtract(_2xa);
                yc = calculateYc(a, xc, m);
                return new EllipticCurveActualElement(group, xc, yc);
            } else if (a.x().equals(b.x())) {
                return NEUTRAL_ELEMENT;
            } else {
                ZModZPStarElement m = m(a, b);
                xc = m.multiply(m).subtract(a.x()).subtract(b.x());
                yc = calculateYc(a, xc, m);
                return new EllipticCurveActualElement(group, xc, yc);
            }
        } else throw new UnsupportedOperationException("Caused by " + other);
    }

    private ZModZPStarElement calculateYc(EllipticCurveActualElement a, ZModZPStarElement xc, ZModZPStarElement m) {
        return a.y().negate().subtract(m.multiply(xc.subtract(a.x())));
    }

    private ZModZPStarElement mForSquaring(EllipticCurveActualElement a) {
        ZModZPStarElement _3xa2 = group.getElement(3).multiply(a.x.multiply(a.x));
        ZModZPStarElement _2ya = group.getElement(2).multiply(a.y);
        return _3xa2.add(group.getU()).divide(_2ya);
    }

    private ZModZPStarElement m(EllipticCurveActualElement a, EllipticCurveActualElement b) {
        ZModZPStarElement xa = a.x();
        ZModZPStarElement xb = b.x();
        ZModZPStarElement ya = a.y();
        ZModZPStarElement yb = b.y();

        ZModZPStarElement yd = yb.subtract(ya);
        ZModZPStarElement xd = xb.subtract(xa);
        ZModZPStarElement m = yd.divide(xd);

        return m;
    }

    @Override
    public String toString() {
        return String.format("P(%s, %s)", x, y);
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
}
