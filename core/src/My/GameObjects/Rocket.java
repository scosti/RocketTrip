package My.GameObjects;

import My.Game.Assets;
import My.Game.LevelsManager;
import My.Game.RocketTrip;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import static My.Game.RocketTrip.HEIGHT;
import static My.Game.RocketTrip.PPM;

/**
 *  <p>
 *  This class extends {@link GameObject} and defines a Rocket.
 *  There are three state for the rocket: START,FLY and TURBO.
 *  When the state is set TURBO the color of the rocket becomes green (changing animation, using animation_trip instead
 *  of animation_normal).
 *  </p>
 *  @author Benedetti Costi
 */

public class Rocket extends GameObject implements Disposable{
    public static final float ROCKET_WIDTH = 0.4f;
    public static final float ROCKET_HEIGHT = 0.6f;

    public enum State{START,FLY,TURBO}

    private State internalState;
    private RocketTrip game;
    private TextureRegion rocket;
    private TextureAtlas textureAtlas;
    private Animation animation_normal;
    private Animation animation_trip;
    private LevelsManager levelsManager;

    public Rocket(RocketTrip game, Vector2 position,LevelsManager levelsManager) {
        super(position.x,position.y,ROCKET_WIDTH,ROCKET_HEIGHT);
        this.game=game;
        this.levelsManager = levelsManager;
        internalState = State.START;
        rocket = new TextureRegion(new Texture("img/rocket.png"));

        textureAtlas = Assets.manager.get(Assets.rocket_pack,TextureAtlas.class);
        Array<TextureRegion> normal = new Array<TextureRegion>();
        normal.add(textureAtlas.findRegion("rocket_1"));
        normal.add(textureAtlas.findRegion("rocket_2"));
        normal.add(textureAtlas.findRegion("rocket_3"));
        animation_normal = new Animation(0.2f,normal);

        Array<TextureRegion> trip = new Array<TextureRegion>();
        trip.add(textureAtlas.findRegion("rocket_trip1"));
        trip.add(textureAtlas.findRegion("rocket_trip2"));
        trip.add(textureAtlas.findRegion("rocket_trip3"));
        animation_trip = new Animation(0.1f,trip);

    }


    private static float stateTime = 0f;
    @Override
    public void draw(Batch batch, float alpha) {
        stateTime += Gdx.graphics.getDeltaTime();
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * alpha);
        if(internalState == State.START || getVelocity().y < -0.5f)
            batch.draw(rocket, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        if(internalState == State.FLY && getVelocity().y >= -0.5f)
            batch.draw((TextureRegion) animation_normal.getKeyFrame(stateTime,true), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        if(internalState == State.TURBO && getVelocity().y >= -0.5f)
            batch.draw((TextureRegion) animation_trip.getKeyFrame(stateTime,true), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        cameraFollow();
        updateState(delta);
        checkBorders();
    }

    /**
     *  <p>
     *  This method is used to update at any frame the state of the rocket.
     *  It is set to TURBO the velocity is set higher and camera is managed properly.
     *  Also is managed the phases of TURBO.
     *  </p>
     */

    static float turbo_time = 0f;
    private void updateState(float delta){
        switch (internalState){
            case TURBO:
                turbo_time+=delta;
                setVelocity(new Vector2(getVelocity().x, 11));
                if(turbo_time<3.0f) {
                    camera_y -= 1.5*delta;
                }
                if(turbo_time>=3.0f && turbo_time<6.0f){
                    camera_y += 1.5*delta;
                }
                if(turbo_time>=6.0f && turbo_time<7.0f){
                    setVelocity(new Vector2(getVelocity().x, 3 + levelsManager.getLevel()*0.2f));
                }
                if(turbo_time>=7.0f){
                    internalState = State.FLY;
                    turbo_time = 0f;
                }
                break;
            case FLY:
                setVelocity(new Vector2(getVelocity().x, 3 + levelsManager.getLevel()*0.2f));
            break;
            default:break;
        }
    }

    public boolean isActive(){
        return internalState != State.START;
    }

    /**
     *  <p>
     *  This method is used to manage the camera that must follow the rocket in y-axis at the same velocity.
     *  </p>
     */

    private float camera_y = 3f;
    private void cameraFollow(){
        game.getCamera().position.x = 0;
        if ((getY()<(HEIGHT/(PPM*4))-Rocket.ROCKET_HEIGHT/2) && internalState==State.FLY){
            game.getCamera().position.y = game.getCamera().position.y + Gdx.graphics.getDeltaTime();
        }else {
            if (getVelocity().y > 0)
                game.getCamera().position.y = camera_y + getY();
        }
    }

    /**
     *  <p>
     *  This method is used to check the bounds of the game to avoid that the rocket could exit from the screen of the
     *  device.
     *  </p>
     */

    private void checkBorders(){
        if (getX() < -RocketTrip.LEVEL_WIDTH/2){
            setPosition(-RocketTrip.LEVEL_WIDTH/2,getY());
            setVelocity(new Vector2(0,getVelocity().y));
        }
        if (getX()+ROCKET_WIDTH > RocketTrip.LEVEL_WIDTH/2){
            setPosition(RocketTrip.LEVEL_WIDTH/2-ROCKET_WIDTH,getY());
            setVelocity(new Vector2(0,getVelocity().y));
        }
    }

    public State getInternalState() {
        return internalState;
    }

    public void setInternalState(State internalState) {
        this.internalState = internalState;
    }

    @Override
    public void dispose() {
        rocket.getTexture().dispose();
    }

}
