package com.fhswf.kryptographie;

import java.math.BigInteger;

public interface Group<D extends GroupElement<D>> {
    D getElement(BigInteger value);

    DiffieHellmanGroupElement pow(D base, int e);

    D multiply(D factorA, D factorB);
}
