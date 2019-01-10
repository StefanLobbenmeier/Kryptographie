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
        assertRoot(41, 5, 13);
        assertRoot(13, 10, 6);
        assertRoot(101, 56, 37);
        assertRoot(10009, 1030, 1632);
        assertRootDoesNotExist(10009, 1032);
    }

    private void assertRoot(int p, int n, int expected) {

        assertThat(root(BigInteger.valueOf(p), BigInteger.valueOf(n)).get(), is(BigInteger.valueOf(expected)));
    }

    private void assertRootDoesNotExist(int p, int n) {
        assertThat(root(BigInteger.valueOf(p), BigInteger.valueOf(n)), is(Optional.empty()));
    }

    public static Optional<BigInteger> root(BigInteger p, BigInteger n) {
        ZModZPStarGroup group = new ZModZPStarGroup(p);
        return group.getElement(n).root().map(ZModZPStarElement::getValue);
    }

    @Test
    public void legendreSymbol() {
        ZModZPStarGroup group = new ZModZPStarGroup(97);
        TonelliShanksAlgorithm tonelliShanksAlgorithm = new TonelliShanksAlgorithm(group);
        assertLegendreSymbol(group.getElement(1), tonelliShanksAlgorithm, SQAURE);
        assertLegendreSymbol(group.getElement(4), tonelliShanksAlgorithm, SQAURE);
        assertLegendreSymbol(group.getElement(9), tonelliShanksAlgorithm, SQAURE);
        assertLegendreSymbol(group.getElement(16), tonelliShanksAlgorithm, SQAURE);

        assertLegendreSymbol(group.getElement(0), tonelliShanksAlgorithm, 0);

        //non squares are hard to find for now
    }

    private void assertLegendreSymbol(ZModZPStarElement a, TonelliShanksAlgorithm tonelliShanksAlgorithm, int expected) {
        assertThat(tonelliShanksAlgorithm.legendreSymbol(a), is(expected));
    }

}