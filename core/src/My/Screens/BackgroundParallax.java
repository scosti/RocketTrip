package My.Screens;


import My.Game.Assets;
import My.Game.RocketTrip;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static My.Game.RocketTrip.*;

public class BackgroundParallax {
    private final RocketTrip game;
    private Stage stage;
    private Viewport viewport;
    private Image background;

    public BackgroundParallax(RocketTrip game) {
        this.game = game;
        viewport = new ExtendViewport(RocketTrip.WIDTH, RocketTrip.HEIGHT, new OrthographicCamera());
    }

    public void load(){
        stage = new Stage(viewport,game.getBatch());
        background = new Image(Assets.background);
        viewport.getCamera().position.x = 0;
        background.setPosition(-background.getWidth()/2,0);
        stage.addActor(background);
    }

    public void update(float height){
        float parallax = PPM/15;
        if(game.getCamera().position.y < (background.getHeight()/parallax-game.getCamera().viewportHeight * 15))
            background.setPosition(background.getX(),game.getCamera().viewportHeight/2-height*parallax+0.3f);
    }

    public void reset(){
        background.setPosition(background.getX(),0);
    }
    public Stage getStage(){
        return stage;
    }
}
