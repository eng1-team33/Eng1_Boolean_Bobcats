package com.team5.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.team5.game.Sprites.Pathfinding.Node;
import com.team5.game.Sprites.Pathfinding.NodeGraph;
import com.team5.game.Sprites.Pathfinding.Pathfinder;
import com.team5.game.Tools.Constants;

import java.util.Random;

public class NPC extends Sprite {

    public TextureAtlas atlas;

    //Collider
    public World world;
    public Body b2body;
    private int size = 16;
    private Box2DDebugRenderer b2dr;

    //Animations
    public static final float frameDuration = 0.2f;
    float stateTime;

    Animation<TextureRegion> currentAnim;
    Animation<TextureRegion> idleAnim;
    Animation<TextureRegion> runAnim;

    public TextureRegion currentSprite;

    boolean facingRight;

    //Movement
    float speed = 500;

    private Vector2 direction;

    public float x = 640;
    public float y = 120;

    //AI
    NodeGraph graph;

    public Node currentNode;
    public Node goalNode;

    GraphPath<Node> path;

    int currentIndex;

    public NPC(World world, TextureAtlas atlas, NodeGraph graph, Node node, Vector2 position){
        this.world = world;
        this.atlas = atlas;
        this.graph = graph;
        this.currentNode = node;
        this.x = position.x;
        this.y = position.y;

        newTarget();

        defineCharacter();
        setupAnimations();
    }

    public void update(float delta){
        AI();
        handleAnimations(direction);
        stateTime += delta;
    }

    public void defineCharacter(){
        BodyDef bodDef = new BodyDef();
        bodDef.position.set(x, y);
        bodDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodDef);

        FixtureDef fixDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7.5f);
        shape.setPosition(new Vector2(size/2,size/2));
        fixDef.shape = shape;

        fixDef.filter.groupIndex = Constants.GROUP_PLAYER;

        b2body.createFixture(fixDef);
    }

    public void setupAnimations(){
        //Setting initial values of animations
        idleAnim = new Animation<TextureRegion>(frameDuration, atlas.findRegions("NPC/Idle"));
        runAnim = new Animation<TextureRegion>(frameDuration, atlas.findRegions("NPC/Run"));
        currentAnim = idleAnim;
        facingRight = true;
        currentSprite = currentAnim.getKeyFrame(stateTime, true);
    }

    void newTarget(){
        goalNode = graph.getRandom(currentNode);
        path = graph.findPath(currentNode, goalNode);
    }

    void AI(){
        if (x < goalNode.getX() + 8 && x > goalNode.getX() - 8 &&
            y < goalNode.getY() + 8 && y > goalNode.getY() - 8){
            currentNode = goalNode;
            currentIndex = 1;

            newTarget();

        } else if (x < path.get(currentIndex).getX() + 8 && x > path.get(currentIndex).getX() - 8 &&
                y < path.get(currentIndex).getY() + 8 && y > path.get(currentIndex).getY() - 8){
            currentNode = path.get(currentIndex);
            currentIndex++;
        }

        Vector2 resultant = new Vector2(path.get(currentIndex).getX() - x,
                path.get(currentIndex).getY() - y).nor();

        direction = new Vector2(resultant.x * speed, resultant.y * speed);
    }

    void handleAnimations(Vector2 direction){
        //Deciding which animation will be played each frame
        if (direction.isZero(0.01f)){
            b2body.setLinearVelocity(0f, 0f);
            currentAnim = idleAnim;
        } else {
            b2body.setLinearVelocity(direction);
            currentAnim = runAnim;
        }

        x = b2body.getPosition().x;
        y = b2body.getPosition().y;

        currentSprite = currentAnim.getKeyFrame(stateTime, true);

        if ((b2body.getLinearVelocity().x < 0 || !facingRight) && !currentSprite.isFlipX()){
            currentSprite.flip(true, false);
            facingRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || facingRight) && currentSprite.isFlipX()){
            currentSprite.flip(true, false);
            facingRight = true;
        }
    }

}
