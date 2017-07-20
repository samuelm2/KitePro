package com.mcfadden.kitepro;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by samuelmcfadden on 6/19/17.
 *  A MoveableObject that gets its target based on a static Vector2 targetLocation
 */

public class BadGuy extends MoveableObject{

    static Vector2 targetLocation;

    public BadGuy(float currentX, float currentY, float radius, float speed) {
        super(currentX, currentY, radius, speed);
        if(targetLocation == null) {
            targetLocation = new Vector2(Vector2.Zero);
        }
    }

    public static void setTargetLocation(float x, float y) {
        targetLocation.x = x;
        targetLocation.y = y;
    }

    public void respawn() {
        this.currentX = KitePro.SCREEN_WIDTH * 1/4;
        this.currentY = KitePro.SCREEN_HEIGHT/2;
    }

    @Override
    public void recalculatePath() {
        this.targetX = targetLocation.x;
        this.targetY = targetLocation.y;
        super.recalculatePath();
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.RED);
        super.draw(shapeRenderer);
    }
}
