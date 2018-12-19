package com.fhswf.kryptographie;

public interface GroupElement<D extends GroupElement<D>> {
    D pow(D e);
}
