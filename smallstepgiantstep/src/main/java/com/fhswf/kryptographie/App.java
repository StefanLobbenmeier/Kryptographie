package com.fhswf.kryptographie;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Optional;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        task1BabystepGianststep();

        task2Ecc();
    }

    private static void task1BabystepGianststep() {
        System.out.println("Task 1b) Calculate a = log2(3) in (Z/2698727Z)*");
        ZModZPStarGroup group = new ZModZPStarGroup(BigInteger.valueOf(2698727L));
        ZModZPStarElement bigA = group.getElement(BigInteger.valueOf(3));
        ZModZPStarElement base = group.getElement(BigInteger.valueOf(2));
        int a = BabyStepGiantStep.babyStepGiantStep(group, bigA, base);

        System.out.printf("a = log2 %s = %s %n", bigA, a);

        ZModZPStarElement check = base.pow(a);
        System.out.println("check = " + check);
    }

    private static void task2Ecc() {
        System.out.println("Task 2) Elliptic Curves");
        // Information on the curve can be found here http://www.lmfdb.org/EllipticCurve/Q/496/a/1


        EllipticCurveGroup group = new EllipticCurveGroup(1, 1, 127);

        task2aEcc(group);
        task2bEcc(group);
        task2cEcc(group);
    }

    private static void task2aEcc(EllipticCurveGroup group) {
        System.out.println("Task 2a) Count Number of Elements");
        System.out.println("group.hasseInterval() = " + group.hasseInterval());
        HashSet<EllipticCurveGroupElement> elements = new HashSet<>();
        elements.add(EllipticCurveNeutralElement.getNeutralElement());
        int k = group.getK().intValue();
        for (int x = 0; x < k; x++) {
            Optional<EllipticCurveActualElement> element = group.getElement(x);
            if (element.isPresent()) {
                elements.add(element.get());
                elements.add(element.get().negate());
            }
        }
        System.out.println("elements.size() = " + elements.size());
        System.out.println("elements = " + elements);

        boolean resultIsInHasseInterval = group.hasseInterval().contains(BigInteger.valueOf(elements.size()));
        System.out.println("resultIsInHasseInterval = " + resultIsInHasseInterval);
    }

    private static void task2bEcc(EllipticCurveGroup group) {
        System.out.println("Task 2b) Calculate q = 111 * p");
        EllipticCurveActualElement p = group.getElement(11, 33);
        EllipticCurveGroupElement q = p.pow(111);

        System.out.println("q = " + q);
    }

    private static void task2cEcc(EllipticCurveGroup group) {
        System.out.println("Task 2c) Find Order and Cofactor of p");
        EllipticCurveActualElement p = group.getElement(11, 33);

        BigInteger orderOfP = p.getOrder();
        System.out.println("orderOfP = " + orderOfP);

        BigInteger cofactor = p.getCofactor();
        System.out.println("cofactor = " + cofactor);
    }
}
