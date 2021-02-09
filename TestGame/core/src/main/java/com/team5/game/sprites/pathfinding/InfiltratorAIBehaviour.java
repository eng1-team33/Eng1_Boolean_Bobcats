package com.team5.game.sprites.pathfinding;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.team5.game.environment.SystemChecker;
import com.team5.game.sprites.Infiltrator;
import com.team5.game.sprites.Player;
import com.team5.game.tools.Difficulty;
import com.team5.game.tools.GameController;

public class InfiltratorAIBehaviour extends NPCAIBehaviour {

    /*
    InfiltratorAIBehaviour contains all of the basic AI for Infiltrators.

    It makes the Infiltrators either follow the same AI as the NPCs or
    target a system and break it.
     */

    Infiltrator npc;

    //Abilities
    Player player;

    float changeCooldown = Difficulty.getChangeCooldown();
    float timer = changeCooldown;

    float distance = 50;

    //Systems
    System goalSystem;
    SystemChecker systemChecker;
    Array<System> systems;

    //States
    boolean breaking;

    float breakOdds = 1f;

    //Audio
    Sound explosion = Gdx.audio.newSound(Gdx.files.internal("Audio/Sound Effects/explosion.wav"));

    public InfiltratorAIBehaviour(GameController gameController, Infiltrator infiltrator,
                                  NodeGraph graph, Node node) {
        super(infiltrator, graph, node);
        npc = infiltrator;
        systemChecker = gameController.getSystemChecker();
        player = gameController.getPlayer();
        systems = new Array<>();
        systems.addAll(graph.getSystems());

        goalSystem = systems.random();
    }

    //Used to randomly generate a target system from the systems that
    //haven't been visited yet.
    void newSystemTarget() {
        goalSystem = systems.random();
        goalNode = goalSystem;
        path = graph.findPath(currentNode, goalNode);

        target = path.get(currentIndex).randomPos();
    }

    @Override
    public void update(float delta) {
        if (waiting) {
            wait(delta);
            npc.direction = Vector2.Zero;
        } else {
            npc.direction = move(npc.x, npc.y);
        }

        //This changes the infiltrators disguise every 30 seconds
        //But only if it's away from the player.
        timer -= delta;
        if (timer <= 0) {
            if (Vector2.dst(player.getX(), player.getY(), npc.x, npc.y) > distance) {
                npc.changeSkin();
                timer = changeCooldown;
            }
        }
    }

    //wait is changed to break a system if they're at one.
    @Override
    public void wait(float delta) {
        if (waitTime <= 0f || goalSystem.getBroken()) {
            if (breaking && !goalSystem.getBroken()) {
                explosion.play(0.2f);
                goalSystem.destroy();
                systemChecker.breakSystem();
                player.givePowerUp();
            }
            waiting = false;
            systems.removeValue(goalSystem, false);

            if (random.nextFloat() < breakOdds) {
                breaking = true;
                newSystemTarget();
            } else {
                breaking = false;
                newTarget();
            }
        } else {
            waitTime -= delta;
        }
    }

    public boolean isBreaking() {
        return breaking;
    }

    public boolean isWaiting() {
        return waiting;
    }

    public void dispose() {
        explosion.dispose();
    }
}
