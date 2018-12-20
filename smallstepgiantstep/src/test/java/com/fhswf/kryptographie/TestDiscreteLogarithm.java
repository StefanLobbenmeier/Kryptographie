package com.fhswf.kryptographie;

import org.junit.Test;

import java.math.BigInteger;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Unit test for simple App.
 */
public class TestDiscreteLogarithm
{
    @Test
    public void testDiscreteLogarithm() {
        ZModZPStarGroup group = new ZModZPStarGroup(BigInteger.valueOf(131));
        ZModZPStarElement bigA = group.getElement(BigInteger.valueOf(125));
        ZModZPStarElement base = group.getElement(BigInteger.valueOf(2));

        int foundA = BabyStepGiantStep.babyStepGiantStep(group, bigA, base);

        assertThat(foundA, is(8));

    }

}
