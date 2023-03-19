package My.GameObjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 *  <p>
 *  This abstract class defines a generic game object (2D) that will be extended in the specific's objects classes.
 *  It has velocity, acceleration, and a rectangular size.
 *  </p>
 *  @author Benedetti Costi
 */

public abstract class GameObject extends Actor {
    private Rectangle rectangle;
    private Vector2 velocity;
    private Vector2 acceleration;

    public GameObject(float x, float y, float width, float height) {
        setBounds(x,y,width,height);
        rectangle = new Rectangle(getX(),getY(),getWidth(),getHeight());
        velocity = new Vector2(0,0);
        acceleration = new Vector2(0,0);
    }

    /**
     *  <p>
     *  Updates the game object based on time. Is called each frame by {@link Rocket#act(float)}
     *  </p>
     */

    @Override
    public void act(float delta) {
        super.act(delta);
        velocity.x = velocity.x + acceleration.x * delta;
        velocity.y = velocity.y + acceleration.y * delta;
        float x = velocity.x*delta;
        float y = velocity.y*delta;
        setBounds(getX()+x,getY()+y,getWidth(),getHeight());
        rectangle.setPosition(getX(),getY());
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration;
    }

}
