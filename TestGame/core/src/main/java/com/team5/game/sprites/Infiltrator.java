package com.team5.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.team5.game.MainGame;
import com.team5.game.environment.SystemChecker;
import com.team5.game.screens.PlayScreen;
import com.team5.game.screens.WinScreen;
import com.team5.game.sprites.animation.Animator;
import com.team5.game.sprites.pathfinding.InfiltratorAIBehaviour;
import com.team5.game.sprites.pathfinding.Node;
import com.team5.game.sprites.pathfinding.NodeGraph;
import com.team5.game.tools.Atlas;
import com.team5.game.tools.Constants;
import com.team5.game.tools.GameController;

import java.util.Random;

public class Infiltrator extends NPC {

    /*
    Infiltrator contains all of the information regarding an Infiltrator
    in the game with reference to the AI used for it's pathfinding
     */

    //MainGame reference
    MainGame game;

    //GameController reference
    GameController gameController;

    //States
    boolean caught = false;
    boolean imprisoned = false;

    //AI reference
    InfiltratorAIBehaviour ai;

    boolean breaking;

    //SystemChecker reference
    SystemChecker systemChecker;

    //Audio
    Sound pass = Gdx.audio.newSound(Gdx.files.internal("Audio/Sound Effects/pass.wav"));

    public Infiltrator(MainGame game, PlayScreen screen, GameController gameController, World world,
                       NodeGraph graph, Node node, Vector2 position) {
        super(screen, world, graph, node, position);
        this.game = game;

        this.gameController = gameController;

        systemChecker = gameController.getSystemChecker();

        ai = new InfiltratorAIBehaviour(gameController, this, graph, node);
    }

    //To be called every frame to move and animate the infiltrator.
    @Override
    public void update(float delta) {
        if (!caught) {
            ai.update(delta);
            if (ai.isWaiting() && ai.isBreaking()) {
                breaking = true;
                b2body.setLinearVelocity(0f, 0f);
                x = b2body.getPosition().x;
                y = b2body.getPosition().y;
                outlineButton.setPosition(x - 4, y - 4);

                anim.play("interact");
                outlineAnim.play("interact");

                currentSprite = anim.getSprite();
                outlineImage = new Image(outlineAnim.getSprite());
                outlineButton.getStyle().imageOver = outlineImage.getDrawable();
            } else {
                handleAnimations(direction);
            }

        } else {
            if (!imprisoned && caught && anim.finished()) {
                b2body.setTransform(gameController.getBrig().imprison(), 0);
                x = b2body.getPosition().x;
                y = b2body.getPosition().y;
                if (gameController.getBrig().allCaught()) {
                    game.setScreen(new WinScreen(game));
                }

                imprisoned = true;
            }
            currentSprite = anim.getSprite();
        }
    }

    //Sets up all the base Animations
    @Override
    public void setup() {

        Random random = new Random();
        int sprite = random.nextInt(6) + 1;
        anim = new Animator("idle", "NPC/" + sprite + "/Idle");
        anim.add("run", "NPC/" + sprite + "/Run");
        anim.add("interact", "NPC/" + sprite + "/Interact");
        anim.add("caught", "NPC/Infiltrator/Caught");
        facingRight = true;
        currentSprite = anim.getSprite();

        //Setting outline animations
        outlineAnim = new Animator("idle", "NPC/" + sprite + "/IdleOutline");
        outlineAnim.add("run", "NPC/" + sprite + "/RunOutline");
        outlineAnim.add("interact", "NPC/" + sprite + "/InteractOutline");

        outlineImage = new Image(outlineAnim.getSprite());
        outlineButton = new ImageButton(new Image(Atlas.getInstance().findRegion("Empty")).getDrawable());

        outlineButton.setPosition(x - 4, y - 4);
        outlineButton.setSize(Constants.TILE_SIZE + 8, Constants.TILE_SIZE + 8);

        outlineButton.getStyle().imageOver = outlineImage.getDrawable();

        outlineButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (!caught) {
                    pass.play(0.3f);
                    caught = true;
                    anim.play("caught");
                    outlineButton.getStyle().imageOver =
                            new Image(Atlas.getInstance().findRegion("Empty")).getDrawable();
                }
            }
        });

        screen.stage.addActor(outlineButton);
    }

    //Ability that changes the appearance
    public void changeSkin() {
        Random random = new Random();
        int sprite = random.nextInt(6) + 1;
        anim = new Animator("idle", "NPC/" + sprite + "/Idle");
        anim.add("run", "NPC/" + sprite + "/Run");
        anim.add("interact", "NPC/" + sprite + "/Interact");
        anim.add("caught", "NPC/Infiltrator/Caught");
        facingRight = true;
        currentSprite = anim.getSprite();

        outlineAnim = new Animator("idle", "NPC/" + sprite + "/IdleOutline");
        outlineAnim.add("run", "NPC/" + sprite + "/RunOutline");
        outlineAnim.add("interact", "NPC/" + sprite + "/InteractOutline");

        outlineImage = new Image(outlineAnim.getSprite());
        outlineButton.getStyle().imageOver = outlineImage.getDrawable();
    }


    public void dispose() {
        pass.dispose();
        ai.dispose();
    }
}
