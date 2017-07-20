package com.mcfadden.kitepro;

/**
 * Created by samuelmcfadden on 6/20/17.
 * projectiles shot by the BlueMan
 */

public class Projectile extends MoveableObject {
    private static final float PROJECTILE_SPEED = 10;
    private static final float PROJECTILE_RADIUS = 5;


    public Projectile(float currentX, float currentY, MoveableObject target) {
        super(currentX, currentY, PROJECTILE_RADIUS, PROJECTILE_SPEED);
        targetX = target.currentX;
        targetY = target.currentY;
    }

    @Override
    public boolean hasReachedTarget() {
        return (targetX - currentX) * (targetX - currentX) +
                (targetY - currentY) * (targetY - currentY) <= 64;
    }

}
