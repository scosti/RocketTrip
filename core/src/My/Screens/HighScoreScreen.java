package My.Screens;

import My.Game.Assets;
import My.Game.RocketTrip;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import My.Screens.GameScreenManager.STATE;


public class HighScoreScreen extends AbstractScreen
{
    private Stage stage;

    HighScoreScreen(final RocketTrip game) {
        super(game);
    }

    @Override
    public void show() {
        stage = new Stage(viewport, game.getBatch());
        Skin skin2 = Assets.manager.get(Assets.skins_pack1,Skin.class);
        Skin skin = Assets.manager.get(Assets.skins,Skin.class);
        Gdx.input.setInputProcessor(stage);

        Table mainTable = new Table();
        Table titleTable = new Table();

        titleTable.setFillParent(true);
        titleTable.top();

        mainTable.setFillParent(true);
        mainTable.bottom();

        Label titolo = new Label("HighScore",skin2,"title-text");
        //Label prova = new Label("Testo di prova",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        TextButton backButton = new TextButton("Menu", skin);
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getGsm().setScreen(STATE.MENU);
            }
        });

        titleTable.pad(50.0f);
        titleTable.add(titolo);
        titleTable.row();
        mainTable.pad(50.0f);
        mainTable.add(backButton);

        stage.addActor(titleTable);
        new ScrollPanel(game,stage,skin);

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

    public String getString(String string){
        return string;
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
