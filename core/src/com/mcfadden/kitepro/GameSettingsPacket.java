package com.mcfadden.kitepro;

/**
 * Created by samuelmcfadden on 6/23/17.
 * A packet of the data that holds all of the game settings that the user inputs from the StartingMenu.
 * The GameSettingsPacket also translates the user input numbers (stats similar to those in League)
 * to numbers the game can use internally. It does this by using my defined constants.
 */

public class GameSettingsPacket {

    float championSize;
    float enemyChampionSize;
    float movementSpeed;
    float millisPerAttack;
    float attackDamage;
    float attackRange;
    float enemyMovementSpeed;

    static final float ATTACK_RANGE_MULTIPLIER = 0.357f;
    static final float MOVESPEED_MULTIPLIER = 0.0057f;

    public GameSettingsPacket(float championSize, float enemyChampionSize, float attackRange, float attackDamage,
                              float attackSpeed, float movementSpeed, float enemyMovementSpeed) {
        this.championSize = championSize;
        this.enemyChampionSize = enemyChampionSize;
        this.attackRange = ATTACK_RANGE_MULTIPLIER * attackRange;
        this.attackDamage = attackDamage;
        this.millisPerAttack = 1000/attackSpeed;
        this.movementSpeed = MOVESPEED_MULTIPLIER * movementSpeed;
        this.enemyMovementSpeed = MOVESPEED_MULTIPLIER * enemyMovementSpeed;
    }
}
