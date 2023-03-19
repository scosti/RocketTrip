package My.GameObjects;


import My.Game.Assets;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 *  <p>
 *  This class extends {@link GameObject} and defines an Asteroid.
 *  @author Benedetti Costi
 */

public class Asteroid extends GameObject {
    private static final float ASTEROID_HEIGHT = 0.6f;
    private static final float ASTEROID_WIDTH = 0.6f;
    private static final float ASTEROID_X = 0;
    private TextureRegion textureRegion;

    public Asteroid( float y) {
        super(ASTEROID_X, y, ASTEROID_WIDTH, ASTEROID_HEIGHT);
        textureRegion = new TextureRegion(Assets.manager.get(Assets.asteroid, Texture.class));
    }

    @Override
    public void draw(Batch batch, float alpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * alpha);
        batch.draw(textureRegion, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }


}
