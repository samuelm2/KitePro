package com.mcfadden.kitepro;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * The main game screen
 * Created by samuelmcfadden on 6/19/17.
 */

public class GameScreen implements Screen, InputProcessor {

    private Game game;

    private BlueMan mainCharacter;
    private BadGuy chaser;
    private ShapeRenderer shapeRenderer;

    private Table damageInfoTable;

    private Label damageDealtLabel;
    private Label DPSLabel;
    private Label OptimalDPSLabel;

    public GameScreen(Game game, GameSettingsPacket gameSettingsPacket) {
        this.game = game;

        Gdx.input.setInputProcessor(this);
        mainCharacter = new BlueMan(KitePro.SCREEN_WIDTH * 3/4, KitePro.SCREEN_HEIGHT/2,
                gameSettingsPacket.championSize, gameSettingsPacket.movementSpeed);
        mainCharacter.updateStats(gameSettingsPacket);

        DamageCounter.setAutoDamage((int)gameSettingsPacket.attackDamage);
        DamageCounter.resetDamageDealt();


        chaser = new BadGuy(KitePro.SCREEN_WIDTH * 1/4, KitePro.SCREEN_HEIGHT/2,
                gameSettingsPacket.enemyChampionSize, gameSettingsPacket.enemyMovementSpeed);
        shapeRenderer = new ShapeRenderer();

        damageInfoTable = new Table(KitePro.cloudFormUI);
        damageInfoTable.setPosition(5 * KitePro.UNIT, 1 * KitePro.UNIT);

        damageDealtLabel = new Label("Total damage dealt: " + String.valueOf(DamageCounter.getTotalDamageDealt()),
                KitePro.cloudFormUI);

        damageInfoTable.add(damageDealtLabel);
        damageInfoTable.row();

        DPSLabel = new Label("DPS: " + String.valueOf(DamageCounter.getDPS()), KitePro.cloudFormUI);
        damageInfoTable.add(DPSLabel);
        damageInfoTable.row();

        double optimalDPS = DamageCounter.calculateOptimalDPS((long) gameSettingsPacket.millisPerAttack,
                (long) gameSettingsPacket.attackDamage);
        OptimalDPSLabel = new Label("Optimal DPS: " +  String.format("%.2f", optimalDPS), KitePro.cloudFormUI);
        damageInfoTable.add(OptimalDPSLabel);
        DamageCounter.startDPSCounter();

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        int mouseX = Gdx.input.getX();
        if(mouseX < 10) {
            mainCharacter.shiftLeft(delta);
            chaser.shiftLeft(delta);
        } else if(mouseX > KitePro.SCREEN_WIDTH - 10) {
            mainCharacter.shiftRight(delta);
            chaser.shiftRight(delta);
        }

        int mouseY = Gdx.input.getY();
        if(mouseY < 10) {
            mainCharacter.shiftUp(delta);
            chaser.shiftUp(delta);
        } else if(mouseY > KitePro.SCREEN_HEIGHT - 10) {
            mainCharacter.shiftDown(delta);
            chaser.shiftDown(delta);
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        mainCharacter.draw(shapeRenderer);
        chaser.draw(shapeRenderer);
        shapeRenderer.end();

        damageDealtLabel.setText("Total damage dealt: " + String.valueOf(DamageCounter.getTotalDamageDealt()));
        DPSLabel.setText("DPS: " + String.format("%.2f", DamageCounter.tryToGetDPS()));
        KitePro.batch.begin();
        damageInfoTable.draw(KitePro.batch, 1);
        KitePro.batch.end();

        BadGuy.setTargetLocation(mainCharacter.currentX, mainCharacter.currentY);

        chaser.recalculatePath();
        mainCharacter.update(delta);
        chaser.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        KitePro.SCREEN_WIDTH = Gdx.graphics.getWidth();
        KitePro.SCREEN_HEIGHT = Gdx.graphics.getHeight();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            game.setScreen(new StartingMenu(game));
        } else if (keycode == Input.Keys.F || keycode == Input.Keys.D) {
            mainCharacter.handleClick(new GameClick(Gdx.input.getX(), Gdx.input.getY(),
                    Input.Buttons.MIDDLE, chaser, true));
        } else if (keycode == Input.Keys.S) {
            mainCharacter.stop();
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        mainCharacter.handleClick(new GameClick(screenX, screenY, button, chaser, false));
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            mainCharacter.targetX = screenX;
            mainCharacter.targetY = KitePro.SCREEN_HEIGHT - screenY;
            mainCharacter.recalculatePath();
        }
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
