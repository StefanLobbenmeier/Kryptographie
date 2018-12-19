package com.fhswf.kryptographie;

import java.math.BigInteger;
import java.util.HashMap;

public class BabyStepGiantStep {
    static int babyStepGiantStep(DiffieHellmanGroup group, DiffieHellmanGroupElement bigA, DiffieHellmanGroupElement base) {
        int m = BigIntegerUtil.bigIntSqRootCeil(group.getModulus()).intValue();
        DiffieHellmanGroupElement baseToM = base.pow(group.getElement(BigInteger.valueOf(m)));

        //prepare table
        HashMap<DiffieHellmanGroupElement, Integer> preparedBaseToMToU = new HashMap<DiffieHellmanGroupElement, Integer>(m, 1);
        for (int u = 1; u <= m; u++) {
            DiffieHellmanGroupElement baseToMToU = baseToM.pow(u);
            preparedBaseToMToU.put(baseToMToU, u);
        }


        int foundA = -1;
        for (int v = 0; v <= m; v++) {
            DiffieHellmanGroupElement baseToV = base.pow(v);
            DiffieHellmanGroupElement aTimesBaseToV = baseToV.multiply(bigA);

            Integer foundU = preparedBaseToMToU.get(aTimesBaseToV);
            if (foundU != null) {
                foundA = foundU * m - v;
                break;
            }
        }
        return foundA;
    }
}