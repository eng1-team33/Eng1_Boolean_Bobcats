package com.team5.game.tools;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.team5.game.MainGame;
import com.team5.game.environment.Brig;
import com.team5.game.environment.SystemChecker;
import com.team5.game.factories.*;
import com.team5.game.screens.PlayScreen;
import com.team5.game.sprites.Player;
import com.team5.game.sprites.Teleporters;
import com.team5.game.sprites.pathfinding.Node;
import com.team5.game.sprites.pathfinding.NodeGraph;
import com.team5.game.sprites.pathfinding.System;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    @Mock
    MainGame mockGame;
    @Mock
    PlayScreen mockScreen;
    @Mock
    PlayerFactory mockPlayerFactory;
    @Mock
    TeleportersFactory mockTeleportersFactory;
    @Mock
    BrigFactory mockBrigFactory;
    @Mock
    SystemCheckerFactory mockSystemCheckerFactory;
    @Mock
    NodeGraphFactory mockNodeGraphFactory;
    @Mock
    InfiltratorFactory mockInfiltratorFactory;
    @Mock
    NPCFactory mockNPCFactory;

    @Mock
    Player mockPlayer;
    @Mock
    Teleporters mockTeleporters;
    @Mock
    Brig mockBrig;
    @Mock
    SystemChecker mockSystemChecker;
    @Mock
    NodeGraph mockNodeGraph;
    @Mock
    System mockSystem;
    @Mock
    Node mockRoom;
    @Mock
    Audio mockAudio;
    @Mock
    Files mockFiles;

    GameController gameController;

    @BeforeEach
    void setup() {

        Gdx.audio = mockAudio;
        Gdx.files = mockFiles;

        when(mockNodeGraph.getRandomRoom()).thenReturn(mockSystem);
        when(mockPlayerFactory.create()).thenReturn(mockPlayer);
        when(mockTeleportersFactory.create()).thenReturn(mockTeleporters);
        when(mockBrigFactory.create()).thenReturn(mockBrig);
        when(mockSystemCheckerFactory.create()).thenReturn(mockSystemChecker);
        when(mockNodeGraphFactory.create()).thenReturn(mockNodeGraph);

        when(mockNodeGraph.getRandomSystem()).thenReturn(mockSystem);
        when(mockNodeGraph.getRandomRoom()).thenReturn(mockRoom);


        GameState.initialise();

    }
/*
    @Test
    void testAllNPCsAreCreated() {

        when(mockRoom.getX()).thenReturn(987.1f);
        when(mockRoom.getY()).thenReturn(789.1f);

        GameState gameState = GameState.getInstance();

        gameController = new GameController(mockGame, mockScreen, mockPlayerFactory, mockTeleportersFactory, mockBrigFactory, mockSystemCheckerFactory, mockNodeGraphFactory, mockInfiltratorFactory, mockNPCFactory);

        // 72 is number of NPCs for difficulty of 2
        verify(mockNPCFactory, times(72)).create(mockNodeGraph, gameController, mockRoom, 987.1f,789.1f);
    }

 */
}