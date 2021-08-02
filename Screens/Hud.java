package My.Screens;

import My.Game.Assets;
import My.Game.LevelsManager;
import My.Game.RocketTrip;
import My.GameObjects.Rocket;
import My.Screens.GameScreenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.Locale;

public class Hud implements Pool.Poolable{
    private final RocketTrip game;
    private Stage stage;
    private Viewport viewport;
    private Skin skin1,skin2;
    private Label metersLabel;
    private Label fuelLabel;
    private Label levelLabel;
    private ImageButton pause;
    private Image fuelImage;
    private TextureAtlas textureAtlas;
    private Table middleTable;
    private TextButton resumeButton;

    public int meters;
    private float fuel;

    public Hud(final RocketTrip game) {
        this.game = game;
        viewport = new ExtendViewport(RocketTrip.WIDTH, RocketTrip.HEIGHT, new OrthographicCamera());
    }

    public void load(){
        fuel = 100;
        meters = 0;

        stage = new Stage(viewport,game.getBatch());
        skin2 = Assets.manager.get(Assets.skins,Skin.class);
        skin1 = Assets.manager.get(Assets.skins_pack1,Skin.class);
        textureAtlas = Assets.manager.get(Assets.pack_1,TextureAtlas.class);

        fuelImage = new Image(new TextureRegion(textureAtlas.findRegion("fuel1")));
        fuelLabel = new Label(String.format(Locale.ENGLISH, "%d",(int) fuel), skin1);
        metersLabel = new Label(String.format(Locale.ENGLISH,"%03d",meters), skin1);
        levelLabel = new Label(String.format(Locale.ENGLISH, "s"), skin1);
        pause = new ImageButton(skin1,"pause");
        pause.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pauseScreen();
                middleTable.setVisible(true);
                resumeButton.setDisabled(false);
                pause.setDisabled(true);
                Gdx.graphics.setContinuousRendering(false);
            }
        });

        Table table = new Table();
        table.top();
        table.setFillParent(true);
        table.add(fuelImage).padTop(10).size(50,50);
        table.add(fuelLabel).padLeft(0).padTop(10);
        table.add(metersLabel).expandX().padTop(10);
        table.add(pause).padTop(10).size(50,50);
        stage.addActor(table);
        Table tablelevel = new Table();
        tablelevel.add(levelLabel).expandX().padTop(10);
        tablelevel.setFillParent(true);
        levelLabel.setVisible(false);
        stage.addActor(tablelevel);

        Label label = new Label("PAUSE", skin1);
        resumeButton = new TextButton("Resume", skin2);
        TextButton menuButton = new TextButton("Menu", skin2);

        resumeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.graphics.setContinuousRendering(true);
                game.resume();
                middleTable.setVisible(false);
                resumeButton.setDisabled(true);
                pause.setDisabled(false);
            }
        });
        menuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getGsm().setScreen(GameScreenManager.STATE.HIGHSCORE);
            }
        });

        middleTable = new Table();
        middleTable.setFillParent(true);
        middleTable.center();
        middleTable.add(label).row();
        middleTable.add(resumeButton).row();
        middleTable.add(menuButton).row();
        stage.addActor(middleTable);

        middleTable.setVisible(false);
        resumeButton.setDisabled(true);
    }

    public void pauseScreen(){
        game.pause();
    }

    public void update(float meters, Rocket.State state, LevelsManager levelsManager){
        this.meters = (int)meters;
        metersLabel.setText(String.format(Locale.ENGLISH,"METERS %03d",(int)(meters*50)-29));
        if (levelsManager.isStampLayer()) {
            if (levelsManager.getLevel()<=7) {
                levelLabel.setText(String.format(Locale.ENGLISH, "%s", (String) levelsManager.getLayers().get(levelsManager.getLevel())));
            }
            else{
                levelLabel.setText(String.format(Locale.ENGLISH, "SPACE "+levelsManager.getLevel()));
            }
            levelLabel.setVisible(true);
        }
        else{
            levelLabel.setVisible(false);
        }
        if (state == Rocket.State.TURBO){
            metersLabel.setColor(0,1,0,1);
            fuelLabel.setColor(0,1,0,1);
        }
        else{
            if (fuel>0 && state == Rocket.State.FLY){
                fuel-=Gdx.graphics.getDeltaTime()*3;
            }
            metersLabel.setColor(1,0,0,1);
            fuelLabel.setColor(1,0,0,1);
        }

        fuelLabel.setText(String.format(Locale.ENGLISH,"%03d",(int)fuel));
    }

    public Stage getStage(){
        return stage;
    }
    public void setFuel(int fuel) {
        this.fuel = fuel;
    }
    public float getFuel() {
        return fuel;
    }

    public int getMeters() {
        return Integer.parseInt(metersLabel.getText().toString().substring(7));
    }

    @Override
    public void reset() {
        fuel = 100;
    }

}
