package com.fhswf.kryptographie;

import java.math.BigInteger;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ZModZPStarGroup group = new ZModZPStarGroup(BigInteger.valueOf(2698727L));
        ZModZPStarElement bigA = group.getElement(BigInteger.valueOf(3));
        ZModZPStarElement base = group.getElement(BigInteger.valueOf(2));
        int a = BabyStepGiantStep.babyStepGiantStep(group, bigA, base);

        System.out.println("a = " + a);

        ZModZPStarElement check = base.pow(a);
        System.out.println("check = " + check);
    }
}
