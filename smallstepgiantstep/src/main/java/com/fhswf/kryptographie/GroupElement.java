package com.fhswf.kryptographie;

import java.math.BigInteger;

public interface GroupElement<D extends GroupElement<D>> {
    D pow(BigInteger e);
    default D pow(int e) {
        return pow(BigInteger.valueOf(e));
    }
}
