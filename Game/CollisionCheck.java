package My.Game;

import My.GameObjects.GameObject;
import My.GameObjects.Rocket;
import My.GameObjects.Tank;
import My.GameObjects.Turbo;

import My.Screens.PlayScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.LinkedList;
import java.util.Random;

/**
 *  <p>
 *  This class with the method update (called in {@link PlayScreen#update(float)})checks at any frame if the rocket
 *  collides with other game objects (e.g. enemies, tanks) and update the rocket state and the game state properly.
 *  Note: if the rocket state is set TURBO collisions with enemies are enabled.
 *  </p>
 *  @author Benedetti Costi
 */

public class CollisionCheck {
    private PlayScreen playScreen;
    private Rocket rocket;
    private GameObject enemyTaken;
    private GameObject tankTaken;
    private Random random;

    public CollisionCheck(PlayScreen playScreen,Rocket rocket){
        this.playScreen = playScreen;
        this.rocket = rocket;
        random = new Random();
    }

    public void  update(LinkedList<GameObject> listEnemy,LinkedList<GameObject> listTank){
        for (GameObject gameObject : listEnemy){
            if (rocket.getRectangle().overlaps(gameObject.getRectangle()) && gameObject != enemyTaken){
                enemyTaken = gameObject;
                if(rocket.getInternalState()!= Rocket.State.TURBO) //if TURBO collision enabled
                    playScreen.setInternalState(PlayScreen.GameState.CRASH);
                if(random.nextBoolean()){
                    gameObject.addAction(Actions.rotateBy(150,1.5f));
                }
                else{
                    gameObject.addAction(Actions.rotateBy(-150,1.5f));
                }
                gameObject.setAcceleration(new Vector2(0,-9));
            }
        }

        for (GameObject gameObject : listTank){
            if (rocket.getRectangle().overlaps(gameObject.getRectangle()) && gameObject != tankTaken){
                tankTaken = gameObject;
                tankTaken.setVisible(false);
                if (tankTaken.getClass() == Tank.class) {
                    playScreen.getHud().setFuel(101);
                }
                if (tankTaken.getClass() == Turbo.class) {
                    rocket.setInternalState(Rocket.State.TURBO);
                }
            }
        }


        if (playScreen.getHud().getFuel() <= 1){
            rocket.setInternalState(Rocket.State.START);
            rocket.setAcceleration(new Vector2(0,-9));
            time+=Gdx.graphics.getDeltaTime();
            if (time >= 0.8f)
                playScreen.setInternalState(PlayScreen.GameState.CRASH);
        }
    }
    private float time = 0f;
}
