package My.Screens;

import My.Game.*;
import My.GameObjects.*;
import My.Generators.Renderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.*;
import My.Screens.GameScreenManager.STATE;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.viewport.ExtendViewport;


import static My.Game.RocketTrip.*;

public class PlayScreen extends AbstractScreen implements Pool.Poolable{
    public enum GameState{PLAY,PAUSE,CRASH}
    private GameState internalState;

    private Renderer renderer;
    private Hud hud;
    private Stage stage;
    private CollisionCheck cc;
    private MusicManager musicManager;

    private Color sky;
    private Rocket rocket;
    private Explosion explosion;
    private BackgroundParallax backgroundParallax;


    PlayScreen(final RocketTrip game) {
        super(game);
        viewport = new ExtendViewport(RocketTrip.WIDTH/ RocketTrip.PPM, RocketTrip.HEIGHT/PPM,game.getCamera());
        hud = new Hud(game);
        backgroundParallax = new BackgroundParallax(game);
    }

    @Override
    public void show() {
        internalState = GameState.PLAY;
        musicManager = new MusicManager();
        sky = new Color(.28f, .29f, .30f, 1);
        Assets.manager.finishLoading();
        stage = new Stage(viewport,game.getBatch());
        renderer = new Renderer(stage);
        hud.load();
        backgroundParallax.load();
        rocket = new Rocket(game,new Vector2(-Rocket.ROCKET_WIDTH/2,(HEIGHT/(PPM*9))-Rocket.ROCKET_HEIGHT/2),renderer.getLevelsManager());
        explosion = new Explosion();
        stage.addActor(rocket);

        cc = new CollisionCheck(this,rocket);

        MyGestureListener myGestureListener = new MyGestureListener(this, rocket);
        InputMultiplexer im = new InputMultiplexer(stage,hud.getStage(),new GestureDetector(myGestureListener));
        Gdx.input.setInputProcessor(im);
    }

    private void phoneInput(){
        if (rocket.isActive())
            rocket.setVelocity(new Vector2(-Gdx.input.getAccelerometerX()*2,rocket.getVelocity().y));
    }
    private void inputKeyboard(){
        if(Gdx.input.isKeyPressed(Input.Keys.Q)){
            game.getGsm().setScreen(STATE.HIGHSCORE);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && rocket.isActive()){
            rocket.setVelocity(new Vector2(8,rocket.getVelocity().y));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && rocket.isActive()){
            rocket.setVelocity(new Vector2(-8,rocket.getVelocity().y));
        }
    }

    public void update(float dt){
        renderer.update(rocket.getY(),rocket.getX());
        cc.update(renderer.getEnemyGenerator().getList(), renderer.getFuelTankGenerator().getList());
        hud.update(rocket.getY(),rocket.getInternalState(),renderer.getLevelsManager());
        backgroundParallax.update(game.getCamera().position.y);
        phoneInput();
        inputKeyboard();
        stage.addActor(rocket);
    }

    private float time = 0;
    @Override
    public void render(float delta) {
        musicManager.update(getInternalState(),rocket.getInternalState());
        if (internalState == GameState.PLAY) {
            Gdx.gl.glClearColor(sky.r, sky.g, sky.b, sky.a);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            update(delta);
            backgroundParallax.getStage().draw();
            stage.act();
            stage.draw();
            game.getBatch().setProjectionMatrix(hud.getStage().getCamera().combined);
            hud.getStage().act();
            hud.getStage().draw();
        }
         if(internalState == GameState.PAUSE){
             backgroundParallax.getStage().draw();
            stage.draw();
            game.getBatch().setProjectionMatrix(hud.getStage().getCamera().combined);
            hud.getStage().act();
            hud.getStage().draw();
            hud.update((int) rocket.getY(),rocket.getInternalState(),renderer.getLevelsManager());
        }
        if(internalState == GameState.CRASH){
            Gdx.gl.glClearColor(sky.r, sky.g, sky.b, sky.a);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            backgroundParallax.getStage().draw();
            rocket.setVisible(false);
            rocket.setVelocity(new Vector2(0,0));
            stage.act();
            stage.draw();
            game.getBatch().setProjectionMatrix(hud.getStage().getCamera().combined);
            hud.getStage().draw();
            explosion.boom(WIDTH/2+rocket.getX()*PPM + rocket.getWidth()*PPM,
                    HEIGHT/2 + (rocket.getY()*PPM-game.getCamera().position.y*PPM)+ rocket.getHeight()*PPM/2);
            explosion.update(delta);
            game.getBatch().begin();
            explosion.getParticleEffect().draw(game.getBatch());
            getGame().getBatch().end();
            time+=delta;
            if(time > 1.5) {
                if(game.db != null)
                    game.getGsm().save(hud.getMeters());
                reset();
                game.getGsm().setScreen(STATE.HIGHSCORE);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        internalState = GameState.PAUSE;
    }

    @Override
    public void resume() {
        internalState = GameState.PLAY;
    }

    public RocketTrip getGame(){
        return game;
    }
    public Hud getHud() {
        return hud;
    }
    public GameState getInternalState() {
        return internalState;
    }

    public void setInternalState(GameState internalState) {
        this.internalState = internalState;
    }

    @Override
    public void hide() {
        rocket.dispose();
        stage.dispose();
        hud.getStage().dispose();
        backgroundParallax.getStage().dispose();
    }

    @Override
    public void reset() {
        time= 0;
        explosion.reset();
        explosion.dispose();
        internalState = GameState.PLAY;
        hud.reset();
        backgroundParallax.reset();
        stage.clear();
        renderer.getLevelsManager().setLevel(1);
    }

    @Override
    public void dispose() {
        super.dispose();
        musicManager.dispose();
    }
}
