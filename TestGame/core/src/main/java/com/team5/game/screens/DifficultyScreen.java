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
import com.team5.game.tools.Constants;
import com.team5.game.tools.CustomCamera;
import com.team5.game.tools.Difficulty;

public class DifficultyScreen implements Screen {

    /*
    DifficultyScreen is the class that renders the difficulty selection screen
    and transfers over to the PlayScreen or the MainMenuScreen
     */

    //Main Game Reference
    MainGame game;

    //Menu buttons
    ImageButton easyButton;
    ImageButton normalButton;
    ImageButton hardButton;
    ImageButton backButton;

    Stage stage;

    //Audio
    Sound click = Gdx.audio.newSound(Gdx.files.internal("Audio/Sound Effects/click.wav"));

    //Menu positions
    Vector2 easyPos = new Vector2(Constants.CAMERA_WIDTH/2-48, 140);
    Vector2 normalPos = new Vector2(Constants.CAMERA_WIDTH/2-48, 100);
    Vector2 hardPos = new Vector2(Constants.CAMERA_WIDTH/2-48, 60);
    Vector2 backPos = new Vector2(Constants.CAMERA_WIDTH/2-48, 20);

    //Colliders
    private final World world;
    private final Box2DDebugRenderer b2dr;

    //Reference
    private final CustomCamera camera;

    public DifficultyScreen (final MainGame game){

        this.game = game;


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

    void setupButtons(){
        stage = new Stage(camera.port);
        Gdx.input.setInputProcessor(stage);

        easyButton = new ImageButton(new Image(new Texture("Sprites/Menu/EasyOff.png")).getDrawable());
        normalButton = new ImageButton(new Image(new Texture("Sprites/Menu/MediumOff.png")).getDrawable());
        hardButton = new ImageButton(new Image(new Texture("Sprites/Menu/HardOff.png")).getDrawable());
        backButton = new ImageButton(new Image(new Texture("Sprites/Menu/BackOff.png")).getDrawable());

        easyButton.setPosition(easyPos.x, easyPos.y);
        normalButton.setPosition(normalPos.x, normalPos.y);
        hardButton.setPosition(hardPos.x, hardPos.y);
        backButton.setPosition(backPos.x, backPos.y);

        easyButton.setSize(96, 32);
        normalButton.setSize(96, 32);
        hardButton.setSize(96, 32);
        backButton.setSize(96, 32);

        easyButton.getStyle().imageOver = new Image(new Texture("Sprites/Menu/EasyOn.png")).getDrawable();
        normalButton.getStyle().imageOver = new Image(new Texture("Sprites/Menu/MediumOn.png")).getDrawable();
        hardButton.getStyle().imageOver = new Image(new Texture("Sprites/Menu/HardOn.png")).getDrawable();
        backButton.getStyle().imageOver = new Image(new Texture("Sprites/Menu/BackOn.png")).getDrawable();

        stage.addActor(easyButton);
        stage.addActor(normalButton);
        stage.addActor(hardButton);
        stage.addActor(backButton);

        easyButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                click.play(0.5f, 1.5f, 0);
                Difficulty.setDifficulty(0);
                game.setScreen(new PlayScreen(game));
            }
        });

        normalButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                click.play(0.5f, 1.5f, 0);
                Difficulty.setDifficulty(1);
                game.setScreen(new PlayScreen(game));
            }
        });

        hardButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                click.play(0.5f, 1.5f, 0);
                Difficulty.setDifficulty(2);
                game.setScreen(new PlayScreen(game));
            }
        });

        backButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                click.play(0.5f, 1.5f, 0);
                game.setScreen(new MainMenuScreen(game));
            }
        });
    }
}
