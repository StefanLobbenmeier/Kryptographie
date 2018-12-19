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
        DiffieHellmanGroup group = new DiffieHellmanGroup(BigInteger.valueOf(2698727L));
        DiffieHellmanGroupElement bigA = group.getElement(BigInteger.valueOf(3));
        DiffieHellmanGroupElement base = group.getElement(BigInteger.valueOf(2));
        int a = BabyStepGiantStep.babyStepGiantStep(group, bigA, base);

        System.out.println("a = " + a);

        DiffieHellmanGroupElement check = base.pow(a);
        System.out.println("check = " + check);
    }
}
