package com.fhswf.kryptographie;

import java.math.BigInteger;

public class EllipticCurveNeutralElement implements EllipticCurveGroupElement {

    @Override
    public EllipticCurveGroupElement pow(BigInteger e) {
        return this;
    }

    @Override
    public EllipticCurveGroupElement multiply(EllipticCurveGroupElement factorB) {
        return factorB;
    }
}
