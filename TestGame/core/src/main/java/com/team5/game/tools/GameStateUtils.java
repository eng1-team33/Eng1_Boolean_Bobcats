package com.team5.game.tools;

import com.badlogic.gdx.utils.Array;
import com.team5.game.sprites.Infiltrator;
import com.team5.game.sprites.Player;
import com.team5.game.sprites.pathfinding.System;

public class GameStateUtils {

    public static void createGameState(GameController gameController) {
        Player player = gameController.getPlayer();
        Array<System> systems = gameController.getNodeGraph().getSystems();
        Array<Infiltrator> infiltrators = gameController.getInfiltrators();

        GameState gameState = GameState.getInstance();
        gameState.setDifficulty(Difficulty.difficulty);
        gameState.setPlayerX(player.getX());
        gameState.setPlayerY(player.getY());
        gameState.setCurrentHealth(player.getHealth());
        gameState.setSystemsBroken(calculateSystemsBrokenArray(systems));
        gameState.setSystemsBrokenNumber(calculateSystemsBrokenNumber(gameState.getSystemsBroken()));
        gameState.setInfiltratorNumber(calculateInfiltratorNumber(infiltrators));
    }

    private static boolean[] calculateSystemsBrokenArray(Array<System> systems) {
        boolean[] systemsBroken = new boolean[systems.size];
        for(int i = 0; i < systems.size; i++) {
            systemsBroken[i] = systems.get(i).getBroken();
        }
        return systemsBroken;
    }

    private static int calculateSystemsBrokenNumber(boolean[] array) {
        int systemsBrokenNumber = 0;
        for (boolean bool: array) {
            if(bool) {
                systemsBrokenNumber++;
            }
        }
        return systemsBrokenNumber;
    }

    private static int calculateInfiltratorNumber(Array<Infiltrator> infiltrators) {
        int numberOfInfiltrators = infiltrators.size;
        for (Infiltrator infiltrator : infiltrators) {
            if (infiltrator.isImprisoned()) {
                numberOfInfiltrators--;
            }
        }
        return numberOfInfiltrators;
    }
}
