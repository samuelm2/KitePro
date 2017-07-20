package com.mcfadden.kitepro;

/**
 * Created by samuelmcfadden on 6/20/17.
 * A class that holds the information of a click during the GameScreen
 */

public class GameClick {
    int screenX;
    int screenY;
    int button;
    BadGuy target;
    boolean flashPressed;

    public GameClick(int screenX, int screenY, int button, BadGuy potentialTarget, boolean flashPressed) {
        this.screenX = screenX;
        this.screenY = (int) KitePro.SCREEN_HEIGHT - screenY;
        this.button = button;
        this.flashPressed = flashPressed;

        if((this.screenX - potentialTarget.currentX) * (this.screenX - potentialTarget.currentX) +
                (this.screenY - potentialTarget.currentY) *  (this.screenY - potentialTarget.currentY) <=
                potentialTarget.radius * potentialTarget.radius)
        {
            target = potentialTarget;
        } else {
            target = null;
        }
    }

}
