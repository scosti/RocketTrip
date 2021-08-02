package My.Generators;


import My.Game.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 *  <p>
 *  In this class is managed the vertical flow of the game, the generators are instantiated and updated as the rocket
 *  goes up thanks to the method update (called every dt from {@link My.Screens.PlayScreen#update(float)} ).
 *  </p>
 *  @author Benedetti Costi
 */


public class Renderer {

    private LevelsManager levelsManager;
    private BackgroundGenerator backgroundGenerator;
    private EnemyGenerator enemyGenerator;
    private FuelTankGenerator fuelTankGenerator;

    public Renderer(Stage stage) {
        this.levelsManager = new LevelsManager();

        Image touch = new Image(Assets.manager.get(Assets.touch,Texture.class));
        touch.setBounds(-0.5f ,4, 1, 1);
        touch.addAction(forever(sequence(alpha(0),moveBy(0,-0.04f,0.4f),alpha(1),moveBy(0,0.04f,0.8f))));
        stage.addActor(touch);

        Image terreno = new Image(Assets.manager.get(Assets.ground2, Texture.class));
        terreno.setBounds(-4.8f ,0, 9.6f, 2.4f);
        stage.addActor(terreno);

        backgroundGenerator = new BackgroundGenerator(levelsManager,stage);
        enemyGenerator = new EnemyGenerator(stage,levelsManager);
        fuelTankGenerator = new FuelTankGenerator(stage,levelsManager);
    }

    public void update(float height,float x){
        levelsManager.updateLevel(height);
        backgroundGenerator.updateBackgroundElements(height);
        enemyGenerator.update(height,x);
        fuelTankGenerator.update(height);
    }

    public EnemyGenerator getEnemyGenerator(){
        return enemyGenerator;
    }
    public FuelTankGenerator getFuelTankGenerator() {
        return fuelTankGenerator;
    }
    public LevelsManager getLevelsManager() {
        return levelsManager;
    }

}
