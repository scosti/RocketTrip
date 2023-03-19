package My.GameObjects;

import My.Game.Assets;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 *  <p>
 *  This class extends {@link GameObject} and defines a Tank.
 *  @author Benedetti Costi
 */

public class Tank extends GameObject{
    private static final float TANK_HEIGHT = 0.4f;
    private static final float TANK_WIDTH = 0.3f;
    private static final float TANK_X = 0;
    private TextureRegion textureRegion;
    private TextureAtlas textureAtlas;

    public Tank(float y) {
        super(TANK_X, y, TANK_WIDTH, TANK_HEIGHT);
        textureAtlas = Assets.manager.get(Assets.pack_1,TextureAtlas.class);
        textureRegion = textureAtlas.findRegion("Fuel_Can");
    }

    @Override
    public void draw(Batch batch, float alpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * alpha);
        batch.draw(textureRegion, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}
