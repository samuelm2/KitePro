package com.mcfadden.kitepro;

/**
 * Created by samuelmcfadden on 6/20/17.
 * Class that has useful methods
 */

public class Utils {
    public static float dist2(float x1, float y1, float x2, float y2) {
        return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
    }
}
