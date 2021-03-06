package com.mcfadden.kitepro;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by samuelmcfadden on 6/20/17.
 * The character that is controlled by the player
 */

public class BlueMan extends MoveableObject {

    private enum ActionState {
        SEEKING, STUNNED, MOVING, ATTACKING, IDLE
    }

    private static final float FLASH_DISTANCE = 150;

    private ActionState currentState;
    private ActionState previousState;

    private Projectile projectile;
    private boolean isRenderingProjectile;

    private Projectile projectile2;
    private boolean isRenderingProjectile2;

    private long stunnedTime;
    private long currentStunLength;

    private long lastAttackTime;

    private BadGuy target;

    private static float ATTACK_RANGE = 150;
    private static float ATTACK_RANGE2 = ATTACK_RANGE * ATTACK_RANGE;

    private static long ATTACK_STUN_TIME = 100;
    private static long MILLISECONDS_PER_ATTACK = 666;

    public BlueMan(float currentX, float currentY, float radius, float speed) {
        super(currentX, currentY, radius, speed);
        projectile = new Projectile(0, 0, this);
        projectile2 = new Projectile(0,0,this);
        isRenderingProjectile = false;
        isRenderingProjectile2 = false;
        currentState = ActionState.IDLE;
        previousState = ActionState.IDLE;
        lastAttackTime = TimeUtils.millis();
    }

    /**
     * Launches the projectile at the target location.
     * TODO: Switch over to a non-lazy implementation where the BlueMan has an array of projectiles to launch
     * @param target the target that the projectile is aiming for
     */
    public void launchProjectile(MoveableObject target) {
        if(!isRenderingProjectile) {
            projectile.currentX = this.currentX;
            projectile.currentY = this.currentY;
            projectile.targetX = target.currentX;
            projectile.targetY = target.currentY;
            isRenderingProjectile = true;
            projectile.recalculatePath();
        } else if(!isRenderingProjectile2) {
            projectile2.currentX = this.currentX;
            projectile2.currentY = this.currentY;
            projectile2.targetX = target.currentX;
            projectile2.targetY = target.currentY;
            isRenderingProjectile2 = true;
            projectile2.recalculatePath();
        }
    }

    /**
     * Gets the information from the passed in GameClick class and extracts the necessary data to
     * decide what BlueMan should do next
     * @param gameClick
     */
    public void handleClick(GameClick gameClick) {
        if(gameClick.flashPressed) {
            if(currentState != ActionState.STUNNED) {
                flash(gameClick.screenX, gameClick.screenY);
            }
        }
        else if(gameClick.button == Input.Buttons.RIGHT) {
            if(gameClick.target != null) {
                this.target = gameClick.target;
                if(Utils.dist2(this.currentX, this.currentY,
                        gameClick.target.currentX, gameClick.target.currentY) <= ATTACK_RANGE2)
                {
                    setState(ActionState.ATTACKING);
                } else{
                    setState(ActionState.SEEKING);
                }
            } else {
                this.targetX = gameClick.screenX;
                this.targetY = gameClick.screenY;
                setState(ActionState.MOVING);
                recalculatePath();
            }
        }
    }

    /**
     * Generic update() method that calls the other update methods based on the BlueMan's current
     * state
     * @param delta
     */
    public void update(float delta) {
        updateProjectile(delta);
        switch (currentState) {
            case IDLE:
                break;
            case SEEKING:
                seekingUpdate(delta);
                break;
            case STUNNED:
                stunnedUpdate(delta);
                break;
            case MOVING:
                movingUpdate(delta);
                break;
            case ATTACKING:
                attackingUpdate(delta);
                break;
            default:
                throw new RuntimeException("Main Character is in an invalid action state");
        }
    }

    /**
     * Chases down the target. If the target becomes inside the attack range of BlueMan, he will
     * switch to Attacking state
     * @param delta
     */
    private void seekingUpdate(float delta) {
        targetX = target.currentX;
        targetY = target.currentY;
        if(Utils.dist2(currentX, currentY, targetX, targetY) <
                (ATTACK_RANGE + target.radius) * (ATTACK_RANGE + target.radius)) {
            setState(ActionState.ATTACKING);
        } else {
            recalculatePath();
            updatePosition(delta);
        }
    }

    /**
     * Does nothing until BlueMan is unstunned, then reverts BlueMan's state back to previousState
     * @param delta
     */
    private void stunnedUpdate(float delta) {
        if(TimeUtils.timeSinceMillis(stunnedTime) > currentStunLength) {
            currentState = previousState;
        }
    }

    /**
     * The update method that is called when the BlueMan's currentState is Moving
     * @param delta
     */
    private void movingUpdate(float delta) {
        updatePosition(delta);
    }

    /**
     * The update method that is called when the BlueMan's currentState is Attacking.
     * If the target moves out of range, the BlueMan will go back into its seeking state
     * @param delta
     */
    private void attackingUpdate(float delta) {
        if(Utils.dist2(currentX, currentY, targetX, targetY) < (ATTACK_RANGE + target.radius) * (ATTACK_RANGE + target.radius)) {
            if(TimeUtils.timeSinceMillis(lastAttackTime) > MILLISECONDS_PER_ATTACK) {
                launchProjectile(target);
                stun(ATTACK_STUN_TIME);
                lastAttackTime = TimeUtils.millis();
            }
        } else {
            setState(ActionState.SEEKING);
        }
    }

    /**
     * In the superclass, the update() only deals with moving the MoveableObject. However, with
     * the BlueMan, update() must do a lot more, so the super.update() is moved into its own method
     * @param delta
     */
    private void updatePosition(float delta) {
        super.update(delta);
    }

    /**
     * Sets the state of the BlueMan to the given ActionState. If the BlueMan is stunned, it will
     * set the previousState instead
     *
     * @param newState
     */
    private void setState(ActionState newState) {
        if(currentState != ActionState.STUNNED) {
            if(currentState != newState) {
                Gdx.app.log("BlueMan", "Changing state to: " + newState.toString());
                currentState = newState;
            }
        } else {
            if(previousState != newState) {
                Gdx.app.log("Changing state after stun to", newState.toString());
                previousState = newState;
            }
        }
    }

    /**
     * Makes the BlueMan unable to do anything for a given stunLength.
     *
     * @param stunLength
     */
    public void stun(long stunLength) {
        currentStunLength = stunLength;
        stunnedTime = TimeUtils.millis();
        previousState = currentState;
        currentState = ActionState.STUNNED;

    }

    /**
     * Attempts to flash to the mouse location. If the mouse location is too far from the BlueMan,
     * the BlueMan will flash FLASH_DISTANCE units in the direction of the mouse location
     *
     * @param mousePositionX
     * @param mousePositionY
     */
    private void flash(float mousePositionX, float mousePositionY) {
        float dx = mousePositionX - currentX;
        float dy = mousePositionY - currentY;
        Vector2 tempDiff = new Vector2(Vector2.Zero);
        tempDiff.x = dx;
        tempDiff.y = dy;

        if(tempDiff.len() > FLASH_DISTANCE) {
            tempDiff.nor();
            tempDiff.scl(FLASH_DISTANCE);
        }
        currentX += tempDiff.x;
        currentY += tempDiff.y;
        recalculatePath();
    }

    /**
     * Updates Blueman's projectiles
     * @param delta
     */
    private void updateProjectile(float delta) {
        if(isRenderingProjectile) {
            projectile.update(delta);
            if(projectile.hasReachedTarget()) {
                isRenderingProjectile = false;
                DamageCounter.autoAttackHit();
            }
        }

        if (isRenderingProjectile2) {
            projectile2.update(delta);
            if(projectile2.hasReachedTarget()) {
                isRenderingProjectile2 = false;
                DamageCounter.autoAttackHit();
            }
        }

    }

    @Override
    public void shiftUp(float delta) {
        super.shiftUp(delta);
        projectile.shiftUp(delta);
        projectile2.shiftUp(delta);
    }

    @Override
    public void shiftDown(float delta) {
        super.shiftDown(delta);
        projectile.shiftDown(delta);
        projectile2.shiftDown(delta);
    }

    @Override
    public void shiftLeft(float delta) {
        super.shiftLeft(delta);
        projectile.shiftLeft(delta);
        projectile2.shiftLeft(delta);
    }

    @Override
    public void shiftRight(float delta) {
        super.shiftRight(delta);
        projectile.shiftRight(delta);
        projectile2.shiftRight(delta);
    }


    /**
     *  Draws the Blueman and any projectiles that he may be launching
     * @param shapeRenderer
     */
    @Override
    public void draw(ShapeRenderer shapeRenderer) {
        if(isRenderingProjectile) {
            shapeRenderer.setColor(Color.BLACK);
            projectile.draw(shapeRenderer);
        }
        if(isRenderingProjectile2) {
            shapeRenderer.setColor(Color.BLACK);
            projectile2.draw(shapeRenderer);
        }
        shapeRenderer.end();
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.circle(currentX, currentY, ATTACK_RANGE);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        super.draw(shapeRenderer);
    }

    public void stop() {
        setState(ActionState.IDLE);
    }

    public static long getMillisecondsPerAttack() {
        return MILLISECONDS_PER_ATTACK;
    }

    //the following methods are used ONLY by starting menu
    public void updateStats(GameSettingsPacket g) {
        ATTACK_RANGE = g.attackRange;
        ATTACK_RANGE2 = g.attackRange * g.attackRange;
        MILLISECONDS_PER_ATTACK = (long) g.millisPerAttack;
        setSpeed(g.movementSpeed);
        setRadius(g.championSize);
    }

    public void forceAttackRight() {
        ATTACK_RANGE2 = 1000000;
        currentState = ActionState.ATTACKING;
        target = new BadGuy(KitePro.SCREEN_WIDTH - KitePro.UNIT/2, currentY, 10, 0);
    }
}
