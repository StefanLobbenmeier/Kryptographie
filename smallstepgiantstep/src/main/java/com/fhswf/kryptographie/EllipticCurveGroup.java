package com.fhswf.kryptographie;

import java.math.BigInteger;

public class EllipticCurveGroup implements Group<EllipticCurveGroupElement> {
    static final EllipticCurveGroupElement NEUTRAL_ELEMENT = new EllipticCurveNeutralElement();
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
     * @throws ArithmeticException if 4*u^3 + 27*v^2 == 0
     */
    void checkUAndV(BigInteger u, BigInteger v) {
        BigInteger _4u3 = BigInteger.valueOf(4).multiply(u.pow(3));
        BigInteger _27v2 = BigInteger.valueOf(27).multiply(v.pow(2));

        if (_4u3.add(_27v2).equals(BigInteger.ZERO)) {
            throw new ArithmeticException("Criteria 4*u^3 + 27*v^2 == 0 is not fulfilled");
        }
    }

//    /**
//     * @return (x ^ 3 + u * x + v) mod modulus
//     */
//    BigInteger f(BigInteger x) {
//        System.out.println("x = [" + x + "]");
//        BigInteger y2 = x.pow(3)
//                .add(u.multiply(x))
//                .add(v);
//        System.out.println("y2 = " + y2);
//
//        BigInteger y = BigIntegerUtil.bigIntSqRootCeil(y2);
//        System.out.println("y = " + y);
//
//        return y.mod(modulus);
//
//
//    }

    private EllipticCurveActualElement getElement(BigInteger x, BigInteger y) {
        return new EllipticCurveActualElement(this, group.getElement(x), group.getElement(y));
    }

    public EllipticCurveActualElement getElement(int x, int y) {
        return getElement(BigInteger.valueOf(x), BigInteger.valueOf(y));
    }

    public ZModZPStarElement getU() {
        return u;
    }

    public BigInteger getModulus() {
        return group.getModulus();
    }

    public ZModZPStarElement getElement(int i) {
        return group.getElement(i);
    }
}
