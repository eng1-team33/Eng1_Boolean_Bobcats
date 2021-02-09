package com.team5.game.tools;

import com.badlogic.gdx.utils.Array;
import com.team5.game.sprites.Player;
import com.team5.game.sprites.pathfinding.System;

import java.util.List;

public class GameStateUtils {

    public static void createGameState(Player player, Array<System> systems) {
        GameState gameState = GameState.getInstance();
        gameState.setPlayerX(player.getX());
        gameState.setPlayerY(player.getY());
        gameState.setCurrentHealth(player.getHealth());
        gameState.setSystemsBroken(getSystemsBrokenArray(systems));
        gameState.setSystemsBrokenNumber(getSystemsBrokenNumber(gameState.getSystemsBroken()));
    }

    public static boolean[] getSystemsBrokenArray(Array<System> systems) {
        boolean[] systemsBroken = new boolean[19];
        for(int i = 0; i < 19; i++) {
            systemsBroken[i] = systems.get(i).getBroken();
        }
        return systemsBroken;
    }

    public static int getSystemsBrokenNumber(boolean[] array) {
        int systemsBrokenNumber = 0;
        for (boolean bool: array) {
            if(bool) {
                systemsBrokenNumber++;
            }
        }
        return systemsBrokenNumber;
    }

}
