package My.GameObjects;


import My.Game.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 *  <p>
 *  This class extends {@link GameObject} and defines an Alien.
 *  It is implemented also an animation.
 *  @author Benedetti Costi
 */

public class Alien extends GameObject{
    private static final float ALIEN_HEIGHT = 0.5f;
    private static final float ALIEN_WIDTH = 0.8f;
    private static final float ALIEN_X = 0;

    private TextureAtlas aliens;
    private Animation right;
    private Animation left;

    public Alien( float y) {
        super(ALIEN_X, y, ALIEN_WIDTH, ALIEN_HEIGHT);
        aliens = Assets.manager.get(Assets.alien_pack,TextureAtlas.class);

        Array<TextureRegion> right_images = new Array<TextureRegion>();
        right_images.add(aliens.findRegion("alien1"));
        right_images.add(aliens.findRegion("alien2"));
        right_images.add(aliens.findRegion("alien3"));
        right = new Animation(0.2f,right_images);

        Array<TextureRegion> left_images = new Array<TextureRegion>();
        left_images.add(aliens.findRegion("alien4"));
        left_images.add(aliens.findRegion("alien5"));
        left_images.add(aliens.findRegion("alien6"));
        left = new Animation(0.2f,left_images);
    }

    private static float stateTime = 0f;
    @Override
    public void draw(Batch batch, float alpha) {
        stateTime += Gdx.graphics.getDeltaTime();
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * alpha);
        if (getVelocity().x > 0) {
            batch.draw((TextureRegion) right.getKeyFrame(stateTime,true), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        } else {
            batch.draw((TextureRegion) left.getKeyFrame(stateTime,true), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }

}
