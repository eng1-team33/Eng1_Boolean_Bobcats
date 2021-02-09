package com.team5.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.team5.game.MainGame;
import com.team5.game.tools.*;

import java.io.IOException;
import java.net.URISyntaxException;

public class MainMenuScreen implements Screen {

    /*
    MainMenuScreen is the class that renders the main menu
    and transfers over to the PlayScreen or quits the application
     */

    //Main Game Reference
    MainGame game;

    //Menu buttons
    ImageButton playButton;
    ImageButton loadButton;
    ImageButton quitButton;

    Stage stage;

    Texture title;

    //Audio
    Sound click = Gdx.audio.newSound(Gdx.files.internal("Audio/Sound Effects/click.wav"));

    //Menu positions
    Vector2 playPos = new Vector2(Constants.CAMERA_WIDTH/2-48, 100);
    Vector2 loadPos = new Vector2(Constants.CAMERA_WIDTH/2-48, 60);
    Vector2 quitPos = new Vector2(Constants.CAMERA_WIDTH/2-48, 20);
    Vector2 titlePos = new Vector2(Constants.CAMERA_WIDTH/2-120, 140);

    //Colliders
    private final World world;
    private final Box2DDebugRenderer b2dr;

    //Reference
    private final CustomCamera camera;

    public MainMenuScreen (final MainGame game){

        this.game = game;
        title = new Texture("Sprites/Menu/Title.png");

        //Collisions
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        //Camera
        camera = new CustomCamera();

        //Buttons
        setupButtons();

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        b2dr.render(world, new Matrix4(camera.cam.combined));

        game.batch.setProjectionMatrix(camera.cam.combined);

        game.batch.begin();
        game.batch.draw(title, titlePos.x, titlePos.y);
        game.batch.end();

        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        camera.port.update(width, height);
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
        this.dispose();
        stage.dispose();
        world.dispose();
        b2dr.dispose();
        click.dispose();
    }

    //Custom functions from here

    public void update(float delta){
        world.step(1/60f, 6, 2);

        //Updates Camera
        camera.update();
    }

    void setupButtons() {
        stage = new Stage(camera.port);
        Gdx.input.setInputProcessor(stage);

        playButton = new ImageButton(new Image(new Texture("Sprites/Menu/PlayOff.png")).getDrawable());
        loadButton = new ImageButton(new Image(new Texture("Sprites/Menu/LoadOff.png")).getDrawable());
        quitButton = new ImageButton(new Image(new Texture("Sprites/Menu/ExitOff.png")).getDrawable());

        playButton.setPosition(playPos.x, playPos.y);
        loadButton.setPosition(loadPos.x, loadPos.y);
        quitButton.setPosition(quitPos.x, quitPos.y);

        playButton.setSize(96, 32);
        loadButton.setSize(96, 32);
        quitButton.setSize(96, 32);

        playButton.getStyle().imageOver = new Image(new Texture("Sprites/Menu/PlayOn.png")).getDrawable();
        loadButton.getStyle().imageOver = new Image(new Texture("Sprites/Menu/LoadOn.png")).getDrawable();
        quitButton.getStyle().imageOver = new Image(new Texture("Sprites/Menu/ExitOn.png")).getDrawable();

        stage.addActor(playButton);
        stage.addActor(loadButton);
        stage.addActor(quitButton);

        playButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                click.play(0.5f, 1.5f, 0);
                game.setScreen(new DifficultyScreen(game));
            }
        });

        // CHANGE FUNCTION OF LOAD BUTTON
        try {
            loadButton.addListener(new LoadButtonClickListener(game, new GameStorage(), click));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        quitButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                click.play(0.5f, 1.5f, 0);
                Gdx.app.exit();
            }
        });
    }
}