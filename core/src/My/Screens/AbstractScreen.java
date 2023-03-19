package My.Screens;

import My.Game.RocketTrip;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class AbstractScreen implements Screen {

    protected final RocketTrip game;
    protected Viewport viewport;

    public AbstractScreen(final RocketTrip game) {
        this.game = game;
        viewport = new ExtendViewport(RocketTrip.WIDTH, RocketTrip.HEIGHT);
        viewport.apply();
    }

    @Override
    public void show() {

    }

    public abstract void update(float delta);

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.30f, .22f, .24f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        game.getCamera().position.set(game.getCamera().viewportWidth / 2, game.getCamera().viewportHeight / 2, 0);
        game.getCamera().update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}
