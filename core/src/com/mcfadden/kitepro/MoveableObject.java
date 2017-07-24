package com.mcfadden.kitepro;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;


/**
 * Created by samuelmcfadden on 6/19/17.
 * An object that can be moved around on the screen that has a target.
 */

public class MoveableObject {

    public static final int SCREEN_SCROLL_DISTANCE = 15;
    private final float DELTA_MULTIPLIER = 60;

    private float speed;

    float radius;

    float currentX;
    float currentY;

    float targetX;
    float targetY;

    private Vector2 diff;

    public MoveableObject(float currentX, float currentY, float radius, float speed) {
        if(currentX > KitePro.SCREEN_WIDTH || currentX < 0) {
            throw new RuntimeException("X coordinate out of bounds of screen.");
        }
        if(currentY > KitePro.SCREEN_HEIGHT || currentY < 0) {
            throw new RuntimeException("X coordinate out of bounds of screen.");
        }

        this.currentX = currentX;
        this.currentY = currentY;
        this.radius = radius;
        this.speed = speed;

        targetX = currentX;
        targetY = currentY;

        diff = new Vector2(Vector2.Zero);
    }

    /**
     * Sets the diff vector2 to the right direction
     */
    public void recalculatePath() {
        float dx = targetX - currentX;
        float dy = targetY - currentY;
        diff.x = dx;
        diff.y = dy;
        diff.nor();
    }

    /**
     * Moves the MoveableObject closer to the target.
     * @param delta The time between calls. This is used so that the game will be exactly the same
     *              for computers with different framerates
     */
    public void update(float delta) {
        if(!hasReachedTarget()) {
            currentX += diff.x * speed * delta * DELTA_MULTIPLIER;
            currentY += diff.y * speed * delta * DELTA_MULTIPLIER;
        }
    }

    public void shiftUp(float delta) {
        currentY -= SCREEN_SCROLL_DISTANCE;
        targetY -= SCREEN_SCROLL_DISTANCE;
    }

    public void shiftDown(float delta) {
        currentY += SCREEN_SCROLL_DISTANCE;
        targetY += SCREEN_SCROLL_DISTANCE;
    }

    public void shiftLeft(float delta) {
        currentX += SCREEN_SCROLL_DISTANCE;
        targetX += SCREEN_SCROLL_DISTANCE;
    }

    public void shiftRight(float delta) {
        currentX -= SCREEN_SCROLL_DISTANCE;
        targetX -= SCREEN_SCROLL_DISTANCE;
    }

    /**
     * Draws a circle at the moveableObjects current location
     * @param shapeRenderer
     */
    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.circle(currentX, currentY, radius);
    }

    /**
     *
     * @return Returns whether or not the MoveableObject is within three units (a very small distance)
     * of the target location
     */
    public boolean hasReachedTarget() {
        return (targetX - currentX) * (targetX - currentX) +
                (targetY - currentY) * (targetY - currentY) <= 9;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
