package com.fhswf.kryptographie;

import java.util.HashMap;

public class BabyStepGiantStep {
    static int babyStepGiantStep(ZModZPStarGroup group, ZModZPStarElement bigA, ZModZPStarElement base) {
        int m = BigIntegerUtil.bigIntSqRootCeil(group.getModulus()).intValue();
        ZModZPStarElement baseToM = base.pow(m);

        //prepare table
        HashMap<ZModZPStarElement, Integer> preparedBaseToMToU = new HashMap<>(m, 1);
        for (int u = 1; u <= m; u++) {
            ZModZPStarElement baseToMToU = baseToM.pow(u);
            preparedBaseToMToU.put(baseToMToU, u);
        }


        int foundA = -1;
        for (int v = 0; v <= m; v++) {
            ZModZPStarElement baseToV = base.pow(v);
            ZModZPStarElement aTimesBaseToV = baseToV.multiply(bigA);

            Integer foundU = preparedBaseToMToU.get(aTimesBaseToV);
            if (foundU != null) {
                foundA = foundU * m - v;
                break;
            }
        }
        return foundA;
    }
}