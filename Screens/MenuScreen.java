package My.Screens;

import My.Game.Assets;
import My.Game.RocketTrip;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import My.Screens.GameScreenManager.STATE;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class MenuScreen extends AbstractScreen{

	private Stage stage;
    private Skin skin;
    private Image background;


    MenuScreen(final RocketTrip game){
	    super(game);
        game.getCamera().position.set(game.getCamera().viewportWidth / 2, game.getCamera().viewportHeight / 2, 0);
        game.getCamera().update();
	}

    @Override
	public void show() {
        Assets.manager.finishLoading();
        skin = Assets.manager.get(Assets.skins_pack1,Skin.class);
        background = new Image(Assets.manager.get(Assets.ground2, Texture.class));
        stage = new Stage(viewport, game.getBatch());
        Gdx.input.setInputProcessor(stage);
		initButtons();
	}

    private void initButtons(){
        Table mainTable = new Table();
        Table titleTable = new Table();
        Table barTable = new Table();

        barTable.setFillParent(true);
        barTable.top();
        titleTable.setFillParent(true);
        titleTable.top();
        mainTable.setFillParent(true);
        mainTable.center();

        background.setBounds(-210,0, 960, 240);
        background.addAction(moveBy(-20,0,1.5f));
        Image rocket = new Image(new Texture("img/rocket_fire.png"));
        rocket.setBounds(500,-200,40,60);
        rocket.addAction(forever(sequence(
                delay(2),
                moveBy(-700,1100,3f),alpha(0),moveBy(0,-1100,3f),alpha(1),
                delay(3),
                moveBy(700,1100,3f),alpha(0),moveBy(0,-1100,3f),alpha(1)
        )));

        Label titolo = new Label("RocketTrip",skin,"title-text");
        ImageButton playButton = new ImageButton(skin,"play");
        ImageButton optionButton = new ImageButton(skin,"cup");
        ImageButton exitButton = new ImageButton(skin,"exit");

        titolo.setPosition(stage.getViewport().getScreenWidth()+titolo.getWidth(),
                            stage.getViewport().getScreenHeight() -titolo.getHeight());
        playButton.addAction(sequence(parallel(sequence(alpha(0), fadeIn(0.8f)))
                ));
        titolo.addAction(sequence((alpha(0f)),parallel(fadeIn(1),moveBy(0,-40,1.5f))));
        optionButton.addAction(sequence(alpha(0),fadeIn(0.9f)));
        exitButton.addAction(sequence(alpha(0), fadeIn(1f)));


        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getGsm().setScreen(STATE.PLAY);
                //game.setScreen(new PlayScreen(game));
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }

        });

        optionButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getGsm().setScreen(STATE.HIGHSCORE);
            }
        });

        barTable.add(optionButton).expandX();
        barTable.add(exitButton).expandX();
        titleTable.padTop(100);
        titleTable.add(titolo);
        titleTable.row();
        mainTable.add(playButton);


        stage.addActor(background);
        stage.addActor(rocket);
        stage.addActor(barTable);
        stage.addActor(titleTable);
        stage.addActor(mainTable);
    }

    @Override
    public void update(float delta) {
        stage.act();
    }

	@Override
	public void render(float delta) {
		super.render(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {
	    stage.dispose();
	}

}


