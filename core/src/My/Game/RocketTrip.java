package My.Game;

import My.Database.Database;
import My.Screens.GameScreenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *  <p>
 *  This is the main class of the game, it contains the measurement variables.
 *  There are two constructors, one for android and the other for desktop, so you can use it without SQLite.
 *  The class offers some methods, in particular:
 *  -Create: the first method called when the app starts
 *  -Render: method called by the game loop from the application every time rendering should be performed.
 *      Game logic updates are usually also performed in this method.
 *  -Dispose: called when app is destroyed; method for deallocate memory.
 *  </p>
 *  @author Benedetti Costi
 */

public class RocketTrip extends Game {

	public static final String TITLE = "RocketTrip";
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;
	public static final float PPM = 100f;
	public static final float LEVEL_WIDTH = 4.8f;

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private My.Screens.GameScreenManager gsm;
    public Database db;

    public RocketTrip() {
    }

    public RocketTrip(Database db) {
		this.db = db;
    }

    /**
     *  <p>
     *  This is the first method called when the app starts, it loads assets, create a batch, a camera and a GameScreenManager.
     *  </p>
     */

    @Override
	public void create () {
		Assets.load();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		gsm = new GameScreenManager(this);

	}

    /**
     *  <p>
     *  This method is called by the game loop from the application every time rendering should be performed.
     *  Game logic updates are usually also performed in this method.
     */

	@Override
	public void render () {
		super.render();
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
			Gdx.app.exit();
		}
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void setScreen(Screen screen) {
		super.setScreen(screen);
	}

	@Override
	public Screen getScreen() {
		return super.getScreen();
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

    /**
     *  <p>
     *  This method is called when app is destroyed to deallocate memory.
     *  </p>
     *
     */

	@Override
	public void dispose () {
		super.dispose();
		gsm.dispose();
		batch.dispose();
		Assets.dispose();
	}


	public OrthographicCamera getCamera() {
		return camera;
	}

	public SpriteBatch getBatch(){
		return batch;
	}

	public GameScreenManager getGsm() {
		return gsm;
	}



}
