package com.mcfadden.kitepro;


import com.badlogic.gdx.utils.Array;

/**
 * Created by samuelmcfadden on 7/6/17.
 *
 */

public class BadGuyManager {
    Array<BadGuy> badguys;
    boolean[] isBeingRenderedOnScreen;

    public BadGuyManager(float radius, float speed) {
        BadGuy[] badGuyArray = new BadGuy[5];
        badguys = new Array<BadGuy>(badGuyArray);

        isBeingRenderedOnScreen = new boolean[5];
        for(int i = 0; i < isBeingRenderedOnScreen.length; i++) {
            isBeingRenderedOnScreen[i] = false;
            badguys.items[i] = new BadGuy(0, 0, radius, speed);
            badguys.items[i].respawn();
        }
    }

    public void updateAllBadGuys(float delta) {
        for(int i = 0; i < badguys.size; i++) {
            if(isBeingRenderedOnScreen[i]) {
                badguys.items[i].update(delta);
                badguys.items[i].recalculatePath();
            }
        }
    }

    public void spawnNewBadGuy() {
        for(int i = 0; i < badguys.size; i++) {
            if(!isBeingRenderedOnScreen[i]) {
                isBeingRenderedOnScreen[i] = true;
                badguys.items[i].respawn();
                break;
            }
        }
    }

    /**
     *
     * @param x the x position of the search location
     * @param y the y position of the search location
     * @return the index in the badguy array of the clicked badguy, or -1 if there is no badguy at
     * that location
     */
    public int getBadGuyAtLocation(float x, float y) {
        float badGuyRadius2 = (badguys.get(0).radius) * (badguys.get(0).radius);
        for(int i = 0; i < 5; i++) {
            BadGuy b = badguys.items[i];
            if(Utils.dist2(x, y, b.currentX, b.currentY) < badGuyRadius2 && isBeingRenderedOnScreen[i]) {
                return i;
            }
        }
        return -1;
    }
}
