package My.Generators;

import My.Game.LevelsManager;
import My.GameObjects.GameObject;
import My.GameObjects.ObjectFactory;
import My.GameObjects.Tank;
import My.GameObjects.Turbo;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.LinkedList;
import java.util.Random;

import static My.Game.RocketTrip.LEVEL_WIDTH;

/**
 *  <p>
 *  This class is used for generate tanks and fuel in the game, using a linked list of game objects.
 *  Is used the ObjectFactory's method {@link ObjectFactory#createObject(String, float)} to generate tanks and fuel randomly,
 *  according with the factory design pattern.
 *  The objects are created up the screen in central position, then the velocity is set.
 *  The object can't exit from the screen thaks to checkObject method.
 *  The update method is called in {@link Renderer#update(float, float)} while the rocket goes up.
 *  </p>
 *  @author Benedetti Costi
 */


public class FuelTankGenerator {
    private Stage stage;
    private LevelsManager levelsManager;
    private LinkedList<GameObject> gameObjectLinkedList;
    private float lastHeight; //last height where I created a fuel tank
    private float meters; //after how many meters appear
    private Random random;
    private ObjectFactory objectFactory;

    public FuelTankGenerator(Stage stage, LevelsManager levelsManager) {
        this.stage = stage;
        this.levelsManager = levelsManager;
        lastHeight = 10;
        meters = 20+10*levelsManager.getLevel();
        gameObjectLinkedList = new LinkedList<GameObject>();
        random = new Random();
        objectFactory = new ObjectFactory();
    }

    public void update(float height){  //height: where the rocket is
        float heightCreation = height + 30; //height where the tank are created
        if (lastHeight - heightCreation < meters ){
            lastHeight = lastHeight+meters+2*(float)Math.random();
            Vector2 position = new Vector2(0,lastHeight);
            if(random.nextDouble()>0.2) {
                gameObjectLinkedList.addLast(objectFactory.createObject("Tank",position.y));
            }else{
                gameObjectLinkedList.addLast(objectFactory.createObject("Turbo",position.y));
            }
            if(random.nextBoolean()){
                gameObjectLinkedList.getLast().setVelocity(new Vector2(random.nextFloat(),0));
            }
            else{
                gameObjectLinkedList.getLast().setVelocity(new Vector2(-random.nextFloat(),0));
            }
            stage.addActor(gameObjectLinkedList.getLast());
        }
        if (gameObjectLinkedList.getFirst().getY() < height - 3){ //if the Tank is out of the screen I delete it
            gameObjectLinkedList.removeFirst();
        }
        checkObject();
    }
    private void checkObject(){
        for (GameObject e : gameObjectLinkedList) {
            if (e.getX() >= LEVEL_WIDTH/2-e.getWidth()|| e.getX()<= -LEVEL_WIDTH/2+e.getWidth() ){
                e.setVelocity(new Vector2(-e.getVelocity().x,0));
            }
        }
    }
    public LinkedList<GameObject> getList(){
        return gameObjectLinkedList;
    }

}
