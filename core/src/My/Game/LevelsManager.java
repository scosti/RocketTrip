package My.Game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

/**
 *  <p>
 *  This class is used to manage the levels of the game. In the method LevelsManager is defined an array (layers) of
 *  strings that contains the names of the levels.
 *  Furthermore there is the method updateLevel that checks at any frame the position of rocket and updates the actual
 *  level; this is called at {@link My.Generators.Renderer#update(float, float)}.
 *  The method IsStampLayer manage the stamp on screen of the actual level, it will be printed only a when a new level
 *  is reached, and only for a limited time.
 *  </p>
 *  @author Benedetti Costi
 */

public class LevelsManager {
    private final static float LEVEL_LENGTH = 150;

    private Array<String> layers;
    private int level;

    private boolean stampLayer;

    public LevelsManager() {
        layers = new Array<String>(6);
        layers.add("");
        layers.add("LEVEL 1");
        layers.add("LEVEL 2");
        layers.add("LEVEL 3");
        layers.add("LEVEL 4");
        layers.add("LEVEL 5");
        layers.add("LEVEL 6");
        layers.add("SPACE !!!");

        stampLayer = true;
        level = 1;
        Gdx.app.log("level",""+level);
    }

    float count = 0;
    public void updateLevel(float position) {
        if (position > LEVEL_LENGTH*level) {
            level++;
            stampLayer = true;
            Gdx.app.log("level",""+level);
        }
        if(stampLayer){
            count += Gdx.graphics.getDeltaTime();
            if ( count > 3 ){
                count =0;
                stampLayer = false;
            }
        }
    }

    public int getLevel(){
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    public Array<String> getLayers() {
        return layers;
    }

    public boolean isStampLayer() {
        return stampLayer;
    }

}
