package My.Generators;

import My.Game.Assets;
import My.Game.LevelsManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import java.util.LinkedList;
import java.util.Random;

import static My.Game.RocketTrip.*;

/**
 *  <p>
 *  This class is used for generate the background of the game, using a linked list of images where we put the two types
 *  of clouds.
 *  Then the clouds are randomly printed in the background while the rocket goes up with the method updateBackgroundElements(float height)
 *  called in {@link Renderer#update(float, float)}.
 *  </p>
 *  @author Benedetti Costi
 */

public class BackgroundGenerator {

    private LevelsManager levelsManager;
    private Stage stage;

    private Texture cloud1;
    private Texture cloud3;

    private LinkedList<Image> elements;
    private float lastHeight; //last height where I created an element
    private float meters; //after how many meters appear
    private Random random;

    public BackgroundGenerator(LevelsManager levelsManager,Stage stage) {
        this.levelsManager = levelsManager;
        this.stage=stage;

        cloud1 = Assets.manager.get(Assets.nuvola1,Texture.class);
        cloud3 = Assets.manager.get(Assets.nuvola3,Texture.class);

        lastHeight = 6;
        meters = 1.0f;
        elements = new LinkedList<Image>();
        random = new Random();
    }

    public void updateBackgroundElements(float height) {
        float heightCreation = height + 20; //height where the elements are created
        while (lastHeight - heightCreation < meters) {
            lastHeight = lastHeight + (meters) * random.nextFloat();
            if (levelsManager.getLevel() < 4) {  //creo le nuvole solo se sono nei primi 4 livelli
                if (random.nextDouble() > 0.5) {
                    elements.addLast(new Image(cloud1));
                } else {
                    elements.addLast(new Image(cloud3));
                }
            }
            float origin = - LEVEL_WIDTH / 2;
            elements.getLast().setBounds(origin + LEVEL_WIDTH * random.nextFloat(), lastHeight , 0.8f, 0.5f);
            if (random.nextDouble() > 0.5) {
                elements.getLast().addAction(forever(sequence(moveBy(-random.nextFloat(), 0, 10f),
                        delay( 2 * random.nextFloat()), moveBy( random.nextFloat(), 0, 10f))));
            } else
            {
                elements.getLast().addAction(forever(sequence(moveBy( random.nextFloat(), 0, 10f),
                        delay(random.nextFloat()), moveBy( -random.nextFloat(), 0, 10f))));
            }
            stage.addActor(elements.getLast());
        }
        if (elements.getFirst().getY() < height - 5) { //if the element is out of the screen I delete it
            elements.getFirst().remove();
        }

        freeStage(height);
    }


    private void freeStage(float height){
        for(Actor actor : stage.getActors())
        {
            if (actor.getY() < height-10)
                actor.remove();
        }
    }
}
