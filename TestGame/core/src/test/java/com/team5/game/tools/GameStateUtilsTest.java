package com.team5.game.tools;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.team5.game.MainGame;
import com.team5.game.screens.PlayScreen;
import com.team5.game.sprites.Infiltrator;
import com.team5.game.sprites.NPC;
import com.team5.game.sprites.Player;
import com.team5.game.sprites.pathfinding.NodeGraph;
import com.team5.game.sprites.pathfinding.System;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameStateUtilsTest {

    @Mock
    Player mockPlayer;
    @Mock
    Infiltrator mockInfiltrator;
    @Mock
    Infiltrator mockInfiltrator2;
    @Mock
    GameController mockGameController;
    @Mock
    NodeGraph mockNodeGraph;

    Array<Infiltrator> infiltratorArray = new Array<>();
    Array<NPC> npcArray = new Array<>();
    Array<System> systemArray = new Array<>();

    GameState gameState;

    @Mock
    private TextureAtlas mockTextureAtlas;

    @BeforeEach
    void setup() {
        Atlas.overrideTextureAtlasWith(mockTextureAtlas);
        GameState.initialise();
        gameState = GameState.getInstance();

        infiltratorArray.add(mockInfiltrator);
        infiltratorArray.add(mockInfiltrator2);
        when(mockGameController.getPlayer()).thenReturn(mockPlayer);
        when(mockGameController.getNodeGraph()).thenReturn(mockNodeGraph);
        when(mockNodeGraph.getSystems()).thenReturn(systemArray);
        when(mockGameController.getInfiltrators()).thenReturn(infiltratorArray);
    }

    @Test
    void createGameState_Difficulty() {
        Difficulty.setDifficulty(2);
        GameStateUtils.createGameState(mockGameController);

        assertEquals(2, gameState.getDifficulty());
    }

    @Test
    void createGameState_Health() {
        when(mockPlayer.getHealth()).thenReturn(5);
        GameStateUtils.createGameState(mockGameController);
        assertEquals(5, gameState.getHealth());
    }

    @Test
    void createGameState_Player() {
        when(mockPlayer.getX()).thenReturn(6.1f);
        when(mockPlayer.getY()).thenReturn(7.2f);
        GameStateUtils.createGameState(mockGameController);

        assertEquals(6.1f, gameState.getPlayerX());
        assertEquals(7.2f, gameState.getPlayerY());
    }

    @Test
    void createGameState_Systems() {
        System topLeftReactor = new System("Reactor", 46.5f, 39.5f, "Top Left Reactor", 45, 40);
        System topRightReactor = new System("Reactor", 52.5f, 39.5f, "Top Right Reactor", 51, 40);
        System bottomLeftReactor = new System("Reactor", 46.5f, 36.5f, "Bottom Left Reactor", 45, 33);

        topRightReactor.destroy();

        systemArray.add(topLeftReactor);
        systemArray.add(topRightReactor);
        systemArray.add(bottomLeftReactor);

        GameStateUtils.createGameState(mockGameController);

        assertEquals(false, gameState.getSystemsBroken()[0]);
        assertEquals(true, gameState.getSystemsBroken()[1]);
        assertEquals(false, gameState.getSystemsBroken()[2]);

        assertEquals(1, gameState.getSystemsBrokenNumber());
    }

}