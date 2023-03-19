package My.Generators;

import My.Game.LevelsManager;
import My.GameObjects.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import java.util.LinkedList;
import java.util.Random;

import static My.Game.RocketTrip.LEVEL_WIDTH;

/**
 *  <p>
 *  This class is used for generate the enemies in the game, using a linked list of game objects.
 *  At each different level are created different enemies, with different probabilities and different velocities.
 *  Is used the ObjectFactory's method {@link ObjectFactory#createObject(String, float)} to create the enemies, according
 *  with the factory design pattern.
 *  The enemies are created up the screen in central position, then the velocity is set.
 *  The update method is called in {@link Renderer#update(float, float)} while the rocket goes up.
 *  Note: when the rocket is close to the alien, this one takes the x position of the rocket (follow effect).
 *  </p>
 *  @author Benedetti Costi
 */

public class EnemyGenerator {
    private Stage stage;
    private LevelsManager levelsManager;
    private LinkedList<GameObject> gameObjectLinkedList;
    private float lastHeight; //last height where I created an enemy
    private float meters; //after how many meters appear
    private Random random;
    private ObjectFactory objectFactory;

    public EnemyGenerator(Stage stage, LevelsManager levelsManager) {
        this.stage = stage;
        this.levelsManager = levelsManager;
        lastHeight = 10;
        meters = setMeter();
        gameObjectLinkedList = new LinkedList<GameObject>();
        random = new Random();
        objectFactory = new ObjectFactory();
    }

    public void update(float height,float x){  //height: where the rocket is
        float heightCreation = height + 30; //height where the enemy are created
        if (lastHeight - heightCreation < meters ){
            lastHeight = lastHeight+(meters)*(float)Math.random() + 2;
            Vector2 position = new Vector2(0,lastHeight);

            switch(levelsManager.getLevel()) {
                case 1:
                    gameObjectLinkedList.addLast(objectFactory.createObject("Plane",position.y));break;
                case 2:
                    if (random.nextDouble() < 0.2 && !(gameObjectLinkedList.getLast() instanceof Alien)) {
                        gameObjectLinkedList.addLast(objectFactory.createObject("Alien",position.y));
                    } else {
                        gameObjectLinkedList.addLast(objectFactory.createObject("Plane",position.y));
                    }
                    break;
                case 3:
                    if (random.nextDouble() < 0.3 && !(gameObjectLinkedList.getLast() instanceof Alien)) {
                        gameObjectLinkedList.addLast(objectFactory.createObject("Alien",position.y));
                    } else {
                        gameObjectLinkedList.addLast(objectFactory.createObject("Plane",position.y));
                    }
                    break;
                case 4:
                    if (random.nextDouble() < 0.3 && !(gameObjectLinkedList.getLast() instanceof Alien)) {
                        gameObjectLinkedList.addLast(objectFactory.createObject("Alien",position.y));
                    } else {
                        gameObjectLinkedList.addLast(objectFactory.createObject("Asteroid",position.y));
                    }
                    break;
                default:
                    if (random.nextDouble() < 0.4 && !(gameObjectLinkedList.getLast() instanceof Alien)) {
                        gameObjectLinkedList.addLast(objectFactory.createObject("Alien",position.y));
                    } else {
                        gameObjectLinkedList.addLast(objectFactory.createObject("Asteroid",position.y));
                    }
            }

            if(random.nextBoolean()){
                gameObjectLinkedList.getLast().setVelocity(new Vector2(levelsManager.getLevel()*0.5f + random.nextFloat(),0));
            }
            else{
                gameObjectLinkedList.getLast().setVelocity(new Vector2(-levelsManager.getLevel()*0.5f - random.nextFloat(),0));
            }
            stage.addActor(gameObjectLinkedList.getLast());
        }
        if (gameObjectLinkedList.getFirst().getY() < height - 3){ //if the enemy is out of the screen I delete it
            gameObjectLinkedList.removeFirst();
        }

        for (GameObject e : gameObjectLinkedList) {
                if (e.getX() > LEVEL_WIDTH/2+e.getWidth()|| e.getX()< -LEVEL_WIDTH/2-e.getWidth()*2){
                    e.setVelocity(new Vector2(-e.getVelocity().x,0));
                }
                if (e instanceof Alien && e.getY()-height<10){ //Alien follows the rocket
                    if (x<e.getX()) {
                        e.setVelocity(new Vector2(-levelsManager.getLevel() * 0.2f - random.nextFloat()*1.5f, 0));
                    }
                    else {
                        e.setVelocity(new Vector2(levelsManager.getLevel() * 0.2f + random.nextFloat()*1.5f, 0));
                    }
                }
        }
    }
    public LinkedList<GameObject> getList(){
        return gameObjectLinkedList;
    }
    private float setMeter(){
        return 1.1f - levelsManager.getLevel()/10;
    }

}
