package com.mcfadden.kitepro;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by samuelmcfadden on 6/23/17.
 *
 * The screen where the user can input the settings for the game start
 */

public class StartingMenu implements Screen{

    private static final float TABLE_PADDING = 10;

    private Stage stage;
    private Table table;
    private Table buttonTable;

    private Slider sizeSlider;
    private Slider attackRangeSlider;
    private Slider attackDamageSlider;
    private Slider attackSpeedSlider;
    private Slider moveSpeedSlider;
    private Slider enemyMoveSpeedSlider;
    private Slider enemySizeSlider;

    private Label sizeNumberLabel;
    private Label attackRangeNumberLabel;
    private Label attackDamageNumberLabel;
    private Label attackSpeedNumberLabel;
    private Label moveSpeedNumberLabel;
    private Label enemyMoveSpeedNumberLabel;
    private Label enemySizeNumberLabel;

    private TextButton caitlynPresetButton;
    private TextButton lucianPresetButton;
    private TextButton kogMawPresetButton;
    private TextButton hardAFPresetButton;

    private TextButton startGameTextbutton;
    private TextButton quitAppTextButton;

    private static final float MAX_CHAMPION_SIZE = 64;
    private static final float MAX_ATTACK_RANGE = 710;
    private static final float MAX_ATTACK_DAMAGE = 200;
    private static final float MAX_ATTACK_SPEED = 2.5f;
    private static final float MAX_MOVESPEED = 600;

    private float championSize = 16;
    private float attackRange = 100;
    private float attackDamage = 0;
    private float attackSpeed = .5f;
    private float movementSpeed = 0;
    private float enemyMovementSpeed = 0;
    private float enemyChampionSize = 16;

    private BlueMan exampleBlueMan;
    private BadGuy exampleBadGuy;


    public StartingMenu(final Game game) {

        stage = new Stage(new ScreenViewport(), KitePro.batch);
        Gdx.input.setInputProcessor(stage);

        table = new Table(KitePro.cloudFormUI);

        table.align(Align.left);
        table.setPosition(KitePro.UNIT , KitePro.SCREEN_HEIGHT/2);

        caitlynPresetButton = new TextButton("Preset: Caitlyn", KitePro.cloudFormUI);
        caitlynPresetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setSlidersToPreset(PresetCharacters.caitlyn);
            }
        });

        lucianPresetButton = new TextButton("Preset: Lucian", KitePro.cloudFormUI);
        lucianPresetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setSlidersToPreset(PresetCharacters.lucian);
            }
        });

        kogMawPresetButton = new TextButton("Preset: Kog'Maw (with lvl 5 w)", KitePro.cloudFormUI);
        kogMawPresetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setSlidersToPreset(PresetCharacters.kogmaw);
            }
        });

        hardAFPresetButton = new TextButton("Preset: Hard AF", KitePro.cloudFormUI);
        hardAFPresetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setSlidersToPreset(PresetCharacters.hardAF);
            }
        });

        sizeNumberLabel = new Label(String.valueOf((int)championSize), KitePro.cloudFormUI);
        sizeSlider = new Slider(championSize, MAX_CHAMPION_SIZE, 1, false, KitePro.cloudFormUI);
        sizeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                championSize = sizeSlider.getValue();
                sizeNumberLabel.setText(String.valueOf((int) championSize));
            }
        });

        attackRangeNumberLabel = new Label(String.valueOf((int) attackRange), KitePro.cloudFormUI);
        attackRangeSlider = new Slider(attackRange, MAX_ATTACK_RANGE, 5, false, KitePro.cloudFormUI);
        attackRangeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                attackRange = attackRangeSlider.getValue();
                attackRangeNumberLabel.setText(String.valueOf((int) attackRange));
            }
        });

        attackDamageNumberLabel = new Label(String.valueOf((int) attackDamage), KitePro.cloudFormUI);
        attackDamageSlider = new Slider(attackDamage, MAX_ATTACK_DAMAGE, 5, false, KitePro.cloudFormUI);
        attackDamageSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                attackDamage = attackDamageSlider.getValue();
                attackDamageNumberLabel.setText(String.valueOf((int) attackDamage));
            }
        });

        attackSpeedNumberLabel = new Label(String.valueOf(attackSpeed), KitePro.cloudFormUI);
        attackSpeedSlider = new Slider(attackSpeed, MAX_ATTACK_SPEED, .1f, false, KitePro.cloudFormUI);
        attackSpeedSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                attackSpeed = attackSpeedSlider.getValue();
                attackSpeedNumberLabel.setText(String.format("%.1f", attackSpeed));
            }
        });

        moveSpeedNumberLabel = new Label(String.valueOf((int)movementSpeed), KitePro.cloudFormUI);
        moveSpeedSlider = new Slider(movementSpeed, MAX_MOVESPEED, 5, false, KitePro.cloudFormUI);
        moveSpeedSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                movementSpeed = moveSpeedSlider.getValue();
                moveSpeedNumberLabel.setText(String.valueOf((int)movementSpeed));
            }
        });

        enemyMoveSpeedNumberLabel = new Label(String.valueOf((int)enemyMovementSpeed), KitePro.cloudFormUI);
        enemyMoveSpeedSlider = new Slider(movementSpeed, MAX_MOVESPEED, 5, false, KitePro.cloudFormUI);
        enemyMoveSpeedSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                enemyMovementSpeed = enemyMoveSpeedSlider.getValue();
                enemyMoveSpeedNumberLabel.setText(String.valueOf((int)enemyMovementSpeed));
            }
        });

        enemySizeNumberLabel = new Label(String.valueOf((int)enemyChampionSize), KitePro.cloudFormUI);
        enemySizeSlider = new Slider(enemyChampionSize, MAX_CHAMPION_SIZE, 1, false, KitePro.cloudFormUI);
        enemySizeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                enemyChampionSize = enemySizeSlider.getValue();
                enemySizeNumberLabel.setText(String.valueOf((int) enemyChampionSize));
            }
        });

        buildTable();


        exampleBlueMan = new BlueMan(KitePro.UNIT * 8, KitePro.SCREEN_HEIGHT/2, championSize, 0);
        exampleBlueMan.forceAttackRight();

        exampleBadGuy = new BadGuy(KitePro.SCREEN_WIDTH - KitePro.UNIT/2, exampleBlueMan.currentY, enemyChampionSize, 0);

        stage.addActor(table);

        buttonTable = new Table(KitePro.cloudFormUI);
        buttonTable.align(Align.center);
        buttonTable.setPosition(KitePro.SCREEN_WIDTH/2, KitePro.UNIT);

        startGameTextbutton = new TextButton("Start Game", KitePro.cloudFormUI);
        startGameTextbutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameSettingsPacket gameSettingsPacket = new GameSettingsPacket(championSize, enemyChampionSize, attackRange, attackDamage,
                        attackSpeed, movementSpeed, enemyMovementSpeed);

                game.setScreen(new GameScreen(game, gameSettingsPacket));
            }
        });
        startGameTextbutton.align(Align.center);
        stage.addActor(startGameTextbutton);

        quitAppTextButton = new TextButton("Quit Game", KitePro.cloudFormUI);
        quitAppTextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        buttonTable.add(quitAppTextButton).padRight(TABLE_PADDING);
        buttonTable.add(startGameTextbutton);

        stage.addActor(buttonTable);
    }

    private void buildTable() {
        table.clearChildren();

        table.add("Size: ").padBottom(TABLE_PADDING).align(Align.left);
        table.add(sizeSlider).padBottom(TABLE_PADDING);
        table.add(sizeNumberLabel).padBottom(TABLE_PADDING).align(Align.left);
        table.row();

        table.add("Attack range: ").padBottom(TABLE_PADDING).align(Align.left);
        table.add(attackRangeSlider).padBottom(TABLE_PADDING);
        table.add(attackRangeNumberLabel).padBottom(TABLE_PADDING).align(Align.left);;
        table.row();

        table.add("Attack damage: ").padBottom(TABLE_PADDING).align(Align.left);
        table.add(attackDamageSlider).padBottom(TABLE_PADDING);
        table.add(attackDamageNumberLabel).padBottom(TABLE_PADDING).align(Align.left);;
        table.row();

        table.add("Attack speed: ").padBottom(TABLE_PADDING).align(Align.left);
        table.add(attackSpeedSlider).padBottom(TABLE_PADDING);
        table.add(attackSpeedNumberLabel).padBottom(TABLE_PADDING).align(Align.left);;
        table.row();

        table.add("Move speed: ").padBottom(TABLE_PADDING).align(Align.left);
        table.add(moveSpeedSlider).padBottom(TABLE_PADDING);
        table.add(moveSpeedNumberLabel).padBottom(TABLE_PADDING).align(Align.left);;
        table.row();

        table.add("Enemy move speed: ").padBottom(TABLE_PADDING).align(Align.left);
        table.add(enemyMoveSpeedSlider).padBottom(TABLE_PADDING);
        table.add(enemyMoveSpeedNumberLabel).padBottom(TABLE_PADDING).align(Align.left);
        table.row();

        table.add("Enemy Size: ").padBottom(TABLE_PADDING).align(Align.left);
        table.add(enemySizeSlider).padBottom(TABLE_PADDING);
        table.add(enemySizeNumberLabel).padBottom(TABLE_PADDING).align(Align.left);
        table.row();

        table.add(caitlynPresetButton).align(Align.left);
        table.add(lucianPresetButton).align(Align.left);

        table.row();
        table.add(kogMawPresetButton).align(Align.left);
        table.add(hardAFPresetButton).align(Align.left);
    }

    private void setSlidersToPreset(GameSettingsPacket g) {
        sizeSlider.setValue(g.championSize);
        attackRangeSlider.setValue((1/GameSettingsPacket.ATTACK_RANGE_MULTIPLIER) * g.attackRange);
        attackDamageSlider.setValue(g.attackDamage);
        attackSpeedSlider.setValue(1000/g.millisPerAttack);
        moveSpeedSlider.setValue(1/GameSettingsPacket.MOVESPEED_MULTIPLIER * g.movementSpeed);
        enemyMoveSpeedSlider.setValue(1/GameSettingsPacket.MOVESPEED_MULTIPLIER * g.enemyMovementSpeed);
        enemySizeSlider.setValue(g.enemyChampionSize);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        GameSettingsPacket gameSettingsPacket = new GameSettingsPacket(championSize, enemyChampionSize, attackRange,
                attackDamage, attackSpeed, 0, 0);
        exampleBadGuy.setRadius(enemyChampionSize);
        exampleBlueMan.updateStats(gameSettingsPacket);
        exampleBlueMan.update(delta);
        KitePro.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        exampleBlueMan.draw(KitePro.shapeRenderer);
        exampleBadGuy.draw(KitePro.shapeRenderer);
        KitePro.shapeRenderer.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
}
