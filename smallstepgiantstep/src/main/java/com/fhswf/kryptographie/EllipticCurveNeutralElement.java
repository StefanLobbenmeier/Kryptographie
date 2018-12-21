package com.fhswf.kryptographie;

import java.math.BigInteger;

public class EllipticCurveNeutralElement implements EllipticCurveGroupElement {

    private EllipticCurveNeutralElement(){}

    private static EllipticCurveNeutralElement singleton;
    public static EllipticCurveNeutralElement getNeutralElement() {
        if(singleton == null) {
            singleton = new EllipticCurveNeutralElement();
        } return singleton;
    }

    @Override
    public EllipticCurveGroupElement pow(BigInteger e) {
        return this;
    }

    @Override
    public EllipticCurveGroupElement multiply(EllipticCurveGroupElement factorB) {
        return factorB;
    }

    @Override
    public String toString() {
        return "ECC Neutral Element";
    }
}
