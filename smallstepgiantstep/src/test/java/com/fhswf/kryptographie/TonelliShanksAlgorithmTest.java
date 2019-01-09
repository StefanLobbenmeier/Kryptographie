package com.fhswf.kryptographie;

import org.junit.Test;

import java.math.BigInteger;
import java.util.Optional;

import static com.fhswf.kryptographie.TonelliShanksAlgorithm.SQAURE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TonelliShanksAlgorithmTest {

    @Test
    public void root() {
        assertRoot(41, 5, 28);
        assertRoot(13, 10, 7);
        assertRoot(101, 56, 37);
        assertRoot(10009, 1030, 1632);
        assertRootDoesNotExist(10009, 1032);
    }

    private void assertRoot(int p, int n, int expected) {
        assertThat(TonelliShanksAlgorithm.root(BigInteger.valueOf(p), BigInteger.valueOf(n)).get(), is(BigInteger.valueOf(expected)));
    }

    private void assertRootDoesNotExist(int p, int n) {
        assertThat(TonelliShanksAlgorithm.root(BigInteger.valueOf(p), BigInteger.valueOf(n)), is(Optional.empty()));
    }

    @Test
    public void legendreSymbol() {
        assertLegendreSymbol(1, 97, SQAURE);
        assertLegendreSymbol(4, 97, SQAURE);
        assertLegendreSymbol(9, 97, SQAURE);
        assertLegendreSymbol(16, 97, SQAURE);

        assertLegendreSymbol(0, 97, 0);

        //non squares are hard to find for now
    }

    private void assertLegendreSymbol(int a, int p, int expected) {
        assertThat(TonelliShanksAlgorithm.legendreSymbol(BigInteger.valueOf(a)), is(expected));
    }

}