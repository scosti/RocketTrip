package My.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

/**
 *  <p>
 *  This class perform an explosion using a particle effect (for more {@link ParticleEffect}).
 *  When the game state is CRASH (see {@link My.Screens.PlayScreen#render(float)} the method boom is called and the
 *  explosion is performed.
 *  @author Benedetti Costi
 */

public class Explosion {
    private ParticleEffect particleEffect;

    public Explosion() {
        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("pack1/part.p"),Gdx.files.internal("pack1"));
    }

    public void boom(float x,float y){
        particleEffect.setPosition(x,y);
        if(particleEffect.isComplete())
            particleEffect.start();
    }

    public void update(float delta){
        particleEffect.update(delta);
    }

    public void reset(){
        particleEffect.reset();
    }

    public ParticleEffect getParticleEffect() {
        return particleEffect;
    }

    public void dispose(){
        particleEffect.dispose();
    }

}


