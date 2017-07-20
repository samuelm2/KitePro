package com.mcfadden.kitepro;

import com.badlogic.gdx.utils.TimeUtils;


/**
 * Created by samuelmcfadden on 6/22/17.
 */

public class DamageCounter {

    private static int damageDealt = 0;

    private static int autoDamage = 100;

    private static long startingMillis;

    private static int framesPassedSinceLastDPSUpdate = 9;

    private static double previousDPS = 0;

    public static void startDPSCounter() {
        startingMillis = TimeUtils.millis();
    }

    public static void autoAttackHit() {
        damageDealt += autoDamage;
    }

    public static int getTotalDamageDealt() {
        return damageDealt;
    }

    public static double tryToGetDPS() {
        framesPassedSinceLastDPSUpdate++;
        if(framesPassedSinceLastDPSUpdate % 10 == 0) {
            return (previousDPS = getDPS());
        } else {

            return previousDPS;
        }
    }

    public static double getDPS() {
        return damageDealt/(TimeUtils.timeSinceMillis(startingMillis) * .001);
    }


    public static double calculateOptimalDPS(long millisPerAttack, long autoDamage) {
        return (1000d / (double)millisPerAttack) * autoDamage;
    }

    public static void setAutoDamage(int autoDamage) {
        DamageCounter.autoDamage = autoDamage;
    }

    public static void resetDamageDealt() {
        damageDealt = 0;
    }

}
