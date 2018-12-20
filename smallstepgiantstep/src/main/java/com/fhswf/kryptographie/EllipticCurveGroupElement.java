package com.fhswf.kryptographie;

import java.math.BigInteger;

public interface EllipticCurveGroupElement extends GroupElement<EllipticCurveGroupElement> {
    public EllipticCurveGroupElement pow(BigInteger e);
    public EllipticCurveGroupElement multiply(EllipticCurveGroupElement factorB);
}
