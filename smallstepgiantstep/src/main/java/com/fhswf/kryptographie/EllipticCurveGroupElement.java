package com.fhswf.kryptographie;

import java.math.BigInteger;

public interface EllipticCurveGroupElement extends GroupElement<EllipticCurveGroupElement> {
    EllipticCurveGroupElement pow(BigInteger e);
    EllipticCurveGroupElement multiply(EllipticCurveGroupElement factorB);
}
