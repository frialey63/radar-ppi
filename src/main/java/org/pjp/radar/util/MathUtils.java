package org.pjp.radar.util;

public final class MathUtils {


    public static double mod(double y, double x) {
        return y - x * Math.floor(y / x);
    }

    private MathUtils() {
        // prevent instantiation
    }
}
