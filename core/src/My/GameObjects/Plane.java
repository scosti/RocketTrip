package My.GameObjects;

import My.Game.Assets;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 *  <p>
 *  This class extends {@link GameObject} and defines a Plane.
 *  There are 2 types of planes, a fighter-bomber and an airliner.
 *  @author Benedetti Costi
 */

public class Plane extends GameObject {
    private static final float PLANE_HEIGHT = 0.3f;
    private static final float PLANE_WIDTH = 0.9f;
    private static final float PLANE_X = 0;

    private TextureRegion sx;
    private TextureRegion dx;

    public Plane(float y) {
        super(PLANE_X,y,PLANE_WIDTH,PLANE_HEIGHT);
        if(Math.random()>0.4) {
            sx = new TextureRegion(Assets.manager.get(Assets.LibyanSx, Texture.class));
            dx = new TextureRegion(Assets.manager.get(Assets.LibyanDx, Texture.class));
        }
        else{
            sx = new TextureRegion(Assets.manager.get(Assets.aereoSX, Texture.class));
            dx = new TextureRegion(Assets.manager.get(Assets.aereoDx, Texture.class));
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * alpha);

        if (getVelocity().x > 0) {
            batch.draw(sx, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        } else {
            batch.draw(dx, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }

}
