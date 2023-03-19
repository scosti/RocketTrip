package My.GameObjects;


import My.Game.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Array;

/**
 *  <p>
 *  This class extends {@link GameObject} and defines a Turbo.
 *  It is also implemented an animation.
 *  @author Benedetti Costi
 */

public class Turbo extends GameObject{
    private static final float TURBO_WIDTH = 1f;
    private static final float TURBO_HEIGHT = 1f;
    private static final float TURBO_X = 0;

    private Animation animation;
    private TextureAtlas textureAtlas;
    private float stateTime;

    public Turbo(float y) {
        super(TURBO_X,y,TURBO_WIDTH,TURBO_HEIGHT);
        stateTime=0;

        textureAtlas = Assets.manager.get(Assets.turbo,TextureAtlas.class);
        Array<TextureRegion> normal = new Array<TextureRegion>();
        normal.add(textureAtlas.findRegion("particle2-2"));
        normal.add(textureAtlas.findRegion("particle2"));
        normal.add(textureAtlas.findRegion("particle2-3"));
        animation = new Animation(0.2f,normal);

    }
    @Override
    public void draw(Batch batch, float alpha) {
        stateTime+=Gdx.graphics.getDeltaTime();
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * alpha);
        batch.draw((TextureRegion) animation.getKeyFrame(stateTime,true), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

}
