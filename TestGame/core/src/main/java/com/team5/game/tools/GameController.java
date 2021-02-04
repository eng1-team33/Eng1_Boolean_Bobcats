package com.team5.game.tools;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.team5.game.MainGame;
import com.team5.game.environment.Brig;
import com.team5.game.environment.SystemChecker;
import com.team5.game.screens.LoseScreen;
import com.team5.game.screens.PlayScreen;
import com.team5.game.sprites.Infiltrator;
import com.team5.game.sprites.NPC;
import com.team5.game.sprites.Player;
import com.team5.game.sprites.Teleporters;
import com.team5.game.sprites.pathfinding.Node;
import com.team5.game.sprites.pathfinding.NodeGraph;
import com.team5.game.sprites.pathfinding.System;

public class GameController {

    //References
    MainGame game;
    Player player;
    Teleporters teleporters;
    NodeGraph graph;
    Brig brig;
    SystemChecker systemChecker;

    Array<NPC> npcs;
    Array<Infiltrator> infiltrators;

    //NPC numbers
    int noNPCs = Difficulty.getNoNPCs();
    int noInfiltrators = Difficulty.getNoInfiltrators();

    public GameController(MainGame game, PlayScreen screen) {
        this.game = game;

        //Player
        player = new Player(game, screen.getWorld());

        //Teleporters
        teleporters = new Teleporters(screen);

        //Checkers
        brig = new Brig();
        systemChecker = new SystemChecker();

        //NPCs
        graph = new NodeGraph();
        npcs = new Array<>();
        infiltrators = new Array<>();

        for (int i = 0; i < noNPCs; i++) {
            Node node = graph.getRandomRoom();
            NPC npc = new NPC(screen, screen.getWorld(), graph,
                    node, new Vector2(node.getX(), node.getY()));
            npcs.add(npc);
        }
        for (int i = 0; i < noInfiltrators; i++) {
            System node = graph.getRandomSystem();
            Infiltrator npc = new Infiltrator(game, screen, this, screen.getWorld(), graph,
                    node, new Vector2(node.getX(), node.getY()));
            infiltrators.add(npc);
        }
    }

    public void draw(SpriteBatch batch) {

        graph.drawSystems(batch);

        for (NPC npc : npcs) {
            batch.draw(npc.currentSprite, npc.x, npc.y);
        }
        for (Infiltrator bad : infiltrators) {
            batch.draw(bad.currentSprite, bad.x, bad.y);
        }
    }

    public void drawPlayer(SpriteBatch batch) {
        player.draw(batch);
    }

    public void update(float delta) {
        checkSystems();

        //Moves player
        player.update();

        //Moves npc
        for (NPC npc : npcs) {
            npc.update(delta);
        }
        for (Infiltrator bad : infiltrators) {
            bad.update(delta);
        }
    }

    void checkSystems() {
        if (systemChecker.allSystemsBroken()) {
            game.setScreen(new LoseScreen(game));
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Teleporters getTeleporters() {
        return teleporters;
    }

    public SystemChecker getSystemChecker() {
        return systemChecker;
    }

    public Brig getBrig() {
        return brig;
    }

    public void dispose() {
        for (NPC npc : npcs) {
            npc.dispose();
        }
        for (Infiltrator bad : infiltrators) {
            bad.dispose();
        }
    }

}
